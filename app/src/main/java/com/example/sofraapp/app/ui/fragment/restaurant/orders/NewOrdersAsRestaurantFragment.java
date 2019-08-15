package com.example.sofraapp.app.ui.fragment.restaurant.orders;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.adapter.AdapterOrdersNewCurrentPrevious;
import com.example.sofraapp.app.data.model.restaurant.order.acceptorder.AcceptOrder;
import com.example.sofraapp.app.data.model.restaurant.order.myorders.Data2MyOrders;
import com.example.sofraapp.app.data.model.restaurant.order.myorders.MyOrders;
import com.example.sofraapp.app.data.model.restaurant.order.rejectorder.RejectOrder;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.RememberMy;
import com.example.sofraapp.app.helper.SaveData;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
public class NewOrdersAsRestaurantFragment extends Fragment {
    @BindView(R.id.NewOrdersAsRestaurantFragment_Recycler_View)
    RecyclerView NewOrdersAsRestaurantFragmentRecyclerView;
    @BindView(R.id.NewOrdersAsRestaurantFragment_Empty_Image)
    ImageView NewOrdersAsRestaurantFragmentEmptyImage;
    @BindView(R.id.NewOrdersAsRestaurantFragment_RL)
    RelativeLayout NewOrdersAsRestaurantFragmentRL;
    @BindView(R.id.NewOrdersAsRestaurantFragment_Loading_Indicator)
    ProgressBar NewOrdersAsRestaurantFragmentLoadingIndicator;
    Unbinder unbinder;
    AdapterOrdersNewCurrentPrevious adapterOrdersNewCurrentPrevious;
    ArrayList<Data2MyOrders> data2MyOrdersArrayList = new ArrayList<>();
    APIServices apiServices;
    RememberMy rememberMy;
    public NewOrdersAsRestaurantFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_orders, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = getRetrofit().create(APIServices.class);
        rememberMy = new RememberMy(getActivity());
        data2MyOrdersArrayList.clear();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        NewOrdersAsRestaurantFragmentRecyclerView.setLayoutManager(linearLayoutManager);
        adapterOrdersNewCurrentPrevious = new AdapterOrdersNewCurrentPrevious(getActivity(), data2MyOrdersArrayList,
                new AdapterOrdersNewCurrentPrevious.call() {
                    @Override
                    public void itemCall(Data2MyOrders data2MyOrders) {
                        String itemMyOrders1 = data2MyOrders.getClient().getPhone();
                        HelperMethod.makePhoneCall(getActivity(), itemMyOrders1);
                    }
                },
                new AdapterOrdersNewCurrentPrevious.accept() {
                    @Override
                    public void itemAccept(Data2MyOrders data2MyOrders) {
                        String order_id = data2MyOrders.getId().toString();
                        Toast.makeText(getActivity(), order_id, Toast.LENGTH_SHORT).show();
                        NewOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.VISIBLE);
                        apiServices.acceptOrder(rememberMy.getAPIKey(), order_id)
                                .enqueue(new Callback<AcceptOrder>() {
                                    @Override
                                    public void onResponse(Call<AcceptOrder> call, Response<AcceptOrder> response) {
                                        AcceptOrder acceptOrder = response.body();
                                        try {
                                            if (acceptOrder.getStatus() == 1) {
                                                NewOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);
                                                Toast.makeText(getActivity(), acceptOrder.getMsg(), Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getActivity(), acceptOrder.getMsg(), Toast.LENGTH_SHORT).show();
                                                NewOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);

                                            }
                                        } catch (Exception e) {
                                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            NewOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<AcceptOrder> call, Throwable t) {
                                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                        NewOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);
                                    }
                                });

                    }
                }, new AdapterOrdersNewCurrentPrevious.reject() {
            @Override
            public void itemReject(Data2MyOrders data2MyOrders) {
                String order_id = data2MyOrders.getId().toString();
                Toast.makeText(getActivity(), order_id, Toast.LENGTH_SHORT).show();
                NewOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.VISIBLE);
                apiServices.rejectOrder(rememberMy.getAPIKey(), order_id)
                        .enqueue(new Callback<RejectOrder>() {
                            @Override
                            public void onResponse(Call<RejectOrder> call, Response<RejectOrder> response) {
                                RejectOrder rejectOrder = response.body();
                                try {
                                    if (rejectOrder.getStatus() == 1) {
                                        NewOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), rejectOrder.getMsg(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), rejectOrder.getMsg(), Toast.LENGTH_SHORT).show();
                                        NewOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);

                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    NewOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onFailure(Call<RejectOrder> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                NewOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);
                            }
                        });

            }
        }, 0);
        NewOrdersAsRestaurantFragmentRecyclerView.setAdapter(adapterOrdersNewCurrentPrevious);
          if (rememberMy.getAPIKey() != null){
        NewOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.VISIBLE);
        apiServices.getMyOrders(rememberMy.getAPIKey(), "pending", 1).enqueue(new Callback<MyOrders>() {
            @Override
            public void onResponse(Call<MyOrders> call, Response<MyOrders> response) {
                MyOrders data2MyOrders = response.body();
                try {
                    if (data2MyOrders.getStatus() == 1) {
                        NewOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);
                        NewOrdersAsRestaurantFragmentRL.setVisibility(View.GONE);
                        NewOrdersAsRestaurantFragmentRecyclerView.setVisibility(View.VISIBLE);
                        data2MyOrdersArrayList.addAll(data2MyOrders.getData().getData());
                    } else {
                        Toast.makeText(getActivity(), data2MyOrders.getMsg(), Toast.LENGTH_SHORT).show();
                        NewOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);
                        NewOrdersAsRestaurantFragmentRL.setVisibility(View.VISIBLE);
                        NewOrdersAsRestaurantFragmentRecyclerView.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    NewOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);
                    NewOrdersAsRestaurantFragmentRL.setVisibility(View.VISIBLE);
                    NewOrdersAsRestaurantFragmentRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MyOrders> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                NewOrdersAsRestaurantFragmentLoadingIndicator.setVisibility(View.GONE);
                NewOrdersAsRestaurantFragmentRL.setVisibility(View.VISIBLE);
                NewOrdersAsRestaurantFragmentRecyclerView.setVisibility(View.GONE);
            }
        });
           }else {
             Toast.makeText(getActivity(), getString(R.string.login_please), Toast.LENGTH_SHORT).show();
         }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
