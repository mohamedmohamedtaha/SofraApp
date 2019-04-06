package com.example.sofraapp.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.general.offers.Data2Offers;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterOffers extends ArrayAdapter<Data2Offers> {
    public AdapterOffers(Context context, List<Data2Offers> offers) {
        super(context, 0, offers);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        ViewHolder viewHolder;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_offers, parent, false);
        }
        viewHolder = new ViewHolder(listItemView);
        Data2Offers currentOffer = getItem(position);

        if (TextUtils.isEmpty(currentOffer.getPhoto())){
            Glide.with(getContext()).load(R.drawable.home)
                    .centerCrop()
                    .into(viewHolder.IMShowImage);
        }else {
            Glide.with(getContext())
                    .load("http://ipda3.com/sofra/"+currentOffer.getPhoto())
                    .error(R.drawable.home)
                    .centerCrop()
                    .into(viewHolder.IMShowImage);
        }

        viewHolder.TVShowNameRestaurant.setText(currentOffer.getName());
        viewHolder.TVShowOfer.setText(currentOffer.getDescription());
        viewHolder.TVShowPrice.setText(currentOffer.getPrice());
        viewHolder.TVShowPorit.setText(currentOffer.getEndingAt());


        return listItemView;
    }

    static
      class ViewHolder {
        @BindView(R.id.IM_Show_Image)
        ImageView IMShowImage;
        @BindView(R.id.TV_Show_Ofer)
        TextView TVShowOfer;
        @BindView(R.id.TV_Show_Name_Restaurant)
        TextView TVShowNameRestaurant;
        @BindView(R.id.TV_Show_Porit)
        TextView TVShowPorit;
        @BindView(R.id.TV_Show_Price)
        TextView TVShowPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
