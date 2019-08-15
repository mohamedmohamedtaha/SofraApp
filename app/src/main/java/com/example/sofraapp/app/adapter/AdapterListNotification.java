package com.example.sofraapp.app.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.client.cycleClient.notifications.listofnotifications.Data2Notifications;
import com.example.sofraapp.app.helper.HelperMethod;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterListNotification extends RecyclerView.Adapter<AdapterListNotification.ListNotificationViewHolder> {

    private List<Data2Notifications> notificationsArrayList = new ArrayList<>();
    private Context context;

    public AdapterListNotification(List<Data2Notifications> notificationsArrayList, Context context) {
        this.notificationsArrayList = notificationsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListNotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_notification, viewGroup, false);
        ListNotificationViewHolder listNotificationViewHolder = new ListNotificationViewHolder(view);
        return listNotificationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListNotificationViewHolder listNotificationViewHolder, int i) {
        listNotificationViewHolder.TVShowName.setText(notificationsArrayList.get(i).getTitle());
        try {
            listNotificationViewHolder.TVShowDay.setText(HelperMethod.formatDay("yyyy-MM-dd HH:mm:ss",notificationsArrayList.get(i).getCreatedAt()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            String date = HelperMethod.formatDateFromDateString("yyyy-MM-dd HH:mm:ss","dd MMM yyyy",notificationsArrayList.get(i).getCreatedAt());
            listNotificationViewHolder.TVShowDate.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            String time = HelperMethod.formatDateFromDateString("yyyy-MM-dd HH:mm:ss","h:mm a",notificationsArrayList.get(i).getCreatedAt());
            listNotificationViewHolder.TVShowTime.setText(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (notificationsArrayList != null) {
            return notificationsArrayList.size();
        }
        return 0;
    }

    class ListNotificationViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.TV_Show_Name)
        TextView TVShowName;
        @BindView(R.id.TV_Show_Day)
        TextView TVShowDay;
        @BindView(R.id.TV_Show_Date)
        TextView TVShowDate;
        @BindView(R.id.TV_Show_Time)
        TextView TVShowTime;
        public ListNotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this,view);
        }
    }
}
