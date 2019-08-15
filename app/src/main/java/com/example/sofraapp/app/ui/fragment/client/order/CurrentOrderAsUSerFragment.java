package com.example.sofraapp.app.ui.fragment.client.order;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.adapter.AdapterMyOrderAndPreviousCustom;
import com.example.sofraapp.app.data.model.client.order.confirmorder.ConfirmOrder;
import com.example.sofraapp.app.data.model.client.order.declineorder.DeclineOrder;
import com.example.sofraapp.app.data.model.client.order.myordersasuser.Data2MyOrdersAsUser;
import com.example.sofraapp.app.data.model.client.order.myordersasuser.MyOrdersAsUser;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.RememberMy;
import com.example.sofraapp.app.helper.SaveData;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofraapp.app.data.rest.RetrofitClient.getRetrofit;
import static com.example.sofraapp.app.ui.activity.MainActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentOrderAsUSerFragment extends Fragment {
    @BindView(R.id.CurrentOrderAsUSerFragment_List_View)
    ListView CurrentOrderAsUSerFragment_List_View;
    @BindView(R.id.CurrentOrderAsUSerFragment_IV_Empty_Image)
    ImageView CurrentOrderAsUSerFragmentIVEmptyImage;
    @BindView(R.id.CurrentOrderAsUSerFragment_RL_Empty_View)
    RelativeLayout CurrentOrderAsUSerFragmentRLEmptyView;
    @BindView(R.id.CurrentOrderAsUSerFragment_PB_Loading_Indicator)
    ProgressBar CurrentOrderAsUSerFragmentPBLoadingIndicator;
    Unbinder unbinder;
    private APIServices apiServices;
    private List<Data2MyOrdersAsUser> myOrdersAsUserArrayList = new ArrayList<>();
    private AdapterMyOrderAndPreviousCustom adapterMyOrderAndPreviousCustom;
    public static final String ORDER_ID = "order_id";
    RememberMy rememberMy;

    public CurrentOrderAsUSerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_order_as_u, container, false);
        unbinder = ButterKnife.bind(this, view);
        rememberMy = new RememberMy(getActivity());
        myOrdersAsUserArrayList.clear();
        CurrentOrderAsUSerFragment_List_View.setEmptyView(CurrentOrderAsUSerFragmentRLEmptyView);
          if (rememberMy.getAPIKey() != null){
        CurrentOrderAsUSerFragmentPBLoadingIndicator.setVisibility(View.VISIBLE);
        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getMyOrdersAsUser(rememberMy.getAPIKey(), "current", 1).enqueue(new Callback<MyOrdersAsUser>() {
            @Override
            public void onResponse(Call<MyOrdersAsUser> call, Response<MyOrdersAsUser> response) {
                MyOrdersAsUser myOrdersAsUser = response.body();
                myOrdersAsUserArrayList = myOrdersAsUser.getData().getData();
                if (myOrdersAsUser.getStatus() == 1) {
                    CurrentOrderAsUSerFragmentPBLoadingIndicator.setVisibility(View.GONE);
                    adapterMyOrderAndPreviousCustom = new AdapterMyOrderAndPreviousCustom(getActivity(), myOrdersAsUserArrayList
                            , new AdapterMyOrderAndPreviousCustom.showDetails() {
                        @Override
                        public void showDetail(Data2MyOrdersAsUser position) {
                            int order_id = position.getId();
                            SaveData saveData = new SaveData(order_id);
                            ShowDetailsOrderFragment showDetailsOrderFragment = new ShowDetailsOrderFragment();
                            HelperMethod.replece(showDetailsOrderFragment, getActivity().getSupportFragmentManager(),
                                    R.id.Cycle_Home_contener, toolbar, position.getRestaurant().getName(), saveData);
                        }
                    }, new AdapterMyOrderAndPreviousCustom.done() {
                        @Override
                        public void buttonDone(Data2MyOrdersAsUser position) {
                            int data = position.getId();
                            apiServices.ConfirmOrder(/*saveData.getApi_token()*/
                                    rememberMy.getAPIKey(), data).enqueue(new Callback<ConfirmOrder>() {
                                @Override
                                public void onResponse(Call<ConfirmOrder> call, Response<ConfirmOrder> response) {
                                    ConfirmOrder confirmOrder = response.body();
                                    try {
                                        if (confirmOrder.getStatus() == 1) {
                                            Toast.makeText(getActivity(), confirmOrder.getMsg(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), confirmOrder.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ConfirmOrder> call, Throwable t) {
                                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }, new AdapterMyOrderAndPreviousCustom.reject() {
                        @Override
                        public void buttonReject(Data2MyOrdersAsUser position) {
                            int order_id = position.getId();
                            apiServices.declineOrder(/*saveData.getApi_token()*/
                                    rememberMy.getAPIKey(), order_id).enqueue(new Callback<DeclineOrder>() {
                                @Override
                                public void onResponse(Call<DeclineOrder> call, Response<DeclineOrder> response) {
                                    DeclineOrder declineOrder = response.body();
                                    try {
                                        if (declineOrder.getStatus() == 1) {
                                            Toast.makeText(getActivity(), declineOrder.getMsg(), Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getActivity(), declineOrder.getMsg(), Toast.LENGTH_LONG).show();
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<DeclineOrder> call, Throwable t) {
                                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }, false);
                    CurrentOrderAsUSerFragment_List_View.setAdapter(adapterMyOrderAndPreviousCustom);
                } else {
                    Toast.makeText(getActivity(), myOrdersAsUser.getMsg(), Toast.LENGTH_SHORT).show();
                    CurrentOrderAsUSerFragmentPBLoadingIndicator.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MyOrdersAsUser> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                CurrentOrderAsUSerFragmentPBLoadingIndicator.setVisibility(View.GONE);
            }
        });
        }else {
              Toast.makeText(getActivity(), getString(R.string.must_login), Toast.LENGTH_SHORT).show();
         }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}