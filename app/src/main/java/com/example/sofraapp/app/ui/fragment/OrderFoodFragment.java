package com.example.sofraapp.app.ui.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.adapter.AdapterOrderFood;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.data.model.general.restaurants.Data2Restaurants;
import com.example.sofraapp.app.data.model.general.restaurants.Restaurants;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.Model;
import com.example.sofraapp.app.helper.SaveData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofraapp.app.data.rest.RetrofitClient.getRetrofit;
import static com.example.sofraapp.app.helper.HelperMethod.GET_DATA;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFoodFragment extends Fragment {
    @BindView(R.id.OrderFoodFragment_Recycler_View)
    RecyclerView OrderFoodFragmentRecyclerView;
    @BindView(R.id.IV_Empty_Image)
    ImageView IVEmptyImage;
    @BindView(R.id.RL_Empty_View_OrderFoodFragment)
    RelativeLayout RLEmptyViewOrderFoodFragment;
    @BindView(R.id.PB_Loading_Indicator)
    ProgressBar PBLoadingIndicator;
    Unbinder unbinder;
    private APIServices apiServices;
    private ArrayList<Data2Restaurants> restaurantsArrayList = new ArrayList<>();
    private AdapterOrderFood adapterOrderFood;
    SaveData saveData;
    public static final String SAVE_ID_POSITION = "id_position";
    Model model;

    public OrderFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_food, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        restaurantsArrayList.clear();
        apiServices = getRetrofit().create(APIServices.class);
        PBLoadingIndicator.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        OrderFoodFragmentRecyclerView.setLayoutManager(linearLayoutManager);
        adapterOrderFood = new AdapterOrderFood(getActivity(), restaurantsArrayList, new AdapterOrderFood.showDetial() {
            @Override
            public void itemShowDetail(Data2Restaurants position) {
                int detailRestaurant = position.getId();
                DetailRestaurantFragment detailRestaurantFragment = new DetailRestaurantFragment();
                saveData = new SaveData(saveData.getSave_state(),detailRestaurant);
                HelperMethod.replece(detailRestaurantFragment,getActivity().getSupportFragmentManager(),R.id.Cycle_Home_contener,null,null,saveData);
            }
        });
        OrderFoodFragmentRecyclerView.setAdapter(adapterOrderFood);

        apiServices.getRestaurants(1).enqueue(new Callback<Restaurants>() {
            @Override
            public void onResponse(Call<Restaurants> call, Response<Restaurants> response) {
                try {
                    Restaurants restaurants = response.body();
                    if (restaurants.getStatus() == 1) {
                        OrderFoodFragmentRecyclerView.setVisibility(View.VISIBLE);
                        PBLoadingIndicator.setVisibility(View.GONE);
                        RLEmptyViewOrderFoodFragment.setVisibility(View.GONE);
                        restaurantsArrayList.addAll(restaurants.getData().getData());
                        adapterOrderFood.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), restaurants.getMsg(), Toast.LENGTH_SHORT).show();
                        OrderFoodFragmentRecyclerView.setVisibility(View.GONE);
                        PBLoadingIndicator.setVisibility(View.GONE);
                        RLEmptyViewOrderFoodFragment.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    OrderFoodFragmentRecyclerView.setVisibility(View.GONE);
                    PBLoadingIndicator.setVisibility(View.GONE);
                    RLEmptyViewOrderFoodFragment.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Restaurants> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                OrderFoodFragmentRecyclerView.setVisibility(View.GONE);
                PBLoadingIndicator.setVisibility(View.GONE);
                RLEmptyViewOrderFoodFragment.setVisibility(View.VISIBLE);


            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
