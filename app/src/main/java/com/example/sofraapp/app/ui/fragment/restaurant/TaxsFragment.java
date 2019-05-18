package com.example.sofraapp.app.ui.fragment.restaurant;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.profile.commissions.Commissions;
import com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.profile.commissions.DataCommissions;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.SaveData;

import androidx.fragment.app.Fragment;
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
public class TaxsFragment extends Fragment {
    @BindView(R.id.TaxsFragment_sales_restaurant)
    TextView TaxsFragmentSalesRestaurant;
    @BindView(R.id.TaxsFragment_taxs_app)
    TextView TaxsFragmentTaxsApp;
    @BindView(R.id.TaxsFragment_pay)
    TextView TaxsFragmentPay;
    @BindView(R.id.TaxsFragment_still)
    TextView TaxsFragmentStill;
    Unbinder unbinder;
    APIServices apiServices;
    @BindView(R.id.TaxsFragment_Loading_Indicator)
    ProgressBar TaxsFragmentLoadingIndicator;
    @BindView(R.id.TaxsFragment_tax)
    TextView TaxsFragmentTax;

    public TaxsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_taxs, container, false);
        unbinder = ButterKnife.bind(this, view);
        TaxsFragmentLoadingIndicator.setVisibility(View.VISIBLE);
     /*   if (saveData.getApi_token() != null){

        }else {
            Toast.makeText(getActivity(), R.string.login_please, Toast.LENGTH_SHORT).show();
        }*/
        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getCommissions("7jiWQQbN9afm8LTiO4VrmMObYz2lFig117PPCa1vxcK6VsXWy0pGWeq8MA4j").enqueue(new Callback<Commissions>() {
            @Override
            public void onResponse(Call<Commissions> call, Response<Commissions> response) {
                Commissions commissions = response.body();
                DataCommissions dataCommissions = commissions.getData();
                try {
                    if (commissions.getStatus() == 1) {
                        TaxsFragmentLoadingIndicator.setVisibility(View.GONE);
                        TaxsFragmentPay.append(dataCommissions.getPayments().toString());
                        TaxsFragmentSalesRestaurant.append(dataCommissions.getTotal().toString());
                        TaxsFragmentStill.append(dataCommissions.getNetCommissions().toString());
                        TaxsFragmentTaxsApp.append(dataCommissions.getCommission());
                        TaxsFragmentTax.append(""+dataCommissions.getCommission());
                    } else {
                        Toast.makeText(getActivity(), commissions.getMsg(), Toast.LENGTH_SHORT).show();
                        TaxsFragmentLoadingIndicator.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    TaxsFragmentLoadingIndicator.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Commissions> call, Throwable t) {
                TaxsFragmentLoadingIndicator.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }
}
