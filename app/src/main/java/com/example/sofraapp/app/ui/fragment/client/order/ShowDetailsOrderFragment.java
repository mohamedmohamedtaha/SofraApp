package com.example.sofraapp.app.ui.fragment.client.order;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.client.order.showorder.ShowOrder;
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
import static com.example.sofraapp.app.ui.fragment.client.order.CurrentOrderAsUSerFragment.ORDER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowDetailsOrderFragment extends Fragment {
    @BindView(R.id.ShowDetailsOrderFragment_Id_Order)
    TextView ShowDetailsOrderFragmentIdOrder;
    @BindView(R.id.ShowDetailsOrderFragment_State_Order)
    TextView ShowDetailsOrderFragmentStateOrder;
    @BindView(R.id.ShowDetailsOrderFragment_Delevery_Cost)
    TextView ShowDetailsOrderFragmentDeleveryCost;
    Unbinder unbinder;
    APIServices apiServices;
    SaveData saveData;

    public ShowDetailsOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_show_details_order, container, false);
        unbinder = ButterKnife.bind(this,view);
        saveData = getArguments().getParcelable(GET_DATA);
        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getShowOrder(/*saveData.getApi_token()*/"HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB",
                saveData.getId_position()).enqueue(new Callback<ShowOrder>() {
            @Override
            public void onResponse(Call<ShowOrder> call, Response<ShowOrder> response) {
                ShowOrder showOrder = response.body();
                try {
                    if (showOrder.getStatus() == 1){
                        ShowDetailsOrderFragmentIdOrder.setText(showOrder.getData().getId().toString());
                        ShowDetailsOrderFragmentDeleveryCost.setText(showOrder.getData().getDeliveryCost().toString());
                        ShowDetailsOrderFragmentStateOrder.setText(showOrder.getData().getState());
                    }else {
                        Toast.makeText(getActivity(), showOrder.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShowOrder> call, Throwable t) {
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
