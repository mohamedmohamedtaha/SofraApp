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
public class PreviousOrderAsUserFragment extends Fragment {


    @BindView(R.id.PreviousOrderAsUserFragment_List_View)
    ListView PreviousOrderAsUserFragmentListView;
    @BindView(R.id.PreviousOrderAsUserFragment_IV_Empty_Image)
    ImageView PreviousOrderAsUserFragmentIVEmptyImage;
    @BindView(R.id.PreviousOrderAsUserFragment_RL_Empty_View)
    RelativeLayout PreviousOrderAsUserFragmentRLEmptyView;
    @BindView(R.id.PreviousOrderAsUserFragment_PB_Loading_Indicator)
    ProgressBar PreviousOrderAsUserFragmentPBLoadingIndicator;
    Unbinder unbinder;
    private SaveData saveData;
    private APIServices apiServices;
    private List<Data2MyOrdersAsUser> myOrdersAsUserArrayList = new ArrayList<>();
    private AdapterMyOrderAndPreviousCustom adapterMyOrderAndPreviousCustom;

    public PreviousOrderAsUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_previous_order_as_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        myOrdersAsUserArrayList.clear();
        PreviousOrderAsUserFragmentListView.setEmptyView(PreviousOrderAsUserFragmentRLEmptyView);
        apiServices = getRetrofit().create(APIServices.class);
      //  if (saveData.getApi_token() != null){
            apiServices.getMyOrdersAsUser("HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB","previous", 1).enqueue(new Callback<MyOrdersAsUser>() {
                @Override
                public void onResponse(Call<MyOrdersAsUser> call, Response<MyOrdersAsUser> response) {
                    MyOrdersAsUser myOrdersAsUser = response.body();
                    myOrdersAsUserArrayList = myOrdersAsUser.getData().getData();
                    PreviousOrderAsUserFragmentPBLoadingIndicator.setVisibility(View.VISIBLE);
                    if (myOrdersAsUser.getStatus() == 1) {
                        adapterMyOrderAndPreviousCustom = new AdapterMyOrderAndPreviousCustom(getActivity(), myOrdersAsUserArrayList
                                , new AdapterMyOrderAndPreviousCustom.done() {
                            @Override
                            public void buttonDone(Data2MyOrdersAsUser position) {
                                int data = position.getId();


                            }
                        }, new AdapterMyOrderAndPreviousCustom.reject() {
                            @Override
                            public void buttonReject(Data2MyOrdersAsUser position) {

                            }
                        }, false);
                        PreviousOrderAsUserFragmentListView.setAdapter(adapterMyOrderAndPreviousCustom);
                        PreviousOrderAsUserFragmentPBLoadingIndicator.setVisibility(View.GONE);

                    } else {
                        Toast.makeText(getActivity(), myOrdersAsUser.getMsg(), Toast.LENGTH_SHORT).show();
                        PreviousOrderAsUserFragmentPBLoadingIndicator.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onFailure(Call<MyOrdersAsUser> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    PreviousOrderAsUserFragmentPBLoadingIndicator.setVisibility(View.GONE);


                }
            });
    //}else {
      //      Toast.makeText(getActivity(), getString(R.string.must_login), Toast.LENGTH_SHORT).show();
        //}
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
