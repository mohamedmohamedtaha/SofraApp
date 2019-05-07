package com.example.sofraapp.app.ui.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.cycleClient.addreview.AddReview;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.SaveData;

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
public class AddReviewFragment extends DialogFragment {
    AlertDialog dialog;
    @BindView(R.id.AddReviewFragment_RB_Rate_Reviews)
    RatingBar AddReviewFragmentRBRateReviews;
    @BindView(R.id.AddReviewFragment_ET_Add_Review_Here)
    TextInputEditText AddReviewFragmentETAddReviewHere;
    @BindView(R.id.AddReviewFragment_BT_Add)
    Button AddReviewFragmentBTAdd;
    Unbinder unbinder;
    SaveData saveData ;
    private APIServices apiServices;
    float getRate;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_review, null);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        return dialog;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.AddReviewFragment_BT_Add)
    public void onViewClicked() {
        if (saveData.getApi_token() == null) {
            Toast.makeText(getActivity(), getString(R.string.api_request), Toast.LENGTH_SHORT).show();
            return;
        }
        int rate =(int) AddReviewFragmentRBRateReviews.getRating();
        String commit = AddReviewFragmentETAddReviewHere.getText().toString().trim();
        if (commit.isEmpty() || rate <=0 ){
            Toast.makeText(getActivity(), getString(R.string.filed_request), Toast.LENGTH_SHORT).show();
        }
        else {
            apiServices = getRetrofit().create(APIServices.class);
            apiServices.getAddReview(rate, commit, saveData.getId_position(), saveData.getApi_token())
                    .enqueue(new Callback<AddReview>() {
                        @Override
                        public void onResponse(Call<AddReview> call, Response<AddReview> response) {
                            AddReview addReview = response.body();
                            if (addReview.getStatus() == 1){
                                Toast.makeText(getActivity(), addReview.getMsg(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }else {
                                Toast.makeText(getActivity(), addReview.getMsg(), Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<AddReview> call, Throwable t) {
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }
}
