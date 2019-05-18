package com.example.sofraapp.app.ui.fragment.restaurant;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.example.sofraapp.R;
import com.example.sofraapp.app.adapter.AdapterRestaurantItems;
import com.example.sofraapp.app.data.model.general.restaurantitems.Data2RestaurantItems;
import com.example.sofraapp.app.data.model.general.restaurantitems.RestaurantItems;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.Model;
import com.example.sofraapp.app.helper.RememberMy;
import com.example.sofraapp.app.helper.SaveData;
import com.example.sofraapp.app.ui.fragment.client.order.DetailesOrderFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofraapp.app.data.rest.RetrofitClient.getRetrofit;
import static com.example.sofraapp.app.helper.HelperMethod.GET_DATA;
import static com.example.sofraapp.app.ui.activity.MainActivity.toolbar;
import static com.example.sofraapp.app.ui.fragment.general.restaurant.OrderFoodFragment.DETAILS_RESTUARANT;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFoodFragment extends Fragment/* implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener*/ {
    @BindView(R.id.ListFoodFragment_Loading_Indicator)
    ProgressBar ListFoodFragmentLoadingIndicator;
    @BindView(R.id.ListFoodFragment_Recycler_View)
    RecyclerView ListFoodFragmentRecyclerView;
    @BindView(R.id.ListFoodFragment_RL)
    RelativeLayout ListFoodFragmentRL;
    Unbinder unbinder;
    private APIServices apiServices;
    private AdapterRestaurantItems adapterRestaurantItems;
    RememberMy rememberMy;
    Model model;
    SaveData saveData;
    ArrayList<Data2RestaurantItems>data2RestaurantItemsArrayList = new ArrayList<>();
    public ListFoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_food, container, false);
        unbinder = ButterKnife.bind(this, view);
        rememberMy = new RememberMy(getActivity());
        saveData = getArguments().getParcelable(GET_DATA);
        data2RestaurantItemsArrayList.clear();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        ListFoodFragmentRecyclerView.setLayoutManager(linearLayoutManager);
        ListFoodFragmentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ListFoodFragmentRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        ListFoodFragmentLoadingIndicator.setVisibility(View.VISIBLE);
        if (rememberMy.getSaveState() == 2){
           adapterRestaurantItems = new AdapterRestaurantItems(getActivity(), data2RestaurantItemsArrayList,null, new AdapterRestaurantItems.delete() {
               @Override
               public void deleteItem(Data2RestaurantItems position) {
                   Toast.makeText(getActivity(), " Clicked Deleted ", Toast.LENGTH_SHORT).show();
               }
           }, new AdapterRestaurantItems.edit() {
               @Override
               public void editItem(Data2RestaurantItems position) {
                   Toast.makeText(getActivity(), " Clicked Edited ", Toast.LENGTH_SHORT).show();
               }
           },null);
            ((AdapterRestaurantItems) adapterRestaurantItems).setMode(Attributes.Mode.Single);
        }else {
           adapterRestaurantItems = new AdapterRestaurantItems(getActivity(), data2RestaurantItemsArrayList, new AdapterRestaurantItems.showDetial() {
               @Override
               public void itemShowDetail(Data2RestaurantItems position) {
                   int detailRestaurant = position.getId();
                   DetailesOrderFragment detailesOrderFragment = new DetailesOrderFragment();
                   model = new Model(position.getPhotoUrl(),position.getPrice(),position.getName(),
                           position.getPreparingTime(),position.getDescription(),detailRestaurant);
                  // saveData = new SaveData(saveData.getSave_state(), detailRestaurant);
                 //  HelperMethod.replece(detailesOrderFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_Home_contener, null, null, saveData);
                   HelperMethod.repleceModel(detailesOrderFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, model.getTitle(), model);
               }
           },null,null,null);
       }
       ListFoodFragmentRecyclerView.setAdapter(adapterRestaurantItems);

        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getRestaurantItems(saveData.getId_position(), 1).enqueue(new Callback<RestaurantItems>() {
            @Override
            public void onResponse(Call<RestaurantItems> call, Response<RestaurantItems> response) {
                RestaurantItems restaurantItems = response.body();
                try{
                    if (restaurantItems.getStatus() == 1) {
                        ListFoodFragmentRL.setVisibility(View.GONE);
                        ListFoodFragmentLoadingIndicator.setVisibility(View.GONE);
                        ListFoodFragmentRecyclerView.setVisibility(View.VISIBLE);
                        data2RestaurantItemsArrayList.addAll(restaurantItems.getData().getData());
                    } else {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        ListFoodFragmentLoadingIndicator.setVisibility(View.GONE);
                        ListFoodFragmentRL.setVisibility(View.VISIBLE);
                        ListFoodFragmentRecyclerView.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    ListFoodFragmentLoadingIndicator.setVisibility(View.GONE);
                    ListFoodFragmentRL.setVisibility(View.VISIBLE);
                    ListFoodFragmentRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<RestaurantItems> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                ListFoodFragmentLoadingIndicator.setVisibility(View.GONE);
                ListFoodFragmentRL.setVisibility(View.VISIBLE);
                ListFoodFragmentRecyclerView.setVisibility(View.GONE);
            }
        });

        ListFoodFragmentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("RecyclerView","OnScrollStateChanged");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}