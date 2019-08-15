package com.example.sofraapp.app.ui.fragment.restaurant.offers;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.restaurant.offers.newoffer.NewOffer;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.DateModel;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.RememberMy;
import com.google.android.material.textfield.TextInputEditText;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class AddOfferFragment extends DialogFragment {
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
    @BindView(R.id.AddOfferFragment_Bt_Add)
    Button AddOfferFragmentBtAdd;
    @BindView(R.id.AddOfferFragment_Progress_Bar)
    ProgressBar AddOfferFragmentProgressBar;
    Unbinder unbinder;
    private static final String EXTRA_ID_OFFERS = "id";
    APIServices apiServices;
    boolean check_network;
    RememberMy rememberMy;
    @BindView(R.id.AddProductFragment_TVTitleCategory)
    TextView AddProductFragmentTVTitleCategory;
    private DateModel dateModel1;
    private DateModel dateModel2;
    final Calendar getDatenow = Calendar.getInstance();
    private int startYear;
    private int startMonth;
    private int startDay;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private int counter = 1;
    View view;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    Long id_offer;

    public AddOfferFragment() {
        // Required empty public constructor
    }

    public static AddOfferFragment newInstance(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_ID_OFFERS, id);
        AddOfferFragment addOfferFragment = new AddOfferFragment();
        addOfferFragment.setArguments(bundle);
        return addOfferFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_offer, null);
        unbinder = ButterKnife.bind(this, view);
        rememberMy = new RememberMy(getActivity());
        startYear = getDatenow.get(Calendar.YEAR);
        startMonth = getDatenow.get(Calendar.MONTH);
        startDay = getDatenow.get(Calendar.DAY_OF_MONTH);
        dateModel1 = new DateModel(String.valueOf(startDay), String.valueOf(startMonth), String.valueOf(startYear), null);
        dateModel2 = new DateModel(String.valueOf(startDay), String.valueOf(startMonth), String.valueOf(startYear), null);
        AddOfferFragmentTietNameOffer.setHint(getString(R.string.name_offer));

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

       /* //For edit Offer
        if (getArguments() != null && getArguments().getLong(EXTRA_ID_OFFERS) != 0) {
            id_offer = getArguments().getLong(EXTRA_ID_OFFERS);
            AddProductFragmentTVTitleCategory.setText(getString(R.string.eidt_offer));


        }*/

        builder = new AlertDialog.Builder(getActivity());
        builder.setView(view).setIcon(R.mipmap.logo);
        dialog = builder.create();
        dialog.show();

        return dialog;
    }

    @OnClick({R.id.AddOfferFragment_IM_Add_Photo, R.id.AddOfferFragment_Bt_Add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.AddOfferFragment_IM_Add_Photo:
                Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        // TODO accept the result.
                        ImagesFiles.clear();
                        ImagesFiles.addAll(result);
                        Glide.with(getContext()).load(ImagesFiles.get(0).getPath()).into(AddOfferFragmentIMAddPhoto);
                    }
                };
                HelperMethod.openAlbum(counter, getActivity(), ImagesFiles, action);
                break;
            case R.id.AddOfferFragment_Bt_Add:
                check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
                if (check_network == false) {
                    return;
                }
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
                RequestBody getApi_tokenBody = HelperMethod.convertToRequestBody(rememberMy.getAPIKey());
                if (ImagesFiles.size() <= 0) {
                    Toast.makeText(getActivity(), getString(R.string.select_photo), Toast.LENGTH_SHORT).show();
                    return;
                }
                MultipartBody.Part multiPartImage = HelperMethod.convertFileToMultipart(ImagesFiles.get(0).getPath(),
                        "photo");
                AddOfferFragmentProgressBar.setVisibility(View.VISIBLE);
                apiServices = getRetrofit().create(APIServices.class);
                apiServices.getNewOffer(descriptionBody, priceBody, fromBody, nameBody, multiPartImage, toBody, getApi_tokenBody).enqueue(new Callback<NewOffer>() {
                    @Override
                    public void onResponse(Call<NewOffer> call, Response<NewOffer> response) {
                        NewOffer newOffer = response.body();
                        try {
                            if (newOffer.getStatus() == 1) {
                                Toast.makeText(getActivity(), newOffer.getMsg(), Toast.LENGTH_SHORT).show();
                                AddOfferFragmentProgressBar.setVisibility(View.GONE);
                                dialog.dismiss();

                            } else {
                                Toast.makeText(getActivity(), newOffer.getMsg(), Toast.LENGTH_SHORT).show();
                                AddOfferFragmentProgressBar.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            AddOfferFragmentProgressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewOffer> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        AddOfferFragmentProgressBar.setVisibility(View.GONE);

                    }
                });
                break;
        }
    }
}
