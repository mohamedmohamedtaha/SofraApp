package com.example.sofraapp.app.ui.fragment.restaurant.offers;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.adapter.AdapterMyProducts;
import com.example.sofraapp.app.data.model.restaurant.offers.myoffers.Data2MyOffers;
import com.example.sofraapp.app.data.model.restaurant.offers.myoffers.MyOffers;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.RememberMy;
import com.example.sofraapp.app.helper.SaveData;
import com.example.sofraapp.app.ui.fragment.restaurant.offers.AddOfferFragment;

import java.util.ArrayList;

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
public class MyOffersFragment extends Fragment {
    @BindView(R.id.MyOffersFragment_Recycler_View)
    RecyclerView MyOffersFragmentRecyclerView;
    @BindView(R.id.MyOffersFragment_Empty_Image)
    ImageView MyOffersFragmentEmptyImage;
    @BindView(R.id.MyOffersFragment_TV_Empty_View)
    TextView MyOffersFragmentTVEmptyView;
    @BindView(R.id.MyOffersFragment_RL)
    RelativeLayout MyOffersFragmentRL;
    @BindView(R.id.MyOffersFragment_Loading_Indicator)
    ProgressBar MyOffersFragmentLoadingIndicator;
    @BindView(R.id.MyOffersFragment_BT_Add_New_Offer)
    Button MyOffersFragmentBTAddNewOffer;
    Unbinder unbinder;
    private AdapterMyProducts adapterMyOffers;
    ArrayList<Data2MyOffers> data2MyOffersArrayList = new ArrayList<>();
    APIServices apiServices;
    private RememberMy rememberMy;
    public static final String DIALOG_OFFERS= "dialog_offers";
    public MyOffersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_offers, container, false);
        unbinder = ButterKnife.bind(this, view);
        rememberMy = new RememberMy(getActivity());
        data2MyOffersArrayList.clear();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        MyOffersFragmentRecyclerView.setLayoutManager(linearLayoutManager);
        adapterMyOffers = new AdapterMyProducts(getActivity(), data2MyOffersArrayList,true);
        MyOffersFragmentRecyclerView.setAdapter(adapterMyOffers);
        apiServices = getRetrofit().create(APIServices.class);
        //z69wj11jkMZFscNbxgDhIp7YHfxnC7j7plY2EJqWHMModsJ9hMfmo7y0bzLt
        apiServices.getMyOffers(rememberMy.getAPIKey(),1).enqueue(new Callback<MyOffers>() {
            @Override
            public void onResponse(Call<MyOffers> call, Response<MyOffers> response) {
                MyOffersFragmentLoadingIndicator.setVisibility(View.VISIBLE);
                MyOffers myOffers = response.body();
                if (myOffers.getStatus() == 1 ){
                    MyOffersFragmentRecyclerView.setVisibility(View.VISIBLE);
                    MyOffersFragmentLoadingIndicator.setVisibility(View.GONE);
                    MyOffersFragmentRL.setVisibility(View.GONE);
                    data2MyOffersArrayList.addAll(myOffers.getData().getData());
                    adapterMyOffers.notifyDataSetChanged();

                }else {
                    Toast.makeText(getActivity(), myOffers.getMsg(), Toast.LENGTH_SHORT).show();
                    getProperties();
                }
            }

            @Override
            public void onFailure(Call<MyOffers> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                getProperties();
            }
        });


        return view;
    }
    public void getProperties() {
        MyOffersFragmentRecyclerView.setVisibility(View.GONE);
        MyOffersFragmentLoadingIndicator.setVisibility(View.GONE);
        MyOffersFragmentRL.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.MyOffersFragment_BT_Add_New_Offer)
    public void onViewClicked() {
        new AddOfferFragment().show(getFragmentManager(),DIALOG_OFFERS);

    }
}
