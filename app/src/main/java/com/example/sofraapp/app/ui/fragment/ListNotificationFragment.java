package com.example.sofraapp.app.ui.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.adapter.AdapterListNotification;
import com.example.sofraapp.app.data.model.cycleClient.notifications.Data2Notifications;
import com.example.sofraapp.app.data.model.cycleClient.notifications.Notifications;
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
public class ListNotificationFragment extends Fragment {
    @BindView(R.id.ListNotificationFragment_Recycler_View)
    RecyclerView ListNotificationFragmentRecyclerView;
    @BindView(R.id.ListNotificationFragment_Empty_Image)
    ImageView ListNotificationFragmentEmptyImage;
    @BindView(R.id.ListNotificationFragment_TV_Empty_View)
    TextView ListNotificationFragmentTVEmptyView;
    @BindView(R.id.ListNotificationFragment_RL)
    RelativeLayout ListNotificationFragmentRL;
    @BindView(R.id.ListNotificationFragment_Loading_Indicator)
    ProgressBar ListNotificationFragmentLoadingIndicator;
    Unbinder unbinder;
    SaveData saveData;
    APIServices apiServices;
    private List<Data2Notifications> data2NotificationsArrayList = new ArrayList<>();
    private AdapterListNotification adapterListNotification;

    public ListNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_notification, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        data2NotificationsArrayList.clear();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        ListNotificationFragmentRecyclerView.setLayoutManager(linearLayoutManager);
        adapterListNotification = new AdapterListNotification(data2NotificationsArrayList,getActivity());
        ListNotificationFragmentRecyclerView.setAdapter(adapterListNotification);
        apiServices = getRetrofit().create(APIServices.class);
            apiServices.getNotifications("K1X6AzRlJFeVbGnHwGYsdCu0ETP1BqYC7DpMTZ3zLvKgU5feHMvsEEnKTpzh").enqueue(new Callback<Notifications>() {
                @Override
                public void onResponse(Call<Notifications> call, Response<Notifications> response) {
                    Notifications notifications = response.body();
                    ListNotificationFragmentLoadingIndicator.setVisibility(View.VISIBLE);
                    try {
                        if (notifications.getStatus() == 1){
                           // if (!data2NotificationsArrayList.isEmpty()){
                                ListNotificationFragmentLoadingIndicator.setVisibility(View.GONE);
                                ListNotificationFragmentRecyclerView.setVisibility(View.VISIBLE);
                                ListNotificationFragmentRL.setVisibility(View.GONE);
                                data2NotificationsArrayList.addAll(notifications.getData().getData());
                         /*   }else {
                                ListNotificationFragmentLoadingIndicator.setVisibility(View.GONE);
                                ListNotificationFragmentRecyclerView.setVisibility(View.GONE);
                                ListNotificationFragmentRL.setVisibility(View.VISIBLE);
                            }
*/
                        }else {
                            Toast.makeText(getActivity(), notifications.getMsg(), Toast.LENGTH_SHORT).show();
                            ListNotificationFragmentLoadingIndicator.setVisibility(View.GONE);
                            ListNotificationFragmentRecyclerView.setVisibility(View.GONE);
                            ListNotificationFragmentRL.setVisibility(View.VISIBLE);
                        }
                    }catch (Exception e){
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        ListNotificationFragmentLoadingIndicator.setVisibility(View.GONE);
                        ListNotificationFragmentRecyclerView.setVisibility(View.GONE);
                        ListNotificationFragmentRL.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<Notifications> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    ListNotificationFragmentLoadingIndicator.setVisibility(View.GONE);
                    ListNotificationFragmentRecyclerView.setVisibility(View.GONE);
                    ListNotificationFragmentRL.setVisibility(View.VISIBLE);            }
            });
            return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
