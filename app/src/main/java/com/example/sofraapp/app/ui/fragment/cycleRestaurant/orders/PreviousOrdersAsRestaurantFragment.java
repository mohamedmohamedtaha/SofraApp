package com.example.sofraapp.app.ui.fragment.cycleRestaurant.orders;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.adapter.AdapterOrdersNewCurrentPrevious;
import com.example.sofraapp.app.data.model.cycleRestaurant.myorders.Data2MyOrders;
import com.example.sofraapp.app.data.model.cycleRestaurant.myorders.MyOrders;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.SaveData;

import java.util.ArrayList;

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
public class PreviousOrdersAsRestaurantFragment extends Fragment {


    @BindView(R.id.PreviousOrdersAsRestaurantFragment_Recycler_View)
    RecyclerView PreviousOrdersAsRestaurantFragmentRecyclerView;
    @BindView(R.id.PreviousOrdersAsRestaurantFragment_RL)
    RelativeLayout PreviousOrdersAsRestaurantFragmentRL;
    @BindView(R.id.PreviousOrdersAsRestaurantFragment_Loading_Indicator)
    ProgressBar PreviousOrdersAsRestaurantFragmentLoadingIndicator;
    Unbinder unbinder;
    SaveData saveData;
    APIServices apiServices;
    AdapterOrdersNewCurrentPrevious adapterOrdersNewCurrentPrevious;
    ArrayList<Data2MyOrders> data2MyOrdersArrayList = new ArrayList<>();

    public PreviousOrdersAsRestaurantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_previous_orders_as_restaurant, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        data2MyOrdersArrayList.clear();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        PreviousOrdersAsRestaurantFragmentRecyclerView.setLayoutManager(linearLayoutManager);
        adapterOrdersNewCurrentPrevious = new AdapterOrdersNewCurrentPrevious(getActivity(), data2MyOrdersArrayList, null,
                null, null, 2);
        PreviousOrdersAsRestaurantFragmentRecyclerView.setAdapter(adapterOrdersNewCurrentPrevious);
        //  if (saveData.getApi_token() != null){
        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getMyOrders("quW3tUS7GVL5lv1BfAT0Orm4CXBtmRVREu3tCP6B5WebYsVaIQYdeoyg7yay", "pending", 1).enqueue(new Callback<MyOrders>() {
            @Override
            public void onResponse(Call<MyOrders> call, Response<MyOrders> response) {
                PreviousOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.VISIBLE);
                MyOrders data2MyOrders = response.body();
                try {
                    if (data2MyOrders.getStatus() == 1) {
                        PreviousOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);
                        PreviousOrdersAsRestaurantFragmentRL.setVisibility(View.GONE);
                        PreviousOrdersAsRestaurantFragmentRecyclerView.setVisibility(View.VISIBLE);
                        data2MyOrdersArrayList.addAll(data2MyOrders.getData().getData());
                    } else {
                        Toast.makeText(getActivity(), data2MyOrders.getMsg(), Toast.LENGTH_SHORT).show();
                        PreviousOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);
                        PreviousOrdersAsRestaurantFragmentRL.setVisibility(View.VISIBLE);
                        PreviousOrdersAsRestaurantFragmentRecyclerView.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    PreviousOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);
                    PreviousOrdersAsRestaurantFragmentRL.setVisibility(View.VISIBLE);
                    PreviousOrdersAsRestaurantFragmentRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MyOrders> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                PreviousOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);
                PreviousOrdersAsRestaurantFragmentRL.setVisibility(View.VISIBLE);
                PreviousOrdersAsRestaurantFragmentRecyclerView.setVisibility(View.GONE);
            }
        });
        //   }else {
        //     Toast.makeText(getActivity(), getString(R.string.login_please), Toast.LENGTH_SHORT).show();
        // }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
