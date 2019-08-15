package com.example.sofraapp.app.ui.fragment.client.order;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.client.order.neworder.NewOrder;
import com.example.sofraapp.app.data.model.general.paymentmethods.DataPaymentMethods;
import com.example.sofraapp.app.data.model.general.paymentmethods.PaymentMethods;
import com.example.sofraapp.app.data.model.general.restaurantitems.Data2RestaurantItems;
import com.example.sofraapp.app.data.model.general.restaurants.Data2Restaurants;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.data.room.RoomDao;
import com.example.sofraapp.app.data.room.RoomManger;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.Model;
import com.example.sofraapp.app.helper.RememberMy;
import com.example.sofraapp.app.ui.activity.MainActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class NewOrderFragment extends Fragment {
    @BindView(R.id.NewOrderFragment_Tiet_Notes)
    TextInputEditText NewOrderFragmentTietNotes;
    @BindView(R.id.NewOrderFragment_TV_Address)
    TextView NewOrderFragmentTVAddress;
      @BindView(R.id.NewOrderFragment_TV_Total)
    TextView NewOrderFragmentTVTotal;
    @BindView(R.id.NewOrderFragment_TV_Price_Delevery)
    TextView NewOrderFragmentTVPriceDelevery;
    @BindView(R.id.NewOrderFragment_TV_Price_Total)
    TextView NewOrderFragmentTVPriceTotal;
    @BindView(R.id.NewOrderFragment_Perform_Pay)
    Button NewOrderFragmentPerformPay;
    Unbinder unbinder;
    Model model;
    RememberMy rememberMy;
    APIServices apiServices;
    private RoomManger roomManger;
    private RoomDao roomDao;
    @BindView(R.id.NewOrderFragment_RG)
    RadioGroup NewOrderFragmentRG;
    private double Total = 0.0;
    private Data2Restaurants data2Restaurants;
    private List<RadioButton> radioButtons = new ArrayList<>();
    private List<DataPaymentMethods> paymentMethods = new ArrayList<>();
    private List<Integer> ProgectId = new ArrayList<>();
    private List<String> ProductNotes = new ArrayList<>();
    private List<String> ProductCounter = new ArrayList<>();
    private List<String> private_order;
    List<Data2RestaurantItems> data2RestaurantItems ;
    public NewOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        rememberMy = new RememberMy(getActivity());
        apiServices = getRetrofit().create(APIServices.class);
        roomManger = RoomManger.getInstance(getActivity());
        roomDao = roomManger.roomDao();
        Bundle bundle = getArguments();
        Total = 0.0;

        if (bundle != null) {
            Type type = new TypeToken<List<Data2RestaurantItems>>() {}.getType();
            String st = getArguments().getString("complete");
            data2RestaurantItems = new Gson().fromJson(st, type);

            data2Restaurants = new Gson().fromJson(bundle.getString("dev"), Data2Restaurants.class);

                      //  data2RestaurantItems = new Gson().fromJson(bundle.getString("complete"),Data2RestaurantItems.class);
            NewOrderFragmentTVAddress.setText(rememberMy.getAddress());
            for (int i = 0; i < data2RestaurantItems.size(); i++) {
                Total = Total + (Double.parseDouble(data2RestaurantItems.get(i).getPrice()) *
                        Double.parseDouble(data2RestaurantItems.get(i).getCounter()));
            }
            NewOrderFragmentTVTotal.setText(" " + Total);
            NewOrderFragmentTVPriceDelevery.setText(" " + data2Restaurants.getDeliveryCost());
            NewOrderFragmentTVPriceTotal.setText(String.valueOf(Total + Double.parseDouble(data2Restaurants.getDeliveryCost())));
            getPaymentMethods();


        }
        return view;
    }

    @OnClick(R.id.NewOrderFragment_Perform_Pay)
    public void onViewClicked() {
        onOrderCompleter();

    }

    private void onCreateRadioButton(DataPaymentMethods dataPaymentMethods) {
        if (radioButtons.size() == 0) {
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setText(dataPaymentMethods.getName());
            radioButtons.add(radioButton);
            NewOrderFragmentRG.addView(radioButton);

        }
    }

    private void getPaymentMethods() {
        apiServices.getPaymentMethods().enqueue(new Callback<PaymentMethods>() {
            @Override
            public void onResponse(Call<PaymentMethods> call, Response<PaymentMethods> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        paymentMethods = response.body().getData();
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            onCreateRadioButton(response.body().getData().get(i));
                        }
                        radioButtons.get(0).setChecked(true);
                    }else {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<PaymentMethods> call, Throwable t) {

            }
        });
    }
    private  void onOrderCompleter(){
        if (data2RestaurantItems.size() > 0){
            ProgectId = new ArrayList<>();
            ProductNotes = new ArrayList<>();
            ProductCounter = new ArrayList<>();
            for (int i =0; i< data2RestaurantItems.size(); i ++){
                ProgectId.add(data2RestaurantItems.get(i).getId());

                if (data2RestaurantItems.get(i).getNote() == null){
                    ProductNotes.add("No notes");
                }else {
                    ProductNotes.add(data2RestaurantItems.get(i).getNote());
                }
                ProductCounter.add(data2RestaurantItems.get(i).getCounter());
            }
            int paymentId = 0;
            for (int i = 0; i<paymentMethods.size(); i++ ){
                for (int j= 0 ; j<radioButtons.size(); j++){
                    if (radioButtons.get(i).getText() == paymentMethods.get(i).getName()){
                        paymentId = paymentMethods.get(i).getId();
                        break;
                    }
                }
                if (paymentId != 0){
                    break;
                }
            }
            radioButtons.get(0).setChecked(true);
            String notes = NewOrderFragmentTietNotes.getText().toString().trim();
            apiServices.newOrder(ProgectId,ProductCounter,ProductNotes,String.valueOf(data2RestaurantItems.get(0).getRestaurantId()),
                    notes,rememberMy.getAddress(),paymentId,rememberMy.getPhoneUser(),rememberMy.getNameUser(),rememberMy.getEmailUser(),rememberMy.getAPIKey()).enqueue(new Callback<NewOrder>() {
                @Override
                public void onResponse(Call<NewOrder> call, Response<NewOrder> response) {
                    NewOrder newOrder = response.body();
                    try {
                        if (newOrder.getStatus() == 1){
                            Executors.newSingleThreadExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    roomDao.deleteAllItemToCar();

                                }
                            });
                          Toast.makeText(getActivity(), newOrder.getMsg(), Toast.LENGTH_SHORT).show();
                        //    createSnackBar(getView(),newOrder.getMsg(),getActivity());
                            HelperMethod.startActivity(getActivity(), MainActivity.class);

                        }else {
                            Toast.makeText(getActivity(), newOrder.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<NewOrder> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static void createSnackBar(View view, String message, Context context) {
        final Snackbar snackbar = Snackbar.make(view, message, 50000);
        snackbar.setAction(R.string.dismiss, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        })
                .setActionTextColor(context.getResources().getColor(android.R.color.holo_red_light))

                .show();
    }

}
