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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.general.restaurantitems.Data2RestaurantItems;
import com.example.sofraapp.app.data.model.general.restaurants.Data2Restaurants;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.RememberMy;
import com.example.sofraapp.app.ui.fragment.client.order.DetailesOrderFragment;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sofraapp.app.ui.activity.MainActivity.toolbar;

public class AdapterRestaurantItems extends RecyclerSwipeAdapter<AdapterRestaurantItems.AdapterRestaurantItemsViewHolder> {


    private ArrayList<Data2RestaurantItems> data2RestaurantItemsArrayList = new ArrayList<>();
    private Context context;
    private RememberMy saveData;
    View view;
    private Data2Restaurants data2Restaurants;
    Data2RestaurantItems currentRstaurantItem;

    public AdapterRestaurantItems(Context context, ArrayList<Data2RestaurantItems> restaurantItems, Data2Restaurants data2Restaurants) {
        this.context = context;
        this.data2RestaurantItemsArrayList = restaurantItems;
        saveData = new RememberMy(context);
        this.data2Restaurants = data2Restaurants;
    }

    @NonNull
    @Override
    public AdapterRestaurantItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_restaurant_items, viewGroup, false);
        final AdapterRestaurantItemsViewHolder adapterRestaurantItemsViewHolder = new AdapterRestaurantItemsViewHolder(view);
        return adapterRestaurantItemsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRestaurantItemsViewHolder holder, int position) {
        setSwipe(holder, position);
         currentRstaurantItem = data2RestaurantItemsArrayList.get(position);
        Glide.with(context)
                .load(currentRstaurantItem.getPhotoUrl())
                .into(holder.AdapterRestaurantItemsIMShowImage);
        holder.AdapterRestaurantItemsTVShowNameRestaurant.setText(currentRstaurantItem.getName());
        holder.AdapterRestaurantItemsTVPriceItem.setText(currentRstaurantItem.getPrice());
        holder.AdapterRestaurantItemsTVShowTypes.setText(currentRstaurantItem.getDescription());


    }

    private void setSwipe(AdapterRestaurantItemsViewHolder holder, int position) {
        if (saveData.getSaveState() == 2) {
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

            holder.editBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();

                }
            });
            holder.deleteBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            holder.editBt.setVisibility(View.GONE);
            holder.deleteBt.setVisibility(View.GONE);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    Data2RestaurantItems data2Posts = data2RestaurantItemsArrayList.get(position);
                    DetailesOrderFragment detailesOrderFragment = new DetailesOrderFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("food", new Gson().toJson(data2Posts));
                    bundle.putString("dev", new Gson().toJson(data2Restaurants));
                    detailesOrderFragment.setArguments(bundle);
                    HelperMethod.replece(detailesOrderFragment, ((AppCompatActivity) context).getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar,
                            data2Posts.getName());
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        if (data2RestaurantItemsArrayList != null) {
            return data2RestaurantItemsArrayList.size();
        }
        return 0;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public class AdapterRestaurantItemsViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.cardView_background)
        public CardView cardViewBackground;
        @BindView(R.id.cardView_foreground)
        public CardView cardViewForeground;

        @BindView(R.id.swipe)
        SwipeLayout swipe;
        @BindView(R.id.delete_Bt)
        Button deleteBt;
        @BindView(R.id.edit_Bt)
        Button editBt;

        private View view;

        public AdapterRestaurantItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
       /*     deleteBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Data2RestaurantItems data2Posts = data2RestaurantItemsArrayList.get(position);
                    if (deleteListener != null) deleteListener.deleteItem(data2Posts);
                }
            });
            editBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Data2RestaurantItems data2Posts = data2RestaurantItemsArrayList.get(position);
                    if (editListener != null) editListener.editItem(data2Posts);
                }
            });*/

        }
    }

    public interface showDetial {
        void itemShowDetail(Data2RestaurantItems position);
    }

    public interface delete {
        void deleteItem(Data2RestaurantItems position);
    }

    public interface edit {
        void editItem(Data2RestaurantItems position);
    }

    public void removeItem(int position) {
        data2RestaurantItemsArrayList.remove(position);
        notifyItemRemoved(position);
        //  notifyItemRangeChanged(position,data2RestaurantItemsArrayList.size());
    }

    public void restoretem(Data2RestaurantItems data2RestaurantItems, int position) {
        data2RestaurantItemsArrayList.add(position, data2RestaurantItems);
        //notify item added by position
        notifyItemInserted(position);
    }

    public ArrayList<Data2RestaurantItems> getData() {
        return data2RestaurantItemsArrayList;
    }
}
