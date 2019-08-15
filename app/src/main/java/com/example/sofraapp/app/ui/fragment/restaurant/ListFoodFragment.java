package com.example.sofraapp.app.ui.fragment.restaurant;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.util.Attributes;
import com.example.sofraapp.R;
import com.example.sofraapp.app.adapter.AdapterRestaurantItems;
import com.example.sofraapp.app.data.model.general.restaurantitems.Data2RestaurantItems;
import com.example.sofraapp.app.data.model.general.restaurantitems.RestaurantItems;
import com.example.sofraapp.app.data.model.general.restaurants.Data2Restaurants;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.Model;
import com.example.sofraapp.app.helper.RememberMy;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofraapp.app.data.rest.RetrofitClient.getRetrofit;

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
    private Data2Restaurants data2Restaurants;

    ArrayList<Data2RestaurantItems> data2RestaurantItemsArrayList = new ArrayList<>();
    Bundle bundle;

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
        bundle = getArguments();
        if (bundle != null) {
            data2Restaurants = new Gson().fromJson(bundle.getString("dev"), Data2Restaurants.class);
        }

        data2RestaurantItemsArrayList.clear();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        ListFoodFragmentRecyclerView.setLayoutManager(linearLayoutManager);
        ListFoodFragmentRecyclerView.setItemAnimator(new DefaultItemAnimator());

        ListFoodFragmentRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        ListFoodFragmentLoadingIndicator.setVisibility(View.VISIBLE);

        adapterRestaurantItems = new AdapterRestaurantItems(getActivity(), data2RestaurantItemsArrayList, data2Restaurants);
        adapterRestaurantItems.setMode(Attributes.Mode.Single);
        ListFoodFragmentRecyclerView.setAdapter(adapterRestaurantItems);

        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getRestaurantItems(data2Restaurants.getId(), 1).enqueue(new Callback<RestaurantItems>() {
            @Override
            public void onResponse(Call<RestaurantItems> call, Response<RestaurantItems> response) {
                RestaurantItems restaurantItems = response.body();
                try {
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
                } catch (Exception e) {
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
                Log.e("RecyclerView", "OnScrollStateChanged");
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