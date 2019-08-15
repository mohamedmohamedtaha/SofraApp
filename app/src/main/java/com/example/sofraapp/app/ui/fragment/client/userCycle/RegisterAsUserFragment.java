package com.example.sofraapp.app.ui.fragment.client.userCycle;


import android.os.Bundle;

import com.example.sofraapp.app.ui.activity.LoginActivity;
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
import com.example.sofraapp.app.data.model.client.cycleClient.registerclinet.Register;
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
public class RegisterAsUserFragment extends Fragment {
    @BindView(R.id.RegisterAsUserFragment_Name)
    TextInputEditText RegisterAsUserFragmentName;
    @BindView(R.id.RegisterAsUserFragment_Email)
    TextInputEditText RegisterAsUserFragmentEmail;
    @BindView(R.id.RegisterAsUserFragment_Phone)
    TextInputEditText RegisterAsUserFragmentPhone;
    @BindView(R.id.RegisterAsUserFragment_SP_Select_City)
    Spinner RegisterAsUserFragmentSPSelectCity;
    @BindView(R.id.RegisterAsUserFragment_S_Select_hay)
    Spinner RegisterAsUserFragmentSSelectHay;
    @BindView(R.id.RegisterAsUserFragment_Describe_Home)
    TextInputEditText RegisterAsUserFragmentDescribeHome;
    @BindView(R.id.RegisterAsUserFragment_Password)
    TextInputEditText RegisterAsUserFragmentPassword;
    @BindView(R.id.RegisterAsUserFragment_Retry_Password)
    TextInputEditText RegisterAsUserFragmentRetryPassword;
    @BindView(R.id.RegisterAsUserFragment_BT_Register)
    Button RegisterAsUserFragmentBTRegister;
    @BindView(R.id.RegisterAsUserFragment_Progress_Bar)
    ProgressBar RegisterAsUserFragmentProgressBar;
    Unbinder unbinder;
    private APIServices apiServices;
    String getResult;
    int IDPosition;
    ArrayList<String> strings = new ArrayList<>();
    final ArrayList<Integer> IdsCity = new ArrayList<>();
    final ArrayList<Integer> IdsHay = new ArrayList<>();
    Integer positionHay;

    public RegisterAsUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_as_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = getRetrofit().create(APIServices.class);
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
                        HelperMethod.showGovernorates(strings, getActivity(), RegisterAsUserFragmentSPSelectCity);
                        RegisterAsUserFragmentSPSelectCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        HelperMethod.showGovernorates(strings, getActivity(), RegisterAsUserFragmentSSelectHay);
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

    @OnClick(R.id.RegisterAsUserFragment_BT_Register)
    public void onViewClicked() {
        // for check network
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
            return;
        }
        String new_user = RegisterAsUserFragmentName.getText().toString().trim();
        String email = RegisterAsUserFragmentEmail.getText().toString().trim();
        String phone = RegisterAsUserFragmentPhone.getText().toString().trim();
        String password = RegisterAsUserFragmentPassword.getText().toString().trim();
        String retry_password = RegisterAsUserFragmentRetryPassword.getText().toString().trim();
        String describe = RegisterAsUserFragmentDescribeHome.getText().toString().trim();
        if (email.isEmpty() || new_user.isEmpty() || phone.isEmpty() || password.isEmpty() || retry_password.isEmpty()
                || describe.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.filed_request), Toast.LENGTH_SHORT).show();
            return;
        }
        if (IdsHay.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.select_elhay), Toast.LENGTH_SHORT).show();
            return;
        }
        int HayId = IdsHay.get(RegisterAsUserFragmentSSelectHay.getSelectedItemPosition());

        apiServices = getRetrofit().create(APIServices.class);
        RegisterAsUserFragmentProgressBar.setVisibility(View.VISIBLE);
        Call<Register> registerCall = apiServices.getRegister(new_user, email, password, retry_password, phone
                , describe, HayId);
        registerCall.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                try {
                    Register register = response.body();
                    if (register.getStatus() == 0) {
                        Toast.makeText(getActivity(), register.getMsg(), Toast.LENGTH_LONG).show();
                        RegisterAsUserFragmentProgressBar.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getActivity(), register.getMsg(), Toast.LENGTH_LONG).show();
                        RegisterAsUserFragmentProgressBar.setVisibility(View.GONE);
                        HelperMethod.startActivity(getActivity(), LoginActivity.class);
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    RegisterAsUserFragmentProgressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                RegisterAsUserFragmentProgressBar.setVisibility(View.GONE);
            }
        });
    }

}
