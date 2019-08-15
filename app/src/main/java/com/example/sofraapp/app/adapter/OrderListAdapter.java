package com.example.sofraapp.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.general.restaurantitems.Data2RestaurantItems;
import com.example.sofraapp.app.data.room.Food;
import com.example.sofraapp.app.data.room.OrdersViewModel;
import com.example.sofraapp.app.data.room.RoomDao;
import com.example.sofraapp.app.data.room.RoomManger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {
    private final LayoutInflater inflater;
    private List<Data2RestaurantItems> mfoods =new ArrayList<>(); //Cached copy of words

    Double Total = 0.0;
    private TextView carShopFragmentTvTotal;
    private RoomManger roomManger ;
    private RoomDao roomDao;
    private boolean hide ;

    public OrderListAdapter(Context context,TextView carShopFragmentTvTotal,List<Data2RestaurantItems> mfoods) {
        inflater = LayoutInflater.from(context);
        this.mfoods = mfoods;
        this.carShopFragmentTvTotal = carShopFragmentTvTotal;
        roomManger = RoomManger.getInstance(context);
        roomDao = roomManger.roomDao();
        if (mfoods != null){
            for (int i = 0; i<mfoods.size(); i++){
                Total = Total + (Double.parseDouble(mfoods.get(i).getPrice()) * Double.parseDouble(mfoods.get(i).getCounter()));
                carShopFragmentTvTotal.setText(" " + Total);

            }
        }


    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_room_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
              setAction(holder,position);
        if (mfoods != null) {
            Data2RestaurantItems current = mfoods.get(position);
            holder.TVPrice.setText(current.getPrice());
            holder.TVTitle.setText(current.getName());
           // holder.TVTotal.setText(current.getTotal_price());
            int y = Integer.parseInt(mfoods.get(position).getCounter());
            holder.TVCount.setText(String.valueOf(y));
            int x = (int)(Double.parseDouble(mfoods.get(position).getPrice()) *
                    Double.parseDouble(String.valueOf(mfoods.get(position).getCounter())));
            holder.TVTotal.setText(String.valueOf(x));

            Glide.with(inflater.getContext())
                    .load(current.getPhotoUrl())
                    .error(R.drawable.home)
                    .centerCrop()
                    .into(holder.ImageViewPath);
        } else {
            holder.TVPrice.setText("No word");
            holder.BTPlus.setVisibility(View.GONE);
            holder.BTSum.setVisibility(View.GONE);
            carShopFragmentTvTotal.setVisibility(View.GONE);

        }
    }

    public void setFoods(List<Data2RestaurantItems> foods) {
        mfoods = foods;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mfoods != null)
            return mfoods.size();
        else return 0;
    }
    private void setAction(final  OrderViewHolder holder,final  int position){
        holder.BTSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(holder,position);
            }
        });
        holder.BTPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(holder, position);

            }
        });
    }
    private void update(Data2RestaurantItems food) {

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {

                roomDao.updateItemToCar(food);


            }

        });

    }

    private void removeItem(OrderViewHolder holder,int position){
        try {
            Integer count= 0;
            if (!holder.TVCount.getText().toString().equals("")){
                count = Integer.valueOf(holder.TVCount.getText().toString());
            }if (!count.equals(0)){
                count = count -1;
                mfoods.get(position).setCounter(String.valueOf(count));
                holder.TVCount.setText(String.valueOf(count));
                holder.TVTotal.setText(String.valueOf(Double.parseDouble(mfoods.get(position).getPrice()) *
                        Double.parseDouble(mfoods.get(position).getCounter())));
                Total = Total - Double.parseDouble(mfoods.get(position).getPrice());
                carShopFragmentTvTotal.setText( " " + Total);
                update(mfoods.get(position));
            }

        }catch (Exception e){

        }

    }
    private void addItem(OrderViewHolder holder,int position){
        try {
            Integer count =0;
            if (!holder.TVCount.getText().toString().equals("")){
                count = Integer.valueOf(holder.TVCount.getText().toString());
            }
            count = count +1;
            mfoods.get(position).setCounter(String.valueOf(count));
            holder.TVCount.setText(String.valueOf(count));
            holder.TVTotal.setText(String.valueOf(Double.parseDouble(mfoods.get(position).getPrice()) *
                    Double.parseDouble(mfoods.get(position).getCounter())));
            Total = Total + Double.parseDouble(mfoods.get(position).getPrice());
            carShopFragmentTvTotal.setText( " " + Total);
            update(mfoods.get(position));




        }catch (Exception e){

        }
    }
    class OrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ImageView_Path)
        ImageView ImageViewPath;
        @BindView(R.id.TV_Title)
        TextView TVTitle;
        @BindView(R.id.BT_Sum)
        Button BTSum;
        @BindView(R.id.TV_Count)
        TextView TVCount;
        @BindView(R.id.BT_Plus)
        Button BTPlus;
        @BindView(R.id.TV_Price)
        TextView TVPrice;
        @BindView(R.id.TV_Total)
        TextView TVTotal;

        public OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}