package com.example.sofraapp.app.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.cycleRestaurant.myproduct.Data2MyProduct;
import com.example.sofraapp.app.data.model.cycleRestaurant.offers.myoffers.Data2MyOffers;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterMyProducts extends RecyclerView.Adapter<AdapterMyProducts.MyProductsViewHolder> {
    private Context context;
    private ArrayList<Data2MyProduct> data2MyProducts = new ArrayList<>();
    private Boolean isOffer;
    private ArrayList<Data2MyOffers> data2MyOffers = new ArrayList<>();

    public AdapterMyProducts(Context context, ArrayList<Data2MyProduct> data2MyProducts) {
        this.context = context;
        this.data2MyProducts = data2MyProducts;
        this.isOffer = false;
    }
    public AdapterMyProducts(Context context, ArrayList<Data2MyOffers> data2MyOffers, Boolean isOffer) {
        this.context = context;
        this.data2MyOffers = data2MyOffers;
        this.isOffer = isOffer;
    }

    @NonNull
    @Override
    public MyProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_restaurant_items, viewGroup, false);
        MyProductsViewHolder myProductsViewHolder = new MyProductsViewHolder(view);
        return myProductsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyProductsViewHolder holder, int position) {
        if (isOffer == false){
            if (TextUtils.isEmpty(data2MyProducts.get(position).getPhotoUrl())){
                Glide.with(context).load(R.drawable.no_image).into(holder.AdapterRestaurantItemsIMShowImage);
            }else {
                Glide.with(context).load(data2MyProducts.get(position).getPhotoUrl()).into(holder.AdapterRestaurantItemsIMShowImage);

            }
            holder.AdapterRestaurantItemsTVPriceItem.setText(data2MyProducts.get(position).getPrice());
            holder.AdapterRestaurantItemsTVShowNameRestaurant.setText(data2MyProducts.get(position).getName());
            holder.AdapterRestaurantItemsTVShowTypes.setText(data2MyProducts.get(position).getDescription());

        }else {
            if (TextUtils.isEmpty(data2MyOffers.get(position).getPhoto())){
                Glide.with(context).load(R.drawable.no_image).into(holder.AdapterRestaurantItemsIMShowImage);
            }else {
                Glide.with(context).load(data2MyOffers.get(position).getPhoto()).into(holder.AdapterRestaurantItemsIMShowImage);

            }
            holder.AdapterRestaurantItemsTVPriceItem.setText(data2MyOffers.get(position).getPrice());
            holder.AdapterRestaurantItemsTVShowNameRestaurant.setText(data2MyOffers.get(position).getName());
            holder.AdapterRestaurantItemsTVShowTypes.setText(data2MyOffers.get(position).getDescription());

        }


    }

    @Override
    public int getItemCount() {
        if (isOffer == false){
                return data2MyProducts.size();
        }else {
            return data2MyOffers.size();
        }

    }

    class MyProductsViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.Adapter_Restaurant_Items_IM_Show_Image)
        ImageView AdapterRestaurantItemsIMShowImage;
        @BindView(R.id.Adapter_Restaurant_Items_TV_Show_Name_Restaurant)
        TextView AdapterRestaurantItemsTVShowNameRestaurant;
        @BindView(R.id.Adapter_Restaurant_Items_TV_Show_Types)
        TextView AdapterRestaurantItemsTVShowTypes;
        @BindView(R.id.Adapter_Restaurant_Items_TV_Price_Item)
        TextView AdapterRestaurantItemsTVPriceItem;
        @BindView(R.id.Adapter_Restaurant_Items_TV_Price_Only)
        TextView AdapterRestaurantItemsTVPriceOnly;

        public MyProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this,view);


        }
    }


}
