package com.example.sofraapp.app.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.restaurant.fooditem.myitems.Data2MyItems;
import com.example.sofraapp.app.data.model.restaurant.offers.myoffers.Data2MyOffers;
import com.example.sofraapp.app.ui.fragment.restaurant.foodItem.AddAndEditPRoduct;
import com.example.sofraapp.app.ui.fragment.restaurant.offers.AddOfferFragment;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sofraapp.app.ui.fragment.restaurant.foodItem.ProductMyFragment.DIALOG_PRODUCT;
import static com.example.sofraapp.app.ui.fragment.restaurant.offers.MyOffersFragment.DIALOG_OFFERS;

public class AdapterMyProducts extends RecyclerView.Adapter<AdapterMyProducts.MyProductsViewHolder> {
    private Context context;
    private ArrayList<Data2MyItems> data2MyProducts = new ArrayList<>();
    private Boolean isOffer;
    private ArrayList<Data2MyOffers> data2MyOffers = new ArrayList<>();

    public AdapterMyProducts(Context context, ArrayList<Data2MyItems> data2MyProducts, boolean isProduct) {
        this.context = context;
        this.data2MyProducts = data2MyProducts;
        this.isOffer = isProduct;
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
        setSwipe(holder, position);
        setAction(holder,position);
        if (!isOffer) {
            Glide.with(context).load(data2MyProducts.get(position).getPhotoUrl())
                    .placeholder(R.drawable.no_image).into(holder.AdapterRestaurantItemsIMShowImage);
            holder.AdapterRestaurantItemsTVPriceItem.setText(data2MyProducts.get(position).getPrice());
            holder.AdapterRestaurantItemsTVShowNameRestaurant.setText(data2MyProducts.get(position).getName());
            holder.AdapterRestaurantItemsTVShowTypes.setText(data2MyProducts.get(position).getDescription());
        } else {
            Glide.with(context).load(data2MyOffers.get(position).getPhotoUrl())
                    .placeholder(R.drawable.no_image).into(holder.AdapterRestaurantItemsIMShowImage);
            holder.AdapterRestaurantItemsTVPriceItem.setText(data2MyOffers.get(position).getPrice());
            holder.AdapterRestaurantItemsTVShowNameRestaurant.setText(data2MyOffers.get(position).getName());
            holder.AdapterRestaurantItemsTVShowTypes.setText(data2MyOffers.get(position).getDescription());

        }


    }

    @Override
    public int getItemCount() {
        if (!isOffer) {
            return data2MyProducts.size();
        } else {
            return data2MyOffers.size();
        }

    }

    private void setSwipe(MyProductsViewHolder holder, int position) {
        holder.editBt.setVisibility(View.VISIBLE);
        holder.deleteBt.setVisibility(View.VISIBLE);
        holder.swipe.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipe.addDrag(SwipeLayout.DragEdge.Right, holder.swipe.findViewById(R.id.cardView_background));
        holder.swipe.addSwipeListener(new SwipeLayout.SwipeListener() {
            int pos;

            @Override
            public void onStartOpen(SwipeLayout layout) {
                pos = position;

                // Toast.makeText(context, "pos" + pos, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {


            }

            @Override
            public void onClose(SwipeLayout layout) {


            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });
    }
    private void setAction(MyProductsViewHolder holder, int position){

        holder.editBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOffer) {
                    AddAndEditPRoduct addAndEditPRoduct = new AddAndEditPRoduct();
                    Bundle bundle = new Bundle();
                    bundle.putString("product", new Gson().toJson(data2MyProducts.get(position)));
                    addAndEditPRoduct.setArguments(bundle);
                    addAndEditPRoduct.show(((AppCompatActivity) context).getSupportFragmentManager(),DIALOG_PRODUCT);
                }else {
                    int posiiton = data2MyOffers.get(position).getId();
                    AddOfferFragment addOfferFragment = AddOfferFragment.newInstance(posiiton);
                    addOfferFragment.show(((AppCompatActivity) context).getSupportFragmentManager(),DIALOG_OFFERS);

                }


            }
        });
        holder.deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOffer) {
                    Toast.makeText(context, "delete : " + data2MyProducts.get(position).getId(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "delete : " + data2MyOffers.get(position).getId(), Toast.LENGTH_SHORT).show();

                }
            }
        });
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
        @BindView(R.id.delete_Bt)
        Button deleteBt;
        @BindView(R.id.edit_Bt)
        Button editBt;
        @BindView(R.id.swipe)
        SwipeLayout swipe;
        public MyProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);


        }
    }


}
