package com.example.sofraapp.app.ui.fragment.cycleRestaurant;


import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.general.cities.Cities;
import com.example.sofraapp.app.data.model.general.cities.Data2Cities;
import com.example.sofraapp.app.data.model.general.regions.Data2Regions;
import com.example.sofraapp.app.data.model.general.regions.Regions;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.SaveData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofraapp.app.data.rest.RetrofitClient.getRetrofit;
import static com.example.sofraapp.app.helper.HelperMethod.GET_DATA;
import static com.example.sofraapp.app.ui.activity.MainActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterAsRestaurantFragmentOne extends Fragment {
    @BindView(R.id.RegisterAsRestaurantFragmentOne_Name)
    TextInputEditText RegisterAsRestaurantFragmentOneName;
    @BindView(R.id.RegisterAsRestaurantFragmentOne_SP_Select_City)
    Spinner RegisterAsRestaurantFragmentOneSPSelectCity;
    @BindView(R.id.RegisterAsRestaurantFragmentOne_SP_Select_hay)
    Spinner RegisterAsRestaurantFragmentOneSPSelectHay;
    @BindView(R.id.RegisterAsRestaurantFragmentOne_Progress_Bar)
    ProgressBar RegisterAsRestaurantFragmentOneProgressBar;
    @BindView(R.id.RegisterAsRestaurantFragmentOne_Email)
    TextInputEditText RegisterAsRestaurantFragmentOneEmail;
    @BindView(R.id.RegisterAsRestaurantFragmentOne_Password)
    TextInputEditText RegisterAsRestaurantFragmentOnePassword;
    @BindView(R.id.RegisterAsRestaurantFragmentOne_Retry_Password)
    TextInputEditText RegisterAsRestaurantFragmentOneRetryPassword;
    @BindView(R.id.RegisterAsRestaurantFragmentOne_BT_Next)
    Button RegisterAsRestaurantFragmentOneBTNext;
    @BindView(R.id.RegisterAsRestaurantFragmentOne_Address)
    TextInputEditText RegisterAsRestaurantFragmentOneAddress;
    Unbinder unbinder;
    SaveData saveData;
    APIServices apiServices;
    String getResult;
    int IDPosition;
    ArrayList<String> strings = new ArrayList<>();
    final ArrayList<Integer> IdsCity = new ArrayList<>();
    final ArrayList<Integer> IdsHay = new ArrayList<>();
    Integer positionHay;
    boolean check_network;

    public RegisterAsRestaurantFragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_as_restaurant, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        final Call<Cities> citiesCall = apiServices.getCities();
        citiesCall.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                Cities cities = response.body();
                if (cities.getStatus() == 1) {
                    try {
                        strings.add(getString(R.string.select_city));
                        IdsCity.add(0);
                        List<Data2Cities> citiesList = cities.getData().getData();
                        for (int i = 0; i < citiesList.size(); i++) {
                            getResult = citiesList.get(i).getName();
                            strings.add(getResult);
                            IDPosition = citiesList.get(i).getId();
                            IdsCity.add(IDPosition);
                        }
                        HelperMethod.showGovernorates(strings, getActivity(), RegisterAsRestaurantFragmentOneSPSelectCity);
                        RegisterAsRestaurantFragmentOneSPSelectCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    getHay(IdsCity.get(position));
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getActivity(), cities.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private void getHay(int getIdCity) {
        apiServices = getRetrofit().create(APIServices.class);
        final Call<Regions> regionsCall = apiServices.getRegions(getIdCity);
        regionsCall.enqueue(new Callback<Regions>() {
            @Override
            public void onResponse(Call<Regions> call, Response<Regions> response) {
                String getResult;
                ArrayList<String> strings = new ArrayList<>();
                Regions regions = response.body();
                if (regions.getStatus() == 1) {
                    try {
                        strings.add(getString(R.string.select_hay));
                        IdsHay.add(0);
                        List<Data2Regions> data2RegionsList = regions.getData().getData();
                        for (int i = 0; i < data2RegionsList.size(); i++) {
                            getResult = data2RegionsList.get(i).getName();
                            strings.add(getResult);
                            positionHay = data2RegionsList.get(i).getId();
                            IdsHay.add(positionHay);
                        }
                        HelperMethod.showGovernorates(strings, getActivity(), RegisterAsRestaurantFragmentOneSPSelectHay);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), regions.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Regions> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.RegisterAsRestaurantFragmentOne_BT_Next)
    public void onViewClicked() {
        String name = RegisterAsRestaurantFragmentOneName.getText().toString().trim();
        String email = RegisterAsRestaurantFragmentOneEmail.getText().toString().trim();
        String password = RegisterAsRestaurantFragmentOnePassword.getText().toString().trim();
        String address = RegisterAsRestaurantFragmentOneAddress.getText().toString().trim();
        String retryPassword = RegisterAsRestaurantFragmentOneRetryPassword.getText().toString().trim();
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || IdsCity.isEmpty() || IdsHay.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.filed_request), Toast.LENGTH_SHORT).show();
        }
        int idCity = IdsCity.get(RegisterAsRestaurantFragmentOneSPSelectCity.getSelectedItemPosition());
        int idHay = IdsHay.get(RegisterAsRestaurantFragmentOneSPSelectHay.getSelectedItemPosition());
        RegisterAsRestaurantTwoFragment registerAsRestaurantTwoFragment = new RegisterAsRestaurantTwoFragment();
        saveData.setName(name);
        saveData.setCityId(String.valueOf(idCity));
        saveData.setHayId(String.valueOf(idHay));
        saveData.setPassword(password);
        saveData.setEmail(email);
        saveData.setAddress(address);
        saveData.setRetryPassword(retryPassword);
        HelperMethod.replece(registerAsRestaurantTwoFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.create_new_user), saveData);
    }

}
