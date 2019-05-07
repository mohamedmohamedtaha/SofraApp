package com.example.sofraapp.app.ui.fragment;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputEditText;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.cycleRestaurant.addproduct.AddProduct;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.SaveData;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

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
import static com.example.sofraapp.app.helper.OpenCamira.CAMIRA;
import static com.example.sofraapp.app.helper.OpenCamira.GALARY;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment {


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
    SaveData saveData;
    APIServices apiServices;
    Bitmap bitmap;
    MultipartBody.Part fileToUpload;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private int counter = 1;

    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

    private String getRelPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALARY && resultCode == RESULT_OK) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    String filePath = getRelPathFromURIPath(contentURI, getActivity());
                    File file = new File(filePath);
                    RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                    fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
                    AddProductFragmentIMAddPhoto.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMIRA) {
            if (data != null) {
                Bundle extrs = data.getExtras();
                bitmap = (Bitmap) extrs.get("data");
                AddProductFragmentIMAddPhoto.setImageBitmap(bitmap);
            }
        }
    }
*/
    private void showPictureDialog() {
        AlertDialog.Builder pitureDialog = new AlertDialog.Builder(getActivity());
        pitureDialog.setTitle(getString(R.string.select_action));
        String[] pictureDialogItems = {getString(R.string.select_galary), getString(R.string.capture_camira)};
        pitureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        choosePhotoFromGalary();
                        break;
                    case 1:
                        takePhotoFromCamira();
                        break;
                }
            }
        });
        pitureDialog.show();
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
        /*        try {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALARY);
                    } else if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMIRA);

                    } else {
                        showPictureDialog();}
                } catch (Exception e) {
                    e.printStackTrace();
               }*/

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
                //   if (saveData.getApi_token() == null) {
                //     Toast.makeText(getActivity(), getString(R.string.must_login), Toast.LENGTH_LONG).show();
                //  return;
                //}
                if (AddProductFragmentIMAddPhoto.getDrawable() == null) {
                    Toast.makeText(getActivity(), getString(R.string.error_image), Toast.LENGTH_LONG).show();
                    return;
                }
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
                RequestBody getApi_tokenBody = HelperMethod.convertToRequestBody(saveData.getApi_token());
                if (ImagesFiles.size() <= 0) {
                    Toast.makeText(getActivity(), getString(R.string.select_photo), Toast.LENGTH_SHORT).show();
                    return;
                }
                MultipartBody.Part multiPartImage = HelperMethod.convertFileToMultipart(ImagesFiles.get(0).getPath(), "image");
                apiServices = getRetrofit().create(APIServices.class);
                apiServices.getAddProduct(descriptionBody, priceBody, preparing_timeBody, nameBody, multiPartImage, getApi_tokenBody).
                        enqueue(new Callback<AddProduct>() {
                            @Override
                            public void onResponse(Call<AddProduct> call, Response<AddProduct> response) {
                                AddProduct addProduct = response.body();
                                if (addProduct.getStatus() == 1) {
                                    Toast.makeText(getActivity(), addProduct.getMsg(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), addProduct.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<AddProduct> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }
    }
}