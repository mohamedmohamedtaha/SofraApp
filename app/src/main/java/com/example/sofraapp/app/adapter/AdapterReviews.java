package com.example.sofraapp.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.general.reviews.Data2Reviews;
import com.example.sofraapp.app.data.model.general.reviews.Reviews;
import com.example.sofraapp.app.helper.HelperMethod;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterReviews extends ArrayAdapter<Data2Reviews> {
    public AdapterReviews(Context context, List<Data2Reviews> reviewsArrayAdapter) {
        super(context, 0, reviewsArrayAdapter);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Data2Reviews reviews = getItem(position);

        ViewHolder viewHolder ;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_reviews, parent, false);
            viewHolder = new ViewHolder(convertView);

            //Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.AdapterReviewsNameReview.setText(reviews.getClient().getName());
        viewHolder.AdapterReviewsDateReview.setText(reviews.getUpdatedAt());
        viewHolder.AdapterReviewsDetaileReview.setText(reviews.getComment());
        HelperMethod.getReating(Integer.parseInt(reviews.getRate()), viewHolder.AdapterReviewsRBRateReviews);

        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.Adapter_Reviews_Name_Review)
        TextView AdapterReviewsNameReview;
        @BindView(R.id.Adapter_Reviews_RB_Rate_Reviews)
        RatingBar AdapterReviewsRBRateReviews;
        @BindView(R.id.Adapter_Reviews_Date_Review)
        TextView AdapterReviewsDateReview;
        @BindView(R.id.Adapter_Reviews_Detaile_Review)
        TextView AdapterReviewsDetaileReview;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
