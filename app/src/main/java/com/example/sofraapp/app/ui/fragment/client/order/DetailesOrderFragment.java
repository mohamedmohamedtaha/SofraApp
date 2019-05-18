package com.example.sofraapp.app.ui.fragment.client.order;


import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.general.restaurantitems.RestaurantItems;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.Model;
import com.example.sofraapp.app.helper.SaveData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofraapp.app.data.rest.RetrofitClient.getRetrofit;
import static com.example.sofraapp.app.helper.HelperMethod.GET_MODEL;

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

    @BindView(R.id.DetailesOrderFragment_Loading_Indicator)
    ProgressBar DetailesOrderFragmentLoadingIndicator;
    @BindView(R.id.DetailesOrderFragment_BT_Sum)
    Button DetailesOrderFragmentBTSum;
    @BindView(R.id.DetailesOrderFragment_TV_Count)
    TextView DetailesOrderFragmentTVCount;
    @BindView(R.id.DetailesOrderFragment_BT_Plus)
    Button DetailesOrderFragmentBTPlus;
    @BindView(R.id.DetailesOrderFragment_BT_Add_Car)
    Button DetailesOrderFragmentBTAddCar;
    Unbinder unbinder;
    private APIServices apiServices;
    Model model;
    private static int count_orders;

    public DetailesOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detailes_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        model = getArguments().getParcelable(GET_MODEL);
        apiServices = getRetrofit().create(APIServices.class);
        DetailesOrderFragmentLoadingIndicator.setVisibility(View.VISIBLE);
        apiServices.getRestaurantItems(model.getId(), 1).enqueue(new Callback<RestaurantItems>() {
            @Override
            public void onResponse(Call<RestaurantItems> call, Response<RestaurantItems> response) {
                RestaurantItems restaurantItems = response.body();
                if (restaurantItems.getStatus() == 1) {
                    DetailesOrderFragmentLoadingIndicator.setVisibility(View.GONE);
                    Glide.with(getContext()).load(model.getPhoto()).into(DetailesOrderFragmentIMShowImage);
                    DetailesOrderFragmentTVShowName.setText(model.getTitle());
                    DetailesOrderFragmentTVPrice.setText("" + model.getPrice());
                    DetailesOrderFragmentTVShowDetails.setText(model.getDescribe());
                    DetailesOrderFragmentTVTimeRequest.setText(model.getPeriod_delivery());
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

    @OnClick({R.id.DetailesOrderFragment_BT_Sum, R.id.DetailesOrderFragment_BT_Plus, R.id.DetailesOrderFragment_BT_Add_Car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.DetailesOrderFragment_BT_Sum:
                if (count_orders >= 50){
                    Toast.makeText(getActivity(), getString(R.string.not_allowed_alot), Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    count_orders++ ;
                    DetailesOrderFragmentTVCount.setText(""+count_orders);
                }
                break;
            case R.id.DetailesOrderFragment_BT_Plus:
                if (count_orders <= 1){
                    Toast.makeText(getActivity(), getString(R.string.not_allowed), Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    count_orders-- ;
                    DetailesOrderFragmentTVCount.setText(""+count_orders);
                }
                break;
            case R.id.DetailesOrderFragment_BT_Add_Car:
                break;
        }
    }
}
