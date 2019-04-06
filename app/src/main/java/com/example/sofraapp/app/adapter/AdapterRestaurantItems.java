package com.example.sofraapp.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.general.restaurantitems.Data2RestaurantItems;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterRestaurantItems extends ArrayAdapter<Data2RestaurantItems> {
    private showDetial mListener;

    public AdapterRestaurantItems(Context context, List<Data2RestaurantItems> restaurantItems, showDetial mListener) {
        super(context, 0, restaurantItems);
        this.mListener = mListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        final ViewHolder viewHolder;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_restaurant_items, parent, false);
        }
        viewHolder = new ViewHolder(listItemView);

        final Data2RestaurantItems currentRstaurantItem = getItem(position);
        Glide.with(getContext())
                .load(currentRstaurantItem.getPhotoUrl())
                .into(viewHolder.AdapterRestaurantItemsIMShowImage);
        viewHolder.AdapterRestaurantItemsTVShowNameRestaurant.setText(currentRstaurantItem.getName());
        viewHolder.AdapterRestaurantItemsTVPriceItem.setText(currentRstaurantItem.getPrice());
        viewHolder.AdapterRestaurantItemsTVShowTypes.setText(currentRstaurantItem.getDescription());

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data2RestaurantItems data2Posts = currentRstaurantItem;
                if (mListener != null) mListener.itemShowDetail(data2Posts);

            }
        });

        return listItemView;
    }

    static
    class ViewHolder {
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface showDetial {
        void itemShowDetail(Data2RestaurantItems position);
    }
}




















