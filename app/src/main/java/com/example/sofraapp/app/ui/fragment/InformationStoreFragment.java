package com.example.sofraapp.app.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.general.restaurantdetails.RestaurantDetails;
import com.example.sofraapp.app.data.model.general.restaurants.Data2Restaurants;
import com.example.sofraapp.app.data.model.general.restaurants.Restaurants;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.SaveData;

import java.util.List;

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
public class InformationStoreFragment extends Fragment {


    @BindView(R.id.InformationStoreFragment_State)
    TextView InformationStoreFragmentState;
    @BindView(R.id.InformationStoreFragment_City)
    TextView InformationStoreFragmentCity;
    @BindView(R.id.InformationStoreFragment_Hay)
    TextView InformationStoreFragmentHay;
    @BindView(R.id.InformationStoreFragment_MIN_price)
    TextView InformationStoreFragmentMINPrice;
    @BindView(R.id.InformationStoreFragment_Price_Delevery)
    TextView InformationStoreFragmentPriceDelevery;
    Unbinder unbinder;
    SaveData saveData;

    public InformationStoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information_store, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        APIServices apiServices = getRetrofit().create(APIServices.class);
        apiServices.getRestaurantDetails(saveData.getId_position()).enqueue(new Callback<RestaurantDetails>() {
            @Override
            public void onResponse(Call<RestaurantDetails> call, Response<RestaurantDetails> response) {
                RestaurantDetails restaurants = response.body();
                if (restaurants.getStatus() == 1){
                    InformationStoreFragmentCity.setText(restaurants.getData().getRegion().getCity().getName());
                    InformationStoreFragmentPriceDelevery.append(restaurants.getData().getDeliveryCost()+ getString(R.string.ryal));
                    InformationStoreFragmentMINPrice.setText(restaurants.getData().getMinimumCharger()+ getString(R.string.ryal));
                    String available = restaurants.getData().getAvailability();
                    if (available.equals(getString(R.string.open_en))){
                        InformationStoreFragmentState.setText(R.string.open_ar);
                        InformationStoreFragmentState.setTextColor(getResources().getColor(R.color.green_complete));
                    }else {
                        InformationStoreFragmentState.setText(R.string.close_ar);
                        InformationStoreFragmentState.setTextColor(getResources().getColor(R.color.red_error));

                    }
                    InformationStoreFragmentHay.setText(restaurants.getData().getRegion().getName());
                }else {
                    Toast.makeText(getActivity(), restaurants.getMsg(), Toast.LENGTH_SHORT).show();
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
