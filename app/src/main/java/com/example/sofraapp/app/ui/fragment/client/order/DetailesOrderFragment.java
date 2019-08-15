package com.example.sofraapp.app.ui.fragment.client.order;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.general.restaurantitems.Data2RestaurantItems;
import com.example.sofraapp.app.data.model.general.restaurantitems.RestaurantItems;
import com.example.sofraapp.app.data.model.general.restaurants.Data2Restaurants;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.data.room.OrderRoomDatabase;
import com.example.sofraapp.app.data.room.OrdersViewModel;
import com.example.sofraapp.app.data.room.RoomDao;
import com.example.sofraapp.app.data.room.RoomManger;
import com.example.sofraapp.app.helper.RememberMy;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofraapp.app.data.rest.RetrofitClient.getRetrofit;
import static com.example.sofraapp.app.helper.HelperMethod.replece;
import static com.example.sofraapp.app.ui.activity.MainActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailesOrderFragment extends Fragment {
    @BindView(R.id.DetailesOrderFragment_IM_Show_Image)
    ImageView DetailesOrderFragmentIMShowImage;
    @BindView(R.id.DetailesOrderFragment_TV_Show_Name)
    TextView DetailesOrderFragmentTVShowName;
    @BindView(R.id.DetailesOrderFragment_TV_Show_Details)
    TextView DetailesOrderFragmentTVShowDetails;
    @BindView(R.id.DetailesOrderFragment_TV_Show_Price)
    TextView DetailesOrderFragmentTVShowPrice;
    @BindView(R.id.DetailesOrderFragment_TV_Price)
    TextView DetailesOrderFragmentTVPrice;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.DetailesOrderFragment_TV_Time)
    TextView DetailesOrderFragmentTVTime;
    @BindView(R.id.DetailesOrderFragment_TV_Time_Request)
    TextView DetailesOrderFragmentTVTimeRequest;
    @BindView(R.id.DetailesOrderFragment_Private_Request)
    TextInputEditText DetailesOrderFragmentPrivateRequest;
    @BindView(R.id.DetailesOrderFragment_Loading_Indicator)
    ProgressBar DetailesOrderFragmentLoadingIndicator;
    @BindView(R.id.DetailesOrderFragment_BT_Sum)
    Button DetailesOrderFragmentBTSum;
    @BindView(R.id.DetailesOrderFragment_TV_Count)
    TextView DetailesOrderFragmentTVCount;
    @BindView(R.id.DetailesOrderFragment_BT_Plus)
    Button DetailesOrderFragmentBTPlus;
    @BindView(R.id.DetailesOrderFragment_BT_Add_Car)
    Button DetailesOrderFragmentBTAddCar;
    Unbinder unbinder;
    private APIServices apiServices;
    // Model model;
    private  int count_orders = 1;
    RestaurantItems restaurantItems;
    private boolean de;
    private Data2RestaurantItems foodITem;
    private List<Data2RestaurantItems> foodItems = new ArrayList<>();

    private RoomDao roomDao;
    private RoomManger roomManger;
    private RememberMy rememberMy;
    private Data2Restaurants data2Restaurants;
    private String private_order;

    private void initPAramerts() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            foodITem = new Gson().fromJson(bundle.getString("food"), Data2RestaurantItems.class);
            data2Restaurants = new Gson().fromJson(bundle.getString("dev"), Data2Restaurants.class);
        }
    }

    public DetailesOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detailes_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        rememberMy = new RememberMy(getActivity());
        initPAramerts();

        roomManger = RoomManger.getInstance(getActivity());
        roomDao = roomManger.roomDao();

        apiServices = getRetrofit().create(APIServices.class);
        DetailesOrderFragmentLoadingIndicator.setVisibility(View.VISIBLE);
        apiServices.getRestaurantItems(Integer.valueOf(foodITem.getRestaurantId()), 1).enqueue(new Callback<RestaurantItems>() {
            @Override
            public void onResponse(Call<RestaurantItems> call, Response<RestaurantItems> response) {
                restaurantItems = response.body();
                if (restaurantItems.getStatus() == 1) {
                    if (DetailesOrderFragmentLoadingIndicator != null) {
                        DetailesOrderFragmentLoadingIndicator.setVisibility(View.GONE);
                    }
                    Glide.with(getContext()).load(foodITem.getPhotoUrl()).into(DetailesOrderFragmentIMShowImage);
                    DetailesOrderFragmentTVShowName.setText(foodITem.getName());
                    DetailesOrderFragmentTVPrice.setText("" + foodITem.getPrice());
                    DetailesOrderFragmentTVShowDetails.setText(foodITem.getDescription());
                    DetailesOrderFragmentTVTimeRequest.setText(foodITem.getPreparingTime());


                } else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    DetailesOrderFragmentLoadingIndicator.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<RestaurantItems> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                DetailesOrderFragmentLoadingIndicator.setVisibility(View.GONE);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.DetailesOrderFragment_BT_Sum, R.id.DetailesOrderFragment_BT_Plus, R.id.DetailesOrderFragment_BT_Add_Car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.DetailesOrderFragment_BT_Sum:
                if (count_orders >= 50) {
                    Toast.makeText(getActivity(), getString(R.string.not_allowed_alot), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    count_orders++;
                    DetailesOrderFragmentTVCount.setText("" + count_orders);
                }
                break;
            case R.id.DetailesOrderFragment_BT_Plus:
                if (count_orders <= 1) {
                    Toast.makeText(getActivity(), getString(R.string.not_allowed), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    count_orders--;
                    DetailesOrderFragmentTVCount.setText("" + count_orders);
                }
                break;
            case R.id.DetailesOrderFragment_BT_Add_Car:
                try {
                    addToCar(false, true);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public double getTotalPrice(int quantity, Double price) {
        Double total = 0.0;
        if (quantity >= 0) {
            total = quantity * price;
        }
        return total;
    }

    private void addToCar(final boolean delete, boolean check) {
        private_order = DetailesOrderFragmentPrivateRequest.getText().toString().trim();
        if (private_order != null) {
            foodITem.setNote(private_order);
        } else {
            foodITem.setNote("");
        }

        if (check) {
            de = false;
            for (int i = 0; i < foodItems.size(); i++) {
                if (!foodITem.getRestaurantId().equals(foodItems.get(i).getRestaurantId())) {
                    showAlertDialod();
                    de = true;
                    break;
                }

            }
            if (de) {
                return;
            }
        }
        Integer count = 0;
        if (!DetailesOrderFragmentTVCount.getText().toString().equals("")) {
            count = Integer.valueOf(DetailesOrderFragmentTVCount.getText().toString());
        } else {
            return;
        }
        foodITem.setCounter(String.valueOf(count));
        final Integer finalCount = count;

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                foodItems = roomDao.getAllItem();
                Data2RestaurantItems food = foodITem;
                if (delete) {
                    roomDao.deleteAllItemToCar();
                }
                boolean in = false;
                for (int i = 0; i < foodItems.size(); i++) {
                    if (foodITem.getId().equals(foodItems.get(i).getId())) {
                        in = true;
                        foodItems.get(i).setCounter(String.valueOf(finalCount));
                        roomDao.updateItemToCar(foodItems.get(i));
                        break;
                    }
                }
                if (!in) {
                    roomDao.insertItemToCar(food);
                }

                CartOrdersFragment cartOrdersFragment = new CartOrdersFragment();
                Bundle bundle = new Bundle();
                bundle.putString("card", new Gson().toJson(roomDao.getAllItem()));
                bundle.putString("dev", new Gson().toJson(data2Restaurants));
                cartOrdersFragment.setArguments(bundle);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        replece(cartOrdersFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_Home_contener,
                                toolbar, getString(R.string.cart));
                    }
                });
            }
        });

    }

    private void showAlertDialod() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_error);
        builder.setTitle(getActivity().getResources().getString(R.string.alartStep1) +
                getActivity().getResources().getString(R.string.yes) +
                getActivity().getResources().getString(R.string.alartStep2) +
                getActivity().getResources().getString(R.string.complete_order) + "\"");
        builder.setPositiveButton(getActivity().getResources().getString(R.string.complete_order), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CartOrdersFragment cartOrdersFragment = new CartOrdersFragment();
                //   HelperMethod.repleceModel(cartOrdersFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar
                //         , getString(R.string.cart), model);
            }
        });
        builder.setNegativeButton(getActivity().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addToCar(true, false);
            }
        });
        builder.show();
    }
}










































