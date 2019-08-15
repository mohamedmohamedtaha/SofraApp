package com.example.sofraapp.app.ui.fragment.restaurant;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.general.restaurantdetails.RestaurantDetails;
import com.example.sofraapp.app.data.model.general.restaurants.Data2Restaurants;
import com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.profile.changestate.ChangeState;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.RememberMy;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofraapp.app.data.rest.RetrofitClient.getRetrofit;

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
    @BindView(R.id.InformationStoreFragment_SW_State)
    Switch InformationStoreFragmentSWState;
    @BindView(R.id.InformationStoreFragment_Loading_Indicator)
    ProgressBar InformationStoreFragmentLoadingIndicator;
    Unbinder unbinder;
    RememberMy rememberMy;
    Bundle bundle;
    @BindView(R.id.InformationStoreFragment_BT_Save)
    Button InformationStoreFragmentBTSave;
    private Data2Restaurants data2Restaurants;
    private APIServices apiServices;
    String name;


    public InformationStoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information_store, container, false);
        unbinder = ButterKnife.bind(this, view);
        rememberMy = new RememberMy(getActivity());
        bundle = getArguments();
        if (bundle != null) {
            data2Restaurants = new Gson().fromJson(bundle.getString("dev"), Data2Restaurants.class);
        }
        apiServices = getRetrofit().create(APIServices.class);

        apiServices.getRestaurantDetails(data2Restaurants.getId()).enqueue(new Callback<RestaurantDetails>() {
            @Override
            public void onResponse(Call<RestaurantDetails> call, Response<RestaurantDetails> response) {
                RestaurantDetails restaurants = response.body();
                if (restaurants.getStatus() == 1) {
                    InformationStoreFragmentLoadingIndicator.setVisibility(View.GONE);
                    InformationStoreFragmentCity.setText(restaurants.getData().getRegion().getCity().getName());
                    InformationStoreFragmentPriceDelevery.append(restaurants.getData().getDeliveryCost() + getString(R.string.ryal));
                    InformationStoreFragmentMINPrice.setText(restaurants.getData().getMinimumCharger() + getString(R.string.ryal));
                    String available = restaurants.getData().getAvailability();
                    if (rememberMy.getSaveState() == 2) {
                        InformationStoreFragmentState.setVisibility(View.GONE);
                        InformationStoreFragmentBTSave.setVisibility(View.VISIBLE);
                        if (available.equals(getString(R.string.open_en))) {
                            InformationStoreFragmentSWState.setChecked(true);
                            InformationStoreFragmentSWState.setText(R.string.open_ar);
                            InformationStoreFragmentSWState.setTextColor(getResources().getColor(R.color.green_complete));
                        } else {
                            InformationStoreFragmentSWState.setChecked(false);
                            InformationStoreFragmentSWState.setText(R.string.close_ar);
                            InformationStoreFragmentSWState.setTextColor(getResources().getColor(R.color.red_error));
                        }
                    } else {
                        InformationStoreFragmentSWState.setVisibility(View.GONE);
                        InformationStoreFragmentBTSave.setVisibility(View.GONE);
                        if (available.equals(getString(R.string.open_en))) {
                            InformationStoreFragmentState.setText(R.string.open_ar);
                            InformationStoreFragmentState.setTextColor(getResources().getColor(R.color.green_complete));
                        } else {
                            InformationStoreFragmentState.setText(R.string.close_ar);
                            InformationStoreFragmentState.setTextColor(getResources().getColor(R.color.red_error));
                        }
                    }
                    InformationStoreFragmentHay.setText(restaurants.getData().getRegion().getName());
                } else {
                    Toast.makeText(getActivity(), restaurants.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantDetails> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        InformationStoreFragmentSWState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                name = null;
                if (isChecked) {
                    name = getString(R.string.open_en);
                    InformationStoreFragmentSWState.setText(R.string.open_ar);
                    InformationStoreFragmentSWState.setTextColor(getResources().getColor(R.color.green_complete));
                } else {
                    InformationStoreFragmentSWState.setText(R.string.close_ar);
                    InformationStoreFragmentSWState.setTextColor(getResources().getColor(R.color.red_error));
                    name = getString(R.string.closed);
                }

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.InformationStoreFragment_BT_Save)
    public void onViewClicked() {
        InformationStoreFragmentLoadingIndicator.setVisibility(View.VISIBLE);
        apiServices.changeState(name, rememberMy.getAPIKey()).enqueue(new Callback<ChangeState>() {
            @Override
            public void onResponse(Call<ChangeState> call, Response<ChangeState> response) {
                ChangeState changeState = response.body();
                try {
                    if (changeState.getStatus() == 1) {
                  //      Toast.makeText(getActivity(), changeState.getMsg(), Toast.LENGTH_SHORT).show();
                        InformationStoreFragmentLoadingIndicator.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getActivity(), changeState.getMsg(), Toast.LENGTH_SHORT).show();
                        InformationStoreFragmentLoadingIndicator.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    if (InformationStoreFragmentLoadingIndicator != null) {
                        InformationStoreFragmentLoadingIndicator.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onFailure(Call<ChangeState> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                InformationStoreFragmentLoadingIndicator.setVisibility(View.GONE);
            }
        });
    }
}
