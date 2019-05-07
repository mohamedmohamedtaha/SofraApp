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
public class CurrentOrdersAsRestaurantFragment extends Fragment {


    @BindView(R.id.CurrentOrdersAsRestaurantFragment_Recycler_View)
    RecyclerView CurrentOrdersAsRestaurantFragmentRecyclerView;
    @BindView(R.id.CurrentOrdersAsRestaurantFragment_RL)
    RelativeLayout CurrentOrdersAsRestaurantFragmentRL;
    @BindView(R.id.CurrentOrdersAsRestaurantFragment_Loading_Indicator)
    ProgressBar CurrentOrdersAsRestaurantFragmentLoadingIndicator;
    Unbinder unbinder;
    SaveData saveData;
    APIServices apiServices;
    AdapterOrdersNewCurrentPrevious adapterOrdersNewCurrentPrevious;
    ArrayList<Data2MyOrders>data2MyOrdersArrayList = new ArrayList<>();

    public CurrentOrdersAsRestaurantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_orders, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        data2MyOrdersArrayList.clear();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        CurrentOrdersAsRestaurantFragmentRecyclerView.setLayoutManager(linearLayoutManager);
        adapterOrdersNewCurrentPrevious = new AdapterOrdersNewCurrentPrevious(getActivity(), data2MyOrdersArrayList, null,
                null, null, 1);
        CurrentOrdersAsRestaurantFragmentRecyclerView.setAdapter(adapterOrdersNewCurrentPrevious);
        //  if (saveData.getApi_token() != null){
        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getMyOrders("quW3tUS7GVL5lv1BfAT0Orm4CXBtmRVREu3tCP6B5WebYsVaIQYdeoyg7yay", "pending", 1).enqueue(new Callback<MyOrders>() {
            @Override
            public void onResponse(Call<MyOrders> call, Response<MyOrders> response) {
                CurrentOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.VISIBLE);
                MyOrders data2MyOrders = response.body();
                try {
                    if (data2MyOrders.getStatus() == 1) {
                        CurrentOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);
                        CurrentOrdersAsRestaurantFragmentRL.setVisibility(View.GONE);
                        CurrentOrdersAsRestaurantFragmentRecyclerView.setVisibility(View.VISIBLE);
                        data2MyOrdersArrayList.addAll(data2MyOrders.getData().getData());
                    } else {
                        Toast.makeText(getActivity(), data2MyOrders.getMsg(), Toast.LENGTH_SHORT).show();
                        CurrentOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);
                        CurrentOrdersAsRestaurantFragmentRL.setVisibility(View.VISIBLE);
                        CurrentOrdersAsRestaurantFragmentRecyclerView.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    CurrentOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);
                    CurrentOrdersAsRestaurantFragmentRL.setVisibility(View.VISIBLE);
                    CurrentOrdersAsRestaurantFragmentRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MyOrders> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                CurrentOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);
                CurrentOrdersAsRestaurantFragmentRL.setVisibility(View.VISIBLE);
                CurrentOrdersAsRestaurantFragmentRecyclerView.setVisibility(View.GONE);
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
