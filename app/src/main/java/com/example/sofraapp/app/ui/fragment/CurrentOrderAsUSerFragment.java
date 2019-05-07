package com.example.sofraapp.app.ui.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
import com.example.sofraapp.app.data.model.cycleClient.myordersasuser.Data2MyOrdersAsUser;
import com.example.sofraapp.app.data.model.cycleClient.myordersasuser.MyOrdersAsUser;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.SaveData;

import java.util.ArrayList;
import java.util.List;

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
    SaveData saveData;
    private APIServices apiServices;
    private List<Data2MyOrdersAsUser> myOrdersAsUserArrayList = new ArrayList<>();
    private AdapterMyOrderAndPreviousCustom adapterMyOrderAndPreviousCustom;
    public CurrentOrderAsUSerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_order_as_u, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        myOrdersAsUserArrayList.clear();

        CurrentOrderAsUSerFragment_List_View.setEmptyView(CurrentOrderAsUSerFragmentRLEmptyView);
      //  if (saveData.getApi_token() != null){
            CurrentOrderAsUSerFragmentPBLoadingIndicator.setVisibility(View.VISIBLE);
            apiServices = getRetrofit().create(APIServices.class);
            apiServices.getMyOrdersAsUser("HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB", "current",1).enqueue(new Callback<MyOrdersAsUser>() {
            @Override
            public void onResponse(Call<MyOrdersAsUser> call, Response<MyOrdersAsUser> response) {
                MyOrdersAsUser myOrdersAsUser = response.body();
                myOrdersAsUserArrayList = myOrdersAsUser.getData().getData();
                if (myOrdersAsUser.getStatus() == 1) {
                    CurrentOrderAsUSerFragmentPBLoadingIndicator.setVisibility(View.GONE);
                    adapterMyOrderAndPreviousCustom = new AdapterMyOrderAndPreviousCustom(getActivity(), myOrdersAsUserArrayList
                            , null, null,true);
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
    //}else {
      //      Toast.makeText(getActivity(), getString(R.string.must_login), Toast.LENGTH_SHORT).show();
       // }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
