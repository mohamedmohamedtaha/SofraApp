package com.example.sofraapp.app.ui.fragment.client.order;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofraapp.R;
import com.example.sofraapp.app.adapter.OrderListAdapter;
import com.example.sofraapp.app.data.model.general.restaurantitems.Data2RestaurantItems;
import com.example.sofraapp.app.data.model.general.restaurants.Data2Restaurants;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.Model;
import com.example.sofraapp.app.helper.RememberMy;
import com.example.sofraapp.app.ui.fragment.client.userCycle.LoginFragment;
import com.example.sofraapp.app.ui.fragment.general.restaurant.DetailRestaurantFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.sofraapp.app.ui.activity.MainActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartOrdersFragment extends Fragment {
    @BindView(R.id.CartOrdersFragment_Recycler_View)
    RecyclerView CartOrdersFragmentRecyclerView;
    @BindView(R.id.CartOrdersFragment_Loading_Indicator)
    ProgressBar CartOrdersFragmentLoadingIndicator;
    @BindView(R.id.CartOrdersFragment_RL)
    RelativeLayout CartOrdersFragmentRL;
    @BindView(R.id.CartOrdersFragment_Totally_Price)
    TextView CartOrdersFragmentTotallyPrice;
    @BindView(R.id.CartOrdersFragment_Add_More)
    Button CartOrdersFragmentAddMore;
    @BindView(R.id.CartOrdersFragment_Perform_Order)
    Button CartOrdersFragmentPerformOrder;
    Unbinder unbinder;
    OrderListAdapter orderListAdapter;
    Model model;
    public List<Data2RestaurantItems> mfoods = new ArrayList<>(); //Cached copy of words


    RememberMy rememberMy;
    @BindView(R.id.CartOrdersFragment_Linear_Total)
    LinearLayout CartOrdersFragmentLinearTotal;
    private Data2Restaurants data2Restaurants;


    public CartOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_orders, container, false);
        unbinder = ButterKnife.bind(this, view);
        rememberMy = new RememberMy(getActivity());
        Bundle bundle = getArguments();
        if (bundle != null) {
            Type listType = new TypeToken<List<Data2RestaurantItems>>() {
            }.getType();
            String st = getArguments().getString("card");
            mfoods = new Gson().fromJson(st, listType);
            data2Restaurants = new Gson().fromJson(bundle.getString("dev"), Data2Restaurants.class);
            if (mfoods != null) {
                Toast.makeText(getActivity(), "the card Not Empty", Toast.LENGTH_SHORT).show();
                CartOrdersFragmentRL.setVisibility(View.GONE);
                CartOrdersFragmentAddMore.setVisibility(View.VISIBLE);
                CartOrdersFragmentPerformOrder.setVisibility(View.VISIBLE);
                CartOrdersFragmentLinearTotal.setVisibility(View.VISIBLE);

            } else {
                Toast.makeText(getActivity(), "the card Empty", Toast.LENGTH_SHORT).show();
                CartOrdersFragmentRL.setVisibility(View.VISIBLE);
                CartOrdersFragmentAddMore.setVisibility(View.GONE);
                CartOrdersFragmentPerformOrder.setVisibility(View.GONE);
                CartOrdersFragmentLinearTotal.setVisibility(View.GONE);
            }
        }

        orderListAdapter = new OrderListAdapter(getActivity(), CartOrdersFragmentTotallyPrice, mfoods);
        CartOrdersFragmentRecyclerView.setAdapter(orderListAdapter);
        CartOrdersFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.CartOrdersFragment_Add_More, R.id.CartOrdersFragment_Perform_Order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.CartOrdersFragment_Add_More:
                DetailRestaurantFragment detailRestaurantFragment = new DetailRestaurantFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("dev", new Gson().toJson(data2Restaurants));
                detailRestaurantFragment.setArguments(bundle1);
                HelperMethod.replece(detailRestaurantFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_Home_contener,
                        toolbar, getString(R.string.list_food));
                break;
            case R.id.CartOrdersFragment_Perform_Order:
                if (rememberMy.getAPIKey() != null) {
                    NewOrderFragment newOrderFragment = new NewOrderFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("complete", new Gson().toJson(mfoods));
                    bundle.putString("dev", new Gson().toJson(data2Restaurants));
                    newOrderFragment.setArguments(bundle);
                    HelperMethod.replece(newOrderFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_Home_contener,
                            toolbar, getString(R.string.detail_order));
                } else {
                    LoginFragment loginFragment = new LoginFragment();
                    HelperMethod.repleceModel(loginFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.login), model);
                }
                break;
        }
    }
}