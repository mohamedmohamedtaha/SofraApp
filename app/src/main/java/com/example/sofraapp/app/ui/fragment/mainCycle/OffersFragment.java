package com.example.sofraapp.app.ui.fragment.mainCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.adapter.AdapterOffers;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.data.model.general.offers.Data2Offers;
import com.example.sofraapp.app.data.model.general.offers.Offers;

import java.util.ArrayList;
import java.util.List;

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
public class OffersFragment extends Fragment {


    @BindView(R.id.List_Offer)
    ListView ListOffer;
    @BindView(R.id.TV_Empty_View)
    TextView TVEmptyView;
    @BindView(R.id.Loading_Indicator)
    ProgressBar LoadingIndicator;
    Unbinder unbinder;
    private AdapterOffers adapterOffers;
    private APIServices apiServices;
    private List<Data2Offers> data2Offers = new ArrayList<>();

    public OffersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        unbinder = ButterKnife.bind(this, view);
        ListOffer.setEmptyView(TVEmptyView);
        adapterOffers = new AdapterOffers(getActivity(),data2Offers);
        ListOffer.setAdapter(adapterOffers);

        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getOffers(1).enqueue(new Callback<Offers>() {
            @Override
            public void onResponse(Call<Offers> call, Response<Offers> response) {

                Offers offers = response.body();
                if (offers.getStatus() == 1){
                    data2Offers.addAll(response.body().getData().getData());
                    adapterOffers.notifyDataSetChanged();
                    LoadingIndicator.setVisibility(View.GONE);
                }else {
                    Toast.makeText(getActivity(), offers.getMsg(), Toast.LENGTH_SHORT).show();
                    LoadingIndicator.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Offers> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                LoadingIndicator.setVisibility(View.GONE);
            }
        });
        ListOffer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
