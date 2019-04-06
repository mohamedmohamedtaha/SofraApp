package com.example.sofraapp.app.ui.fragment.mainCycle;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.data.model.general.contact.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofraapp.app.data.rest.RetrofitClient.getRetrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {


    @BindView(R.id.ET_Name)
    TextInputEditText ETName;
    @BindView(R.id.ET_Email)
    TextInputEditText ETEmail;
    @BindView(R.id.ET_Phone)
    TextInputEditText ETPhone;
    @BindView(R.id.ET_Content)
    TextInputEditText ETContent;
    @BindView(R.id.BT_Send_Message)
    Button BTSendMessage;
    @BindView(R.id.IM_Instgram)
    ImageView IMInstgram;
    @BindView(R.id.IM_Twitter)
    ImageView IMTwitter;
    @BindView(R.id.IM_Face_Book)
    ImageView IMFaceBook;
    Unbinder unbinder;
    @BindView(R.id.RB_Problem)
    RadioButton RBProblem;
    @BindView(R.id.RB_Suggest)
    RadioButton RBSuggest;
    @BindView(R.id.RB_Estalam)
    RadioButton RBEstalam;
    @BindView(R.id.RG_Content)
    RadioGroup RGContent;
    @BindView(R.id.Content_Us_Progress)
    ProgressBar ContentUsProgress;
    private APIServices apiServices;
    private int idClick;
    private String saveStateRB = null;

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        unbinder = ButterKnife.bind(this, view);

        RGContent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                idClick = RGContent.getCheckedRadioButtonId();
                switch (idClick) {
                    case R.id.RB_Suggest:
                        saveStateRB = getString(R.string.suggestion);
                        break;
                    case R.id.RB_Problem:
                        saveStateRB = getString(R.string.complaint);

                        break;
                    case R.id.RB_Estalam:
                        saveStateRB = getString(R.string.inquiry);

                        break;
                    default:

                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.RG_Content, R.id.BT_Send_Message, R.id.IM_Instgram, R.id.IM_Twitter, R.id.IM_Face_Book})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.RG_Content:
                break;
            case R.id.BT_Send_Message:
                String name = ETName.getText().toString().trim();
                String phone = ETPhone.getText().toString().trim();
                String email = ETEmail.getText().toString().trim();
                String content = ETContent.getText().toString().trim();
                if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || content.isEmpty() || saveStateRB == null) {
                    Toast.makeText(getActivity(), getString(R.string.filed_request), Toast.LENGTH_SHORT).show();
                    return;
                }
                ContentUsProgress.setVisibility(View.VISIBLE);
                apiServices = getRetrofit().create(APIServices.class);

                apiServices.getContact(name, email, phone, saveStateRB, content).enqueue(new Callback<Contact>() {
                    @Override
                    public void onResponse(Call<Contact> call, Response<Contact> response) {
                        if (response.body().getStatus() == 1) {
                            ContentUsProgress.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            ContentUsProgress.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Contact> call, Throwable t) {
                        ContentUsProgress.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                break;
            case R.id.IM_Instgram:
                Toast.makeText(getActivity(), saveStateRB, Toast.LENGTH_SHORT).show();

                break;
            case R.id.IM_Twitter:
                break;
            case R.id.IM_Face_Book:
                break;
        }
    }
}
