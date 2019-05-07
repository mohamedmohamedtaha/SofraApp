package com.example.sofraapp.app.ui.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.adapter.AdapterReviews;
import com.example.sofraapp.app.data.model.general.reviews.Data2Reviews;
import com.example.sofraapp.app.data.model.general.reviews.Reviews;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.SaveData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofraapp.app.data.rest.RetrofitClient.getRetrofit;
import static com.example.sofraapp.app.helper.HelperMethod.GET_DATA;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment {


    @BindView(R.id.ReviewsFragment_BT)
    Button ReviewsFragmentBT;
    @BindView(R.id.ReviewsFragment_LV)
    ListView ReviewsFragmentLV;
    @BindView(R.id.ReviewsFragment_TV_Empty_View)
    TextView ReviewsFragmentTVEmptyView;
    @BindView(R.id.ReviewsFragment_Loading_Indicator)
    ProgressBar ReviewsFragmentLoadingIndicator;
    Unbinder unbinder;
    SaveData saveData;
    Bundle bundle;
    private APIServices apiServices;
    private AdapterReviews adapterReviews;
    public static final String DIALOG_PERMISSION = "dialogPermission";

    public ReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comments, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        ReviewsFragmentLV.setEmptyView(ReviewsFragmentTVEmptyView);
        ReviewsFragmentLoadingIndicator.setVisibility(View.VISIBLE);
        apiServices = getRetrofit().create(APIServices.class);
     //   if (saveData.getApi_token() != null ){
        apiServices.getReviews(saveData.getApi_token(), saveData.getId_position(),1).enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                Reviews reviews = response.body();
                if (reviews.getStatus() == 1 ){
                    List<Data2Reviews> data2Reviews = reviews.getData().getData();
                    adapterReviews = new AdapterReviews(getActivity(),data2Reviews);
                    ReviewsFragmentLV.setAdapter(adapterReviews);
                    ReviewsFragmentLoadingIndicator.setVisibility(View.GONE);
                }else {
                    Toast.makeText(getActivity(), reviews.getMsg(), Toast.LENGTH_SHORT).show();
                    ReviewsFragmentLoadingIndicator.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                ReviewsFragmentLoadingIndicator.setVisibility(View.GONE);
            }
        });//}else {
            //ReviewsFragmentLoadingIndicator.setVisibility(View.GONE);

      //  }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ReviewsFragment_BT)
    public void onViewClicked() {
        AddReviewFragment addReviewFragment = new AddReviewFragment();
        bundle = new Bundle();
        bundle.putParcelable(GET_DATA,saveData);
        addReviewFragment.setArguments(bundle);
        addReviewFragment.show(getFragmentManager(),DIALOG_PERMISSION);

      /*  if (saveData.getApi_token() == null){
            Toast.makeText(getActivity(), "يجب أن تقوم بتسجيل الدخول أولا ", Toast.LENGTH_SHORT).show();
        }else {

        }*/
    }
}















