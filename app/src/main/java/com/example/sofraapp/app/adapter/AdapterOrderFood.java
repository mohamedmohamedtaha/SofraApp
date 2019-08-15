package com.example.sofraapp.app.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.general.restaurants.CategoryRestaurants;
import com.example.sofraapp.app.data.model.general.restaurants.Data2Restaurants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterOrderFood extends RecyclerView.Adapter<AdapterOrderFood.OrderFood> {

    private Context context;
    private showDetial mListener;
    private ArrayList<Data2Restaurants> restaurantsArrayList = new ArrayList<>();

    public AdapterOrderFood(Context context, ArrayList<Data2Restaurants> restaurantsArrayList,showDetial mListener) {
        this.context = context;
        this.restaurantsArrayList = restaurantsArrayList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public OrderFood onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_order_food, viewGroup, false);
        final OrderFood orderFood = new OrderFood(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = orderFood.getAdapterPosition();
                Data2Restaurants data2Restaurants = restaurantsArrayList.get(position);
                if (mListener != null) mListener.itemShowDetail(data2Restaurants);
            }
        });
        return orderFood;
    }
    private void getReating(int rate,OrderFood orderFood){
        switch (rate){
            case 0:
                orderFood.RBRateRestaurant.setRating(0);
                break;
            case 1:
                orderFood.RBRateRestaurant.setRating(1);
                break;
            case 2:
                orderFood.RBRateRestaurant.setRating(2);
                break;
            case 3:
                orderFood.RBRateRestaurant.setRating(3);
                break;

            case 4:
                orderFood.RBRateRestaurant.setRating(4);
                break;
            case 5:
                orderFood.RBRateRestaurant.setRating(5);
                break;
                default:
        }
    }

    @Override
    public void onBindViewHolder(@NonNull OrderFood orderFood, int position) {
        Glide.with(context)
                .load(restaurantsArrayList.get(position).getPhotoUrl())
        .into(orderFood.IMShowImage);
        orderFood.TVShowNameRestaurant.setText(restaurantsArrayList.get(position).getName());
        orderFood.TVMinOrder.setText(restaurantsArrayList.get(position).getMinimumCharger());
        orderFood.TVPriceDelevery.setText(restaurantsArrayList.get(position).getDeliveryCost());
        getReating(Integer.parseInt(restaurantsArrayList.get(position).getRate()),orderFood);
        String isAvailable = restaurantsArrayList.get(position).getAvailability();
        if (isAvailable.equals(context.getString(R.string.closed))){
            orderFood.TVIsOpen.setTextColor(context.getResources().getColor(R.color.holo_red_light));
            orderFood.TVIsOpen.setText(isAvailable);
        }else {
            orderFood.TVIsOpen.setText(isAvailable);
        }
       }

    @Override
    public int getItemCount() {
        if (restaurantsArrayList != null){
            return  restaurantsArrayList.size();
        }
        return 0;
    }

    public interface showDetial {
        void itemShowDetail(Data2Restaurants position);
    }
    class OrderFood extends RecyclerView.ViewHolder {
        @BindView(R.id.IM_Show_Image)
        ImageView IMShowImage;
        @BindView(R.id.TV_Show_Name_Restaurant)
        TextView TVShowNameRestaurant;
        @BindView(R.id.TV_Show_Types)
        TextView TVShowTypes;
        @BindView(R.id.RB_Rate_Restaurant)
        RatingBar RBRateRestaurant;
        @BindView(R.id.TV_Is_Open)
        TextView TVIsOpen;
        @BindView(R.id.TV_Min_Order)
        TextView TVMinOrder;
        @BindView(R.id.TV_Price_Delevery)
        TextView TVPriceDelevery;
        private View view;
        public OrderFood(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this,view);

        }
    }
}
