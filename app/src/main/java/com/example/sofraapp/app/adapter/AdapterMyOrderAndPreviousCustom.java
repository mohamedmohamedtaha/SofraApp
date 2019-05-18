package com.example.sofraapp.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.client.order.myordersasuser.Data2MyOrdersAsUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterMyOrderAndPreviousCustom extends ArrayAdapter<Data2MyOrdersAsUser> {
    Data2MyOrdersAsUser myOrdersAsUser;
    private done done;
    private reject reject;
    private showDetails showDetails;
    private boolean isCurrent = false;
    public AdapterMyOrderAndPreviousCustom(Context context, List<Data2MyOrdersAsUser> myOrdersAsUsers, showDetails showDetails, done done, reject reject, boolean isCurrent) {
        super(context, 0, myOrdersAsUsers);
        this.done = done;
        this.reject = reject;
        this.isCurrent = isCurrent;
        this.showDetails = showDetails;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.custom_myorder_user, parent, false);
        }
        viewHolder = new ViewHolder(view);
        myOrdersAsUser = getItem(position);
        viewHolder.CustomMyOrderUserTVIsPriceDelevery.setText(myOrdersAsUser.getDeliveryCost());
        viewHolder.CustomMyOrderUserTVIsPriceTotally.setText(myOrdersAsUser.getTotal());
        viewHolder.CustomMyOrderUserTVShowNameRestaurant.setText(myOrdersAsUser.getRestaurant().getName());
        viewHolder.CustomMyOrderUserTVShowPrice.setText(myOrdersAsUser.getCost());
        Glide.with(getContext()).load(myOrdersAsUser.getRestaurant().getPhotoUrl()).into(viewHolder.CustomMyOrderUserIMShowImage);
        // Get the TextView current LayoutParams
        LayoutParams lp = (LayoutParams) viewHolder.CustomMyOrderUserTVNumberOrder.getLayoutParams();
        LayoutParams lp2 = (LayoutParams) viewHolder.CustomMyOrderUserTVMNumberOrder.getLayoutParams();

        if (isCurrent) {
            viewHolder.CustomMyOrderUserBTNotDelevery.setVisibility(View.GONE);
            viewHolder.CustomMyOrderUserBTDoneDelevery.setVisibility(View.GONE);
            // Set TextView layout margin 25 pixels to all side
            // Left Top Right Bottom Margin
            lp.setMargins(50, 70, 0, 0);
            lp2.setMargins(100, 70, 0, 0);

            // Apply the updated layout parameters to TextView
            viewHolder.CustomMyOrderUserTVNumberOrder.setLayoutParams(lp);
            viewHolder.CustomMyOrderUserTVMNumberOrder.setLayoutParams(lp2);

            viewHolder.CustomMyOrderUserTVNumberOrder.setText(myOrdersAsUser.getId().toString());
        } else {
            viewHolder.CustomMyOrderUserBTNotDelevery.setVisibility(View.VISIBLE);
            viewHolder.CustomMyOrderUserBTDoneDelevery.setVisibility(View.VISIBLE);
            lp.setMargins(16, 55, 0, 0);
            lp2.setMargins(56, 55, 0, 0);
            // Apply the updated layout parameters to TextView
            viewHolder.CustomMyOrderUserTVMNumberOrder.setLayoutParams(lp2);
            viewHolder.CustomMyOrderUserTVNumberOrder.setLayoutParams(lp);
            viewHolder.CustomMyOrderUserTVNumberOrder.setText(myOrdersAsUser.getId().toString());
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data2MyOrdersAsUser data2MyOrdersAsUser = myOrdersAsUser;
                if (showDetails != null) showDetails.showDetail(data2MyOrdersAsUser);
            }
        });
        return view;
    }

    public interface done {
        public void buttonDone(Data2MyOrdersAsUser position);
    }

    public interface reject {
        public void buttonReject(Data2MyOrdersAsUser position);
    }

    public interface showDetails {
        public void showDetail(Data2MyOrdersAsUser position);
    }


        class ViewHolder {
        @BindView(R.id.Custom_MyOrder_User_IM_Show_Image)
        ImageView CustomMyOrderUserIMShowImage;
        @BindView(R.id.Custom_MyOrder_User_TV_Show_Name_Restaurant)
        TextView CustomMyOrderUserTVShowNameRestaurant;
        @BindView(R.id.Custom_MyOrder_User_TV_Show_Price)
        TextView CustomMyOrderUserTVShowPrice;
        @BindView(R.id.Custom_MyOrder_User_TV_Is_Price_Delevery)
        TextView CustomMyOrderUserTVIsPriceDelevery;
        @BindView(R.id.Custom_MyOrder_User_TV_Is_Price_Totally)
        TextView CustomMyOrderUserTVIsPriceTotally;
        @BindView(R.id.Custom_MyOrder_User_TV_Number_Order)
        TextView CustomMyOrderUserTVNumberOrder;
        @BindView(R.id.Custom_MyOrder_User_BT_Not_Delevery)
        Button CustomMyOrderUserBTNotDelevery;
        @BindView(R.id.Custom_MyOrder_User_BT_Done_Delevery)
        Button CustomMyOrderUserBTDoneDelevery;
        @BindView(R.id.Custom_MyOrder_User_TV_mNumber_Order)
        TextView CustomMyOrderUserTVMNumberOrder;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            CustomMyOrderUserBTDoneDelevery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Data2MyOrdersAsUser data2MyOrdersAsUser = myOrdersAsUser;
                    if (done != null) done.buttonDone(data2MyOrdersAsUser);
                }
            });
            CustomMyOrderUserBTNotDelevery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Data2MyOrdersAsUser data2MyOrdersAsUser = myOrdersAsUser;
                    if (reject != null) reject.buttonReject(data2MyOrdersAsUser);
                }
            });


        }
    }
}
























