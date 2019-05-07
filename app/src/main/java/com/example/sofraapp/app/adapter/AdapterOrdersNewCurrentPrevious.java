package com.example.sofraapp.app.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.cycleRestaurant.myorders.Data2MyOrders;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterOrdersNewCurrentPrevious extends RecyclerView.Adapter<AdapterOrdersNewCurrentPrevious.AdapterOrdersNewCurrentPreviousViewHolder> {
    private Context context;
    private ArrayList<Data2MyOrders> data2MyOrdersArrayList = new ArrayList<>();
    private call callListener;
    private accept acceptListener;
    private reject rejectListener;
    private int testSate ;
    public AdapterOrdersNewCurrentPrevious(Context context, ArrayList<Data2MyOrders> data2MyOrdersArrayList, call callListener, accept acceptListener, reject rejectListener, int testSate) {
        this.context = context;
        this.data2MyOrdersArrayList = data2MyOrdersArrayList;
        this.callListener = callListener;
        this.acceptListener = acceptListener;
        this.rejectListener = rejectListener;
        this.testSate = testSate;
    }

    @NonNull
    @Override
    public AdapterOrdersNewCurrentPreviousViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_orders, viewGroup, false);
        AdapterOrdersNewCurrentPreviousViewHolder adapterOrdersNewCurrentPreviousViewHolder = new AdapterOrdersNewCurrentPreviousViewHolder(view);
        return adapterOrdersNewCurrentPreviousViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrdersNewCurrentPreviousViewHolder holder, int position) {

        Data2MyOrders data2MyOrders = data2MyOrdersArrayList.get(position);
//        Glide.with(context).load(data2MyOrders.getItems().get(position).getPhotoUrl())
  //              .into(holder.CustomOrdersIMShowImage);
        holder.CustomOrdersTVShowNameCustomer.setText(data2MyOrders.getClient().getName());
        holder.CustomOrdersTVIsAddress.setText(data2MyOrders.getAddress());
        holder.CustomOrdersTVIsPriceDelevery.setText(data2MyOrders.getDeliveryCost());
        holder.CustomOrdersTVShowPrice.setText(data2MyOrders.getCost());
        holder.CustomOrdersTVIsPriceTotally.setText(data2MyOrders.getTotal());
    //    holder.CustomOrdersTVNumberOrder.append(data2MyOrders.getItems().get(position).getPivot().getOrderId());
        if (testSate == 0){
            holder.CustomOrdersBTAccept.setVisibility(View.VISIBLE);
            holder.CustomOrdersBTAccept.setText(R.string.accept);
            holder.CustomOrdersBTReject.setVisibility(View.VISIBLE);
            holder.CustomOrdersBTReject.setText(R.string.not_delevery);
            holder.CustomMyOrderUserBTPhone.setVisibility(View.VISIBLE);
            holder.CustomMyOrderUserBTPhone.setText(data2MyOrders.getClient().getPhone());
        }else if (testSate ==1){
            holder.CustomOrdersBTAccept.setVisibility(View.VISIBLE);
            holder.CustomOrdersBTAccept.setText(R.string.sure_accept);
            holder.CustomOrdersBTReject.setVisibility(View.GONE);
            holder.CustomMyOrderUserBTPhone.setVisibility(View.VISIBLE);
            holder.CustomMyOrderUserBTPhone.setText(data2MyOrders.getClient().getPhone());
        }else {
            holder.CustomOrdersBTAccept.setVisibility(View.VISIBLE);
            holder.CustomOrdersBTAccept.setText(R.string.order_finished);
            holder.CustomOrdersBTReject.setVisibility(View.GONE);
            holder.CustomMyOrderUserBTPhone.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (data2MyOrdersArrayList != null) {
            return data2MyOrdersArrayList.size();
        }
        return 0;
    }

    class AdapterOrdersNewCurrentPreviousViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.Custom_Orders_IM_Show_Image)
        ImageView CustomOrdersIMShowImage;
        @BindView(R.id.Custom_Orders_TV_Show_Name_Customer)
        TextView CustomOrdersTVShowNameCustomer;
        @BindView(R.id.Custom_Orders_TV_Show_Price)
        TextView CustomOrdersTVShowPrice;
        @BindView(R.id.Custom_Orders_TV_Is_Price_Delevery)
        TextView CustomOrdersTVIsPriceDelevery;
        @BindView(R.id.Custom_Orders_TV_Is_Price_Totally)
        TextView CustomOrdersTVIsPriceTotally;
        @BindView(R.id.Custom_Orders_TV_Is_Address)
        TextView CustomOrdersTVIsAddress;
        @BindView(R.id.Custom_Orders_TV_Number_Order)
        TextView CustomOrdersTVNumberOrder;
        @BindView(R.id.Custom_Orders_BT_Reject)
        Button CustomOrdersBTReject;
        @BindView(R.id.Custom_Orders_BT_Accept)
        Button CustomOrdersBTAccept;
        @BindView(R.id.Custom_MyOrder_User_BT_Phone)
        Button CustomMyOrderUserBTPhone;

        public AdapterOrdersNewCurrentPreviousViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }
    public interface call{
        void itemCall(Data2MyOrders data2MyOrders);
    }
    public interface accept{
        void itemAccept(Data2MyOrders data2MyOrders);
    }
    public interface reject{
        void itemReject(Data2MyOrders data2MyOrders);
    }
}
