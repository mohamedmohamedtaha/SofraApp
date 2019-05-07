package com.example.sofraapp.app.ui.fragment;


import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.cycleRestaurant.offers.newoffer.NewOffer;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.DateModel;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.SaveData;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofraapp.app.data.rest.RetrofitClient.getRetrofit;
import static com.example.sofraapp.app.helper.HelperMethod.GET_DATA;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddOfferFragment extends Fragment {
    @BindView(R.id.AddOfferFragment_Tiet_Name_Offer)
    TextInputEditText AddOfferFragmentTietNameOffer;
    @BindView(R.id.AddOfferFragment_Tiet_Describe)
    TextInputEditText AddOfferFragmentTietDescribe;
    @BindView(R.id.AddOfferFragment_Tiet_Price_Offer)
    TextInputEditText AddOfferFragmentTietPriceOffer;
    @BindView(R.id.AddOfferFragment_To)
    TextView AddOfferFragmentTo;
    @BindView(R.id.AddOfferFragment_From)
    TextView AddOfferFragmentFrom;
    @BindView(R.id.AddOfferFragment_IM_Add_Photo)
    ImageView AddOfferFragmentIMAddPhoto;
    @BindView(R.id.AddProductFragment_Bt_Add)
    Button AddProductFragmentBtAdd;
    @BindView(R.id.AddProductFragment_Progress_Bar)
    ProgressBar AddProductFragmentProgressBar;
    Unbinder unbinder;
    SaveData saveData;
    APIServices apiServices;
    boolean check_network;
    private DateModel dateModel1;
    private DateModel dateModel2;
    final Calendar getDatenow = Calendar.getInstance();
    private int startYear;
    private int startMonth;
    private int startDay;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private int counter = 1;

    public AddOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_offer, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        startYear = getDatenow.get(Calendar.YEAR);
        startMonth = getDatenow.get(Calendar.MONTH);
        startDay = getDatenow.get(Calendar.DAY_OF_MONTH);
        dateModel1 = new DateModel(String.valueOf(startYear), String.valueOf(startMonth), String.valueOf(startDay), null);
        dateModel2 = new DateModel(String.valueOf(startYear), String.valueOf(startMonth), String.valueOf(startDay), null);

        AddOfferFragmentFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethod.showCalender(getActivity(), getString(R.string.from), AddOfferFragmentFrom, dateModel1);
            }
        });
        AddOfferFragmentTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethod.showCalender(getActivity(), getString(R.string.to), AddOfferFragmentTo, dateModel2);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.AddOfferFragment_IM_Add_Photo, R.id.AddProductFragment_Bt_Add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.AddOfferFragment_IM_Add_Photo:
                Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        // TODO accept the result.
                        ImagesFiles.clear();
                        ImagesFiles.addAll(result);
                    }
                };
                HelperMethod.openAlbum(counter, getActivity(), ImagesFiles, action);
                break;
            case R.id.AddProductFragment_Bt_Add:
                check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
                if (check_network == false) {
                    return;
                }
                if (saveData.getApi_token() == null) {
                    Toast.makeText(getActivity(), getString(R.string.login_please), Toast.LENGTH_SHORT).show();
                    return;
                }

                Glide.with(getContext()).load(ImagesFiles).into(AddOfferFragmentIMAddPhoto);
                String name = AddOfferFragmentTietNameOffer.getText().toString().trim();
                String pre_description = AddOfferFragmentTietDescribe.getText().toString().trim();
                String price = AddOfferFragmentTietPriceOffer.getText().toString().trim();
                String from = AddOfferFragmentFrom.getText().toString().trim();
                String to = AddOfferFragmentTo.getText().toString().trim();
                if (name.isEmpty() || pre_description.isEmpty() || price.isEmpty() || from.isEmpty() || to.isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.filed_request), Toast.LENGTH_SHORT).show();
                    return;
                }
                RequestBody nameBody = HelperMethod.convertToRequestBody(name);
                RequestBody descriptionBody = HelperMethod.convertToRequestBody(pre_description);
                RequestBody priceBody = HelperMethod.convertToRequestBody(price);
                RequestBody fromBody = HelperMethod.convertToRequestBody(from);
                RequestBody toBody = HelperMethod.convertToRequestBody(to);
                RequestBody getApi_tokenBody = HelperMethod.convertToRequestBody(saveData.getApi_token());
                if (ImagesFiles.size() <= 0) {
                    Toast.makeText(getActivity(), getString(R.string.select_photo), Toast.LENGTH_SHORT).show();
                    return;
                }
                MultipartBody.Part multiPartImage = HelperMethod.convertFileToMultipart(ImagesFiles.get(0).getPath(), "image");
                apiServices = getRetrofit().create(APIServices.class);
                apiServices.getNewOffer(descriptionBody, priceBody, fromBody, nameBody, multiPartImage, toBody, getApi_tokenBody).enqueue(new Callback<NewOffer>() {
                    @Override
                    public void onResponse(Call<NewOffer> call, Response<NewOffer> response) {
                        NewOffer newOffer = response.body();
                        try {
                            if (newOffer.getStatus() == 1) {
                                Toast.makeText(getActivity(), newOffer.getMsg(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), newOffer.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<NewOffer> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                break;
        }
    }
}
