package com.example.sofraapp.app.ui.fragment.splashAndUserCycle;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import com.google.android.material.textfield.TextInputEditText;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.cycleClient.profile.EditProfile;
import com.example.sofraapp.app.data.model.cycleClient.profile.getuserprofile.GetUserProfile;
import com.example.sofraapp.app.data.model.general.cities.Cities;
import com.example.sofraapp.app.data.model.general.cities.Data2Cities;
import com.example.sofraapp.app.data.model.general.regions.Data2Regions;
import com.example.sofraapp.app.data.model.general.regions.Regions;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.SaveData;

import java.io.IOException;
import java.util.ArrayList;
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
public class EditProfileUserFragment extends Fragment {
    @BindView(R.id.EditProfileUserFragment_BT_Add_Photo)
    Button EditProfileUserFragmentBTAddPhoto;
    @BindView(R.id.EditProfileUserFragment_IM_Add_Photo)
    ImageView EditProfileUserFragmentIMAddPhoto;
    @BindView(R.id.EditProfileUserFragment_Tiet_Name)
    TextInputEditText EditProfileUserFragmentTietName;
    @BindView(R.id.EditProfileUserFragment_Tiet_Email)
    TextInputEditText EditProfileUserFragmentTietEmail;
    @BindView(R.id.EditProfileUserFragment_Tiet_Phone)
    TextInputEditText EditProfileUserFragmentTietPhone;
    @BindView(R.id.EditProfileUserFragment_SP_City)
    Spinner EditProfileUserFragmentSPCity;
    @BindView(R.id.EditProfileUserFragment_SP_Hay)
    Spinner EditProfileUserFragmentSPHay;
    @BindView(R.id.EditProfileUserFragment_Tiet_Describe_Home)
    TextInputEditText EditProfileUserFragmentTietDescribeHome;
    @BindView(R.id.EditProfileUserFragment_Tiet_Password)
    TextInputEditText EditProfileUserFragmentTietPassword;
    @BindView(R.id.EditProfileUserFragment_Tiet_Retry_Password)
    TextInputEditText EditProfileUserFragmentTietRetryPassword;
    @BindView(R.id.EditProfileUserFragment_Bt_Edit)
    Button EditProfileUserFragmentBtEdit;
    @BindView(R.id.EditProfileUserFragment_Progress_Bar)
    ProgressBar EditProfileUserFragmentProgressBar;
    Unbinder unbinder;
    // private static final int PICK_FROM_GALLERY = 2;
    private static final int GALARY = 0;
    private static final int CAMIRA = 1;
    SaveData saveData;
    private APIServices apiServices;
    Bitmap bitmap;
    String getResult;
    int IDPosition;
    ArrayList<String> strings = new ArrayList<>();
    final ArrayList<Integer> IdsCity = new ArrayList<>();
    final ArrayList<Integer> IdsHay = new ArrayList<>();
    Integer positionHay;
    boolean check_network;

    public EditProfileUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        apiServices = getRetrofit().create(APIServices.class);
        check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
            return view;
        }
        EditProfileUserFragmentProgressBar.setVisibility(View.GONE);
        apiServices.getUserProfile(saveData.getApi_token()).enqueue(new Callback<GetUserProfile>() {
            @Override
            public void onResponse(Call<GetUserProfile> call, Response<GetUserProfile> response) {
                GetUserProfile getUserProfile = response.body();
                try {
                    if (getUserProfile.getStatus() == 1) {
                        EditProfileUserFragmentProgressBar.setVisibility(View.GONE);
                        EditProfileUserFragmentTietDescribeHome.setText(getUserProfile.getData().getClient().getAddress());
                        EditProfileUserFragmentTietEmail.setText(getUserProfile.getData().getClient().getEmail());
                        EditProfileUserFragmentTietName.setText(getUserProfile.getData().getClient().getName());
                        EditProfileUserFragmentTietPhone.setText(getUserProfile.getData().getClient().getPhone());
                        EditProfileUserFragmentSPCity.setSelection(getUserProfile.getData().getClient().getRegion().getId());
                        EditProfileUserFragmentSPHay.setSelection(getUserProfile.getData().getClient().getRegion().getCity().getId());
                    } else {
                        EditProfileUserFragmentProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), getUserProfile.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    EditProfileUserFragmentProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserProfile> call, Throwable t) {
                EditProfileUserFragmentProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        final Call<Cities> citiesCall = apiServices.getCities();
        citiesCall.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                Cities cities = response.body();
                if (cities.getStatus() == 1) {
                    try {
                        strings.add(getString(R.string.select_city));
                        IdsCity.add(0);
                        List<Data2Cities> citiesList = cities.getData().getData();
                        for (int i = 0; i < citiesList.size(); i++) {
                            getResult = citiesList.get(i).getName();
                            strings.add(getResult);
                            IDPosition = citiesList.get(i).getId();
                            IdsCity.add(IDPosition);
                        }
                        HelperMethod.showGovernorates(strings, getActivity(), EditProfileUserFragmentSPCity);
                        EditProfileUserFragmentSPCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    getHay(IdsCity.get(position));
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getActivity(), cities.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void getHay(int getIdCity) {
        apiServices = getRetrofit().create(APIServices.class);
        final Call<Regions> regionsCall = apiServices.getRegions(getIdCity);
        regionsCall.enqueue(new Callback<Regions>() {
            @Override
            public void onResponse(Call<Regions> call, Response<Regions> response) {
                String getResult;
                ArrayList<String> strings = new ArrayList<>();
                Regions regions = response.body();
                if (regions.getStatus() == 1) {
                    try {
                        strings.add(getString(R.string.select_hay));
                        IdsHay.add(0);
                        List<Data2Regions> data2RegionsList = regions.getData().getData();
                        for (int i = 0; i < data2RegionsList.size(); i++) {
                            getResult = data2RegionsList.get(i).getName();
                            strings.add(getResult);
                            positionHay = data2RegionsList.get(i).getId();
                            IdsHay.add(positionHay);
                        }
                        HelperMethod.showGovernorates(strings, getActivity(), EditProfileUserFragmentSPHay);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), regions.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Regions> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.EditProfileUserFragment_BT_Add_Photo, R.id.EditProfileUserFragment_Bt_Edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.EditProfileUserFragment_BT_Add_Photo:
                try {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALARY);
                    } else if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMIRA);
                    } else {
                        showPictureDialog();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.EditProfileUserFragment_Bt_Edit:
                if (check_network == false) {
                    return;
                }
                if (EditProfileUserFragmentIMAddPhoto.getDrawable() == null) {
                    Toast.makeText(getActivity(), getString(R.string.error_image), Toast.LENGTH_LONG).show();
                    return;
                }
                String name = EditProfileUserFragmentTietName.getText().toString().trim();
                String email = EditProfileUserFragmentTietEmail.getText().toString().trim();
                String phone = EditProfileUserFragmentTietPhone.getText().toString().trim();
                String password = EditProfileUserFragmentTietPassword.getText().toString().trim();
                String retryPassword = EditProfileUserFragmentTietRetryPassword.getText().toString().trim();
                String describeHome = EditProfileUserFragmentTietDescribeHome.getText().toString().trim();
                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || retryPassword.isEmpty()
                        || describeHome.isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.filed_request), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (IdsHay.isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.select_elhay), Toast.LENGTH_SHORT).show();
                    return;
                }
                int hayId = IdsHay.get(EditProfileUserFragmentSPHay.getSelectedItemPosition());
                apiServices = getRetrofit().create(APIServices.class);
                apiServices.getProfileAndEdit(saveData.getApi_token(), name, phone, email, password,
                        retryPassword, describeHome, hayId)
                        .enqueue(new Callback<EditProfile>() {
                            @Override
                            public void onResponse(Call<EditProfile> call, Response<EditProfile> response) {
                                EditProfile profile = response.body();
                                if (profile.getStatus() == 1) {
                                    Toast.makeText(getActivity(), profile.getMsg(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), profile.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<EditProfile> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                break;
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALARY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);

                    EditProfileUserFragmentIMAddPhoto.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMIRA) {
            if (data != null) {
                Bundle extrs = data.getExtras();
                bitmap = (Bitmap) extrs.get("data");
                EditProfileUserFragmentIMAddPhoto.setImageBitmap(bitmap);
            }
        }
    }

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

}