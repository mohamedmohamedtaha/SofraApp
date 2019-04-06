package com.example.sofraapp.app.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sofraapp.R;
import com.example.sofraapp.app.adapter.AdapterRestaurantItems;
import com.example.sofraapp.app.data.model.general.restaurantitems.Data2RestaurantItems;
import com.example.sofraapp.app.data.model.general.restaurantitems.RestaurantItems;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
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
public class DetailesOrderFragment extends Fragment {


    @BindView(R.id.DetailesOrderFragment_IM_Show_Image)
    ImageView DetailesOrderFragmentIMShowImage;
    @BindView(R.id.DetailesOrderFragment_TV_Show_Name)
    TextView DetailesOrderFragmentTVShowName;
    @BindView(R.id.DetailesOrderFragment_TV_Show_Details)
    TextView DetailesOrderFragmentTVShowDetails;
    @BindView(R.id.DetailesOrderFragment_TV_Show_Price)
    TextView DetailesOrderFragmentTVShowPrice;
    @BindView(R.id.DetailesOrderFragment_TV_Price)
    TextView DetailesOrderFragmentTVPrice;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.DetailesOrderFragment_TV_Time)
    TextView DetailesOrderFragmentTVTime;
    @BindView(R.id.DetailesOrderFragment_TV_Time_Request)
    TextView DetailesOrderFragmentTVTimeRequest;
    @BindView(R.id.DetailesOrderFragment_Pricate_Request)
    TextInputEditText DetailesOrderFragmentPricateRequest;
    Unbinder unbinder;
    SaveData saveData;
    @BindView(R.id.DetailesOrderFragment_Loading_Indicator)
    ProgressBar DetailesOrderFragmentLoadingIndicator;
    private APIServices apiServices;

    public DetailesOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detailes_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getRestaurantItems(saveData.getId_position(), 1).enqueue(new Callback<RestaurantItems>() {
            @Override
            public void onResponse(Call<RestaurantItems> call, Response<RestaurantItems> response) {
                RestaurantItems restaurantItems = response.body();
                DetailesOrderFragmentLoadingIndicator.setVisibility(View.VISIBLE);
                if (restaurantItems.getStatus() == 1) {
                    DetailesOrderFragmentLoadingIndicator.setVisibility(View.GONE);
                              } else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    DetailesOrderFragmentLoadingIndicator.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<RestaurantItems> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                DetailesOrderFragmentLoadingIndicator.setVisibility(View.GONE);

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
