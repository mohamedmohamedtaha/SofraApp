package com.example.sofraapp.app.ui.fragment.restaurant.restaurantCycle;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.general.categories.Categories;
import com.example.sofraapp.app.data.model.general.categories.DataCategories;
import com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.cyclelogin.registerasrestaurant.RegisterAsRestaurant;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.MediaLoader;
import com.example.sofraapp.app.helper.SaveData;
import com.example.sofraapp.app.helper.library.multispinner.MultiSelectionSpinner;
import com.example.sofraapp.app.ui.activity.LoginActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
public class RegisterAsRestaurantTwoFragment extends Fragment {
    @BindView(R.id.RegisterAsRestaurantTwoFragment_SP_Spicefed)
    MultiSelectionSpinner RegisterAsRestaurantTwoFragmentSPSpicefed;
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
    @BindView(R.id.RegisterAsRestaurantTwoFragment_Delivery_Period)
    TextInputEditText RegisterAsRestaurantTwoFragmentDeliveryPeriod;
    private SaveData saveData;
    APIServices apiServices;
    ArrayList<String> categoriesName = new ArrayList<>();
     ArrayList<Integer> IdsCategory = new ArrayList<>();
    String getResult;
    int IDPosition;
    private List<String> state;
    @BindView(R.id.RegisterAsRestaurantTwoFragment_SP_State)
    Spinner RegisterAsRestaurantTwoFragmentSPState;
    @BindView(R.id.RegisterAsRestaurantFragmentTwo_Progress_Bar)
    ProgressBar RegisterAsRestaurantFragmentTwoProgressBar;

    private boolean check_network;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private int counter = 1;
    private List<Integer> CategoriesSelectedId = new ArrayList<>();


    public RegisterAsRestaurantTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_as_restaurant_two, container, false);
        unbinder = ButterKnife.bind(this, view);
        check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        Bundle bundle = getArguments();
        apiServices = getRetrofit().create(APIServices.class);

        if (bundle != null) {
            saveData = Parcels.unwrap(bundle.getParcelable("page1"));
            Toast.makeText(getActivity(), " NAme:"+ saveData.getName() +"/n" +
                    "city:" + saveData.getCityId() +"/n" +
                    "Id:" + saveData.getHayId(), Toast.LENGTH_SHORT).show();
        }

        if (check_network == false) {
            return view;
        }
        state = new ArrayList<>();
        state.add(getString(R.string.state));
        state.add(getString(R.string.open_ar));
        state.add(getString(R.string.close_ar));
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, state);
        RegisterAsRestaurantTwoFragmentSPState.setAdapter(stateAdapter);
        getCategory();

        return view;
    }
    private void getCategory(){
        apiServices.getCategories().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                Categories categories = response.body();
                if (categories.getStatus() == 1) {
                    try {
                        categoriesName = new ArrayList<>();
                        IdsCategory = new ArrayList<>();
                        categoriesName.add(getString(R.string.select_categories));
                        List<DataCategories> dataCategoriesList = categories.getData();
                        for (int i = 0; i < dataCategoriesList.size(); i++) {
                            getResult = dataCategoriesList.get(i).getName();
                            categoriesName.add(getResult);
                            IDPosition = dataCategoriesList.get(i).getId();
                            IdsCategory.add(IDPosition);
                        }
                        RegisterAsRestaurantTwoFragmentSPSpicefed.setItems(categoriesName);
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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void addImage() {
        Album album = new Album();
        Album.initialize(AlbumConfig.newBuilder(getActivity())
                .setAlbumLoader(new MediaLoader())
                .setLocale(Locale.ENGLISH).build());
        album.image(this)//Image and Video Mix options.
                .multipleChoice()
                .columnCount(3)
                .selectCount(1)
                .camera(true)
                .checkedList(ImagesFiles) //To reverse the list.
                .widget(Widget.newLightBuilder(getActivity())
                        .title("")
                        .statusBarColor(Color.WHITE)
                        .toolBarColor(Color.WHITE)
                        .navigationBarColor(Color.WHITE)
                        .mediaItemCheckSelector(Color.BLACK, Color.GREEN)//Image or video selection box.
                        .bucketItemCheckSelector(Color.RED, Color.YELLOW)//Select the folder selection box.
                        .build())
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        ImagesFiles = new ArrayList<>();
                        ImagesFiles.addAll(result);
                        Glide.with(getContext()).load(ImagesFiles.get(0).getPath())
                                .into(RegisterAsRestaurantTwoFragmentIMAdd_Photo);

                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {

                    }
                }).start();
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
                String delivery_period = RegisterAsRestaurantTwoFragmentDeliveryPeriod.getText().toString().trim();
                String minOrder = RegisterAsRestaurantTwoFragmentMinRequest.getText().toString().trim();
                String priceDelevery = RegisterAsRestaurantTwoFragmentPriceDelevery.getText().toString().trim();
                String phone = RegisterAsRestaurantTwoFragmentPhone.getText().toString().trim();
                String whatsApp = RegisterAsRestaurantTwoFragmentWhatsApp.getText().toString().trim();
                RequestBody nameBody = HelperMethod.convertToRequestBody(saveData.getName());
                RequestBody mailBody = HelperMethod.convertToRequestBody(saveData.getEmail());
                RequestBody passwordBody = HelperMethod.convertToRequestBody(saveData.getPassword());
                RequestBody retryPasswordBody = HelperMethod.convertToRequestBody(saveData.getRetryPassword());
                RequestBody addressBody = HelperMethod.convertToRequestBody(saveData.getAddress());
               // RequestBody cityIdBody = HelperMethod.convertToRequestBody(saveData.getCityId());
                RequestBody hayIdBody = HelperMethod.convertToRequestBody(saveData.getHayId());
                RequestBody minOrderBody = HelperMethod.convertToRequestBody(minOrder);
                RequestBody priceDeleverynBody = HelperMethod.convertToRequestBody(priceDelevery);
                RequestBody phoneBody = HelperMethod.convertToRequestBody(phone);
                RequestBody whatsAppBody = HelperMethod.convertToRequestBody(whatsApp);
                RequestBody delivery_periodBody = HelperMethod.convertToRequestBody(delivery_period);

                List<RequestBody> CategoriesSelectedIdRequest = new ArrayList<>();
                //  int categoryID = IdsCategory.get(RegisterAsRestaurantTwoFragmentSPSpicefed.getSelectedItemPosition());
                int stateId = RegisterAsRestaurantTwoFragmentSPState.getSelectedItemPosition();

                if (minOrder.isEmpty() || priceDelevery.isEmpty() || phone.isEmpty()
                        || whatsApp.isEmpty() || stateId <= 0) {
                    Toast.makeText(getActivity(), getString(R.string.filed_request), Toast.LENGTH_SHORT).show();
                    return;
                }
                RequestBody stateBody = HelperMethod.convertToRequestBody(state.get(RegisterAsRestaurantTwoFragmentSPState.getSelectedItemPosition()));

                if (RegisterAsRestaurantTwoFragmentSPSpicefed.getSelectedStrings().size() > 0) {
                    CategoriesSelectedId = new ArrayList<>();

                    for (int i = 0; i < RegisterAsRestaurantTwoFragmentSPSpicefed.getSelectedStrings().size(); i++) {
                        for (int j = 0; j < categoriesName.size(); j++) {
                            if (categoriesName.get(j).equals(RegisterAsRestaurantTwoFragmentSPSpicefed.getSelectedStrings().get(i))) {
                                CategoriesSelectedId.add(IdsCategory.get(j));
                            }
                        }

                    }
                    for (int j = 0; j < CategoriesSelectedId.size(); j++) {
                        CategoriesSelectedIdRequest.add(HelperMethod.convertToRequestBody(String.valueOf(CategoriesSelectedId.get(j))));


                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.select_one), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ImagesFiles.size() <= 0) {
                    Toast.makeText(getActivity(), getString(R.string.select_photo), Toast.LENGTH_SHORT).show();
                    return;
                }

                RegisterAsRestaurantTwoFragmentProgressBar.setVisibility(View.VISIBLE);
                MultipartBody.Part multiPartImage = HelperMethod.convertFileToMultipart(ImagesFiles.get(0).getPath(), "photo");
                apiServices.getRegisterAsRestaurant(nameBody, mailBody, passwordBody
                        , retryPasswordBody, phoneBody, addressBody, whatsAppBody,hayIdBody,
                        CategoriesSelectedIdRequest, delivery_periodBody, priceDeleverynBody, minOrderBody, multiPartImage, stateBody)
                        .enqueue(new Callback<RegisterAsRestaurant>() {
                            @Override
                            public void onResponse(Call<RegisterAsRestaurant> call, Response<RegisterAsRestaurant> response) {
                                RegisterAsRestaurant register = response.body();
                                try {
                                    if (register.getStatus() == 1) {
                                        RegisterAsRestaurantTwoFragmentProgressBar.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), register.getMsg(), Toast.LENGTH_SHORT).show();
                                        HelperMethod.startActivity(getActivity(), LoginActivity.class);
                                    } else {
                                        RegisterAsRestaurantTwoFragmentProgressBar.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), register.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }catch (Exception e) {
                                    RegisterAsRestaurantTwoFragmentProgressBar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<RegisterAsRestaurant> call, Throwable t) {
                                RegisterAsRestaurantTwoFragmentProgressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }
    }
}
