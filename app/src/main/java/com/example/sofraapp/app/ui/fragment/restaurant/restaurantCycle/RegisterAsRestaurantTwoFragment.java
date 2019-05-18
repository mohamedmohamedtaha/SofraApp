package com.example.sofraapp.app.ui.fragment.restaurant.restaurantCycle;


import android.os.Bundle;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.cyclelogin.registerasrestaurant.RegisterAsRestaurant;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.general.categories.Categories;
import com.example.sofraapp.app.data.model.general.categories.DataCategories;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.SaveData;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

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
public class RegisterAsRestaurantTwoFragment extends Fragment {
    @BindView(R.id.RegisterAsRestaurantTwoFragment_SP_Spicefed)
    Spinner RegisterAsRestaurantTwoFragmentSPSpicefed;
    @BindView(R.id.RegisterAsRestaurantTwoFragment_Min_request)
    TextInputEditText RegisterAsRestaurantTwoFragmentMinRequest;
    @BindView(R.id.RegisterAsRestaurantTwoFragment_Price_Delevery)
    TextInputEditText RegisterAsRestaurantTwoFragmentPriceDelevery;
    @BindView(R.id.RegisterAsRestaurantTwoFragment_Progress_Bar)
    ProgressBar RegisterAsRestaurantTwoFragmentProgressBar;
    @BindView(R.id.RegisterAsRestaurantTwoFragment_WhatsApp)
    TextInputEditText RegisterAsRestaurantTwoFragmentWhatsApp;
    @BindView(R.id.RegisterAsRestaurantTwoFragment_Phone)
    TextInputEditText RegisterAsRestaurantTwoFragmentPhone;
    @BindView(R.id.RegisterAsRestaurantTwoFragment_IM_Add_Photo)
    ImageView RegisterAsRestaurantTwoFragmentIMAdd_Photo;
    @BindView(R.id.RegisterAsRestaurantTwoFragment_BT_Register)
    Button RegisterAsRestaurantTwoFragmentBTRegister;
    Unbinder unbinder;
    SaveData saveData;
    APIServices apiServices;
    ArrayList<String> strings = new ArrayList<>();
    final ArrayList<Integer> IdsCategory = new ArrayList<>();
    String getResult;
    int IDPosition;
    private boolean check_network;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private int counter = 1;


    public RegisterAsRestaurantTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_as_restaurant_two, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
            return view;
        }
        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getCategories().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                Categories categories = response.body();
                if (categories.getStatus() == 1) {
                    try {
                        strings.add(getString(R.string.select_categories));
                        IdsCategory.add(0);
                        List<DataCategories> dataCategoriesList = categories.getData();
                        for (int i = 0; i < dataCategoriesList.size(); i++) {
                            getResult = dataCategoriesList.get(i).getName();
                            strings.add(getResult);
                            IDPosition = dataCategoriesList.get(i).getId();
                            IdsCategory.add(IDPosition);
                        }
                        HelperMethod.showGovernorates(strings, getActivity(), RegisterAsRestaurantTwoFragmentSPSpicefed);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.RegisterAsRestaurantTwoFragment_IM_Add_Photo, R.id.RegisterAsRestaurantTwoFragment_BT_Register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.RegisterAsRestaurantTwoFragment_IM_Add_Photo:
                Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        // TODO accept the result.
                        ImagesFiles.clear();
                        ImagesFiles.addAll(result);
                        Glide.with(getContext()).load(ImagesFiles.get(0).getPath()).into(RegisterAsRestaurantTwoFragmentIMAdd_Photo);

                    }
                };
                HelperMethod.openAlbum(counter, getActivity(), ImagesFiles, action);
                break;
            case R.id.RegisterAsRestaurantTwoFragment_BT_Register:
                String minOrder = RegisterAsRestaurantTwoFragmentMinRequest.getText().toString().trim();
                String priceDelevery = RegisterAsRestaurantTwoFragmentPriceDelevery.getText().toString().trim();
                String phone = RegisterAsRestaurantTwoFragmentPhone.getText().toString().trim();
                String whatsApp = RegisterAsRestaurantTwoFragmentWhatsApp.getText().toString().trim();
                RequestBody nameBody = HelperMethod.convertToRequestBody(saveData.getName());
                RequestBody mailBody = HelperMethod.convertToRequestBody(saveData.getEmail());
                RequestBody passwordBody = HelperMethod.convertToRequestBody(saveData.getPassword());
                RequestBody retryPasswordBody = HelperMethod.convertToRequestBody(saveData.getRetryPassword());
                RequestBody addressBody = HelperMethod.convertToRequestBody(saveData.getAddress());
                RequestBody cityIdBody = HelperMethod.convertToRequestBody(saveData.getCityId());
                RequestBody hayIdBody = HelperMethod.convertToRequestBody(saveData.getHayId());
                RequestBody minOrderBody = HelperMethod.convertToRequestBody(minOrder);
                RequestBody priceDeleverynBody = HelperMethod.convertToRequestBody(priceDelevery);
                RequestBody phoneBody = HelperMethod.convertToRequestBody(phone);
                RequestBody whatsAppBody = HelperMethod.convertToRequestBody(whatsApp);
                if (ImagesFiles.size() <= 0) {
                    Toast.makeText(getActivity(), getString(R.string.select_photo), Toast.LENGTH_SHORT).show();
                    return;
                }
                RegisterAsRestaurantTwoFragmentProgressBar.setVisibility(View.VISIBLE);
                MultipartBody.Part multiPartImage = HelperMethod.convertFileToMultipart(ImagesFiles.get(0).getPath(), "photo");
                apiServices.getRegisterAsRestaurant(nameBody, mailBody, passwordBody
                        , retryPasswordBody, phoneBody, addressBody, cityIdBody, whatsAppBody,
                        hayIdBody, nameBody, priceDeleverynBody, minOrderBody, multiPartImage, nameBody)
                        .enqueue(new Callback<RegisterAsRestaurant>() {
                            @Override
                            public void onResponse(Call<RegisterAsRestaurant> call, Response<RegisterAsRestaurant> response) {
                                RegisterAsRestaurant register = response.body();
                                try {
                                    if (register.getStatus() == 1) {
                                        RegisterAsRestaurantTwoFragmentProgressBar.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), register.getMsg(), Toast.LENGTH_SHORT).show();
                                    }else {
                                        RegisterAsRestaurantTwoFragmentProgressBar.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), register.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }catch (Exception e){
                                    RegisterAsRestaurantTwoFragmentProgressBar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<RegisterAsRestaurant> call, Throwable t) {
                                RegisterAsRestaurantTwoFragmentProgressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }
    }
}
