package com.example.sofraapp.app.ui.fragment.restaurant.foodItem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.restaurant.fooditem.myitems.Data2MyItems;
import com.example.sofraapp.app.data.model.restaurant.fooditem.newitem.NewItem;
import com.example.sofraapp.app.data.model.restaurant.fooditem.updateitem.UpdateItem;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.RememberMy;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.lang.reflect.Type;
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
import static com.example.sofraapp.app.helper.OpenCamira.CAMIRA;
import static com.example.sofraapp.app.helper.OpenCamira.GALARY;

public class AddAndEditPRoduct extends DialogFragment {
    private static final String EXTRA_ID_PRODUCT = "id";
    @BindView(R.id.AddProductFragment_Progress_Bar)
    ProgressBar AddProductFragmentProgressBar;
    @BindView(R.id.AddProductFragment_Tiet_Name_Product)
    TextInputEditText AddProductFragmentTietNameProduct;
    @BindView(R.id.AddProductFragment_Tiet_Describe)
    TextInputEditText AddProductFragmentTietDescribe;
    @BindView(R.id.AddProductFragment_Tiet_Price_Product)
    TextInputEditText AddProductFragmentTietPriceProduct;
    @BindView(R.id.AddProductFragment_Tiet_Priod_order)
    TextInputEditText AddProductFragmentTietPriodOrder;
    @BindView(R.id.AddProductFragment_IM_Add_Photo)
    ImageView AddProductFragmentIMAddPhoto;
    @BindView(R.id.AddProductFragment_Bt_Add)
    Button AddProductFragmentBtAdd;
    Unbinder unbinder;
    @BindView(R.id.AddProductFragment_TVTitleCategory)
    TextView AddProductFragmentTVTitleCategory;
    private Data2MyItems data2MyProducts;
    private Bundle bundle;

    AlertDialog.Builder builder;
    AlertDialog dialog;
    APIServices apiServices;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private int counter = 1;
    RememberMy rememberMy;

    public static AddAndEditPRoduct newInstance(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_ID_PRODUCT, id);
        AddAndEditPRoduct addAndEditPRoduct = new AddAndEditPRoduct();
        addAndEditPRoduct.setArguments(bundle);
        return addAndEditPRoduct;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_product, null);
        unbinder = ButterKnife.bind(this, view);
        rememberMy = new RememberMy(getActivity());
        apiServices = getRetrofit().create(APIServices.class);


         bundle = getArguments();
        if (bundle != null) {
            data2MyProducts = new Gson().fromJson(bundle.getString("product"), Data2MyItems.class);

            //for Edit Product_________________________________________
            AddProductFragmentTVTitleCategory.setText(getString(R.string.edit_product));
            AddProductFragmentBtAdd.setText(getString(R.string.edit_product));
            AddProductFragmentTietDescribe.setText(data2MyProducts.getDescription());
            AddProductFragmentTietNameProduct.setText(data2MyProducts.getName());
            AddProductFragmentTietPriceProduct.setText(data2MyProducts.getPrice());
            AddProductFragmentTietPriodOrder.setText(data2MyProducts.getPreparingTime());
            Glide.with(getActivity()).load(data2MyProducts.getPhotoUrl()).placeholder(R.drawable.no_image)
                    .into(AddProductFragmentIMAddPhoto);

        }
        builder = new AlertDialog.Builder(getActivity());
        builder.setView(view).setIcon(R.mipmap.logo);
        dialog = builder.create();
        dialog.show();

        return dialog;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GALARY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhotoFromGalary();
            } else {
                Toast.makeText(getActivity(), getString(R.string.no_permission_access), Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == CAMIRA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhotoFromCamira();
            } else {
                Toast.makeText(getActivity(), getString(R.string.no_permission_access), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void choosePhotoFromGalary() {
        Intent galaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galaryIntent, GALARY);
    }

    public void takePhotoFromCamira() {
        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takeIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takeIntent, CAMIRA);

        }
    }

    @OnClick({R.id.AddProductFragment_IM_Add_Photo, R.id.AddProductFragment_Bt_Add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.AddProductFragment_IM_Add_Photo:
                Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        // TODO accept the result.
                        ImagesFiles.clear();
                        ImagesFiles.addAll(result);
                        Glide.with(getContext()).load(ImagesFiles.get(0).getPath()).into(AddProductFragmentIMAddPhoto);

                    }
                };
                HelperMethod.openAlbum(counter, getActivity(), ImagesFiles, action);
                break;
            case R.id.AddProductFragment_Bt_Add:
                String name = AddProductFragmentTietNameProduct.getText().toString().trim();
                String description = AddProductFragmentTietDescribe.getText().toString().trim();
                String price = AddProductFragmentTietPriceProduct.getText().toString().trim();
                String preparing_time = AddProductFragmentTietPriodOrder.getText().toString().trim();
                if (name.isEmpty() || description.isEmpty() || price.isEmpty() || preparing_time.isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.filed_request), Toast.LENGTH_LONG).show();
                    return;
                }
                RequestBody nameBody = HelperMethod.convertToRequestBody(name);
                RequestBody descriptionBody = HelperMethod.convertToRequestBody(description);
                RequestBody priceBody = HelperMethod.convertToRequestBody(price);
                RequestBody preparing_timeBody = HelperMethod.convertToRequestBody(preparing_time);
                RequestBody getApi_tokenBody = HelperMethod.convertToRequestBody(rememberMy.getAPIKey());

                if (ImagesFiles.size() <= 0) {
                    Toast.makeText(getActivity(), getString(R.string.select_photo), Toast.LENGTH_SHORT).show();
                    return;
                }
                MultipartBody.Part multiPartImage = HelperMethod.convertFileToMultipart(ImagesFiles.get(0).getPath(), "photo");
                AddProductFragmentProgressBar.setVisibility(View.VISIBLE);
                if (bundle != null){
                    RequestBody getIdItemBody = HelperMethod.convertToRequestBody(String.valueOf(data2MyProducts.getId()));
                    apiServices.updateItem(descriptionBody, priceBody, preparing_timeBody, nameBody, multiPartImage,getIdItemBody,
                            getApi_tokenBody).
                            enqueue(new Callback<UpdateItem>() {
                                @Override
                                public void onResponse(Call<UpdateItem> call, Response<UpdateItem> response) {
                                    UpdateItem updateItem = response.body();
                                    try {
                                        if (updateItem.getStatus() == 1) {
                                            AddProductFragmentProgressBar.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), updateItem.getMsg(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        } else {
                                            AddProductFragmentProgressBar.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), updateItem.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        AddProductFragmentProgressBar.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<UpdateItem> call, Throwable t) {
                                    AddProductFragmentProgressBar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {
                    apiServices.getAddProduct(descriptionBody, priceBody, preparing_timeBody, nameBody, multiPartImage, getApi_tokenBody).
                            enqueue(new Callback<NewItem>() {
                                @Override
                                public void onResponse(Call<NewItem> call, Response<NewItem> response) {
                                    NewItem newItem = response.body();
                                    try {
                                        if (newItem.getStatus() == 1) {
                                            AddProductFragmentProgressBar.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), newItem.getMsg(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        } else {
                                            AddProductFragmentProgressBar.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), newItem.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        AddProductFragmentProgressBar.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<NewItem> call, Throwable t) {
                                    AddProductFragmentProgressBar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                break;
        }
    }
}


















