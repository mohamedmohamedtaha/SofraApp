package com.example.sofraapp.app.ui.fragment;


import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sofraapp.R;
import com.example.sofraapp.app.adapter.AdapterDetailsFood;
import com.example.sofraapp.app.data.model.general.restaurantdetails.RestaurantDetails;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.Model;
import com.example.sofraapp.app.helper.SaveData;

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
public class DetailRestaurantFragment extends Fragment {
    @BindView(R.id.Detail_Restaurant_Fragment_IM_Show_Image)
    ImageView DetailRestaurantFragmentIMShowImage;
    @BindView(R.id.Detail_Restaurant_Fragment_TV_Show_Name_Restaurant)
    TextView DetailRestaurantFragmentTVShowNameRestaurant;
    @BindView(R.id.Detail_Restaurant_Fragment_TV_Show_Types)
    TextView DetailRestaurantFragmentTVShowTypes;
    @BindView(R.id.Detail_Restaurant_Fragment_RB_Rate_Restaurant)
    RatingBar DetailRestaurantFragmentRBRateRestaurant;
    @BindView(R.id.Detail_Restaurant_Fragment_TV_Is_Open)
    TextView DetailRestaurantFragmentTVIsOpen;
    @BindView(R.id.Detail_Restaurant_Fragment_TV_Min_Order)
    TextView DetailRestaurantFragmentTVMinOrder;
    @BindView(R.id.Detail_Restaurant_Fragment_TV_Price_Delevery)
    TextView DetailRestaurantFragmentTVPriceDelevery;
    @BindView(R.id.Detail_Restaurant_Fragment_TabLayout)
    TabLayout DetailRestaurantFragmentTabLayout;
    @BindView(R.id.Detail_Restaurant_Fragment_ViewPager)
    ViewPager DetailRestaurantFragmentViewPager;
    Unbinder unbinder;
    Bundle bundle;
    SaveData saveData;
    private APIServices apiServices;
    Model model;

    public DetailRestaurantFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_restaurant, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);

        AdapterDetailsFood adapterDetailsFood = new AdapterDetailsFood(getActivity(), getChildFragmentManager(), saveData);
        DetailRestaurantFragmentViewPager.setAdapter(adapterDetailsFood);
        DetailRestaurantFragmentTabLayout.setupWithViewPager(DetailRestaurantFragmentViewPager);
        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getRestaurantDetails(saveData.getId_position()).enqueue(new Callback<RestaurantDetails>() {
            @Override
            public void onResponse(Call<RestaurantDetails> call, Response<RestaurantDetails> response) {

                RestaurantDetails restaurantDetails = response.body();
                if (restaurantDetails.getStatus() == 1) {
                    Glide.with(getContext()).load(restaurantDetails.getData().getPhotoUrl()).into(DetailRestaurantFragmentIMShowImage);
                    DetailRestaurantFragmentTVShowNameRestaurant.setText(restaurantDetails.getData().getName());
                    DetailRestaurantFragmentTVMinOrder.setText(restaurantDetails.getData().getMinimumCharger());
                    DetailRestaurantFragmentTVPriceDelevery.setText(restaurantDetails.getData().getDeliveryCost());
                    HelperMethod.getReating(Integer.parseInt(restaurantDetails.getData().getRate()), DetailRestaurantFragmentRBRateRestaurant);
                    String isAvailable = restaurantDetails.getData().getAvailability();

                    if (isAvailable.equals(getString(R.string.closed))) {
                        DetailRestaurantFragmentTVIsOpen.setTextColor(getResources().getColor(R.color.holo_red_light));
                        DetailRestaurantFragmentTVIsOpen.setText(isAvailable);
                    } else {
                        DetailRestaurantFragmentTVIsOpen.setText(isAvailable);
                    }

                } else {
                    Toast.makeText(getActivity(), restaurantDetails.getMsg(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<RestaurantDetails> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

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
















