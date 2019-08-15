package com.example.sofraapp.app.ui.fragment.mainCycle;

import android.os.Bundle;
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
import com.example.sofraapp.app.data.model.general.contact.Contact;
import com.example.sofraapp.app.data.model.general.settings.Settings;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.RememberMy;
import com.google.android.material.textfield.TextInputEditText;

import androidx.fragment.app.Fragment;
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
    @BindView(R.id.ContactUsFragment_TIET_Name)
    TextInputEditText ContactUsFragmentTIETName;
    @BindView(R.id.ContactUsFragment_TIET_Email)
    TextInputEditText ContactUsFragmentTIETEmail;
    @BindView(R.id.ContactUsFragment_TIET_Phone)
    TextInputEditText ContactUsFragmentTIETPhone;
    @BindView(R.id.ContactUsFragment_TIET_Content)
    TextInputEditText ContactUsFragmentTIETContent;
    @BindView(R.id.ContactUsFragment_RB_Estalam)
    RadioButton ContactUsFragmentRBEstalam;
    @BindView(R.id.ContactUsFragment_RB_Suggest)
    RadioButton ContactUsFragmentRBSuggest;
    @BindView(R.id.ContactUsFragment_RB_Problem)
    RadioButton ContactUsFragmentRBProblem;
    @BindView(R.id.ContactUsFragment_RG_Content)
    RadioGroup ContactUsFragmentRGContent;
    @BindView(R.id.ContactUsFragment_BT_Send_Message)
    Button ContactUsFragmentBTSendMessage;
    @BindView(R.id.ContactUsFragment_IM_Instgram)
    ImageView ContactUsFragmentIMInstgram;
    @BindView(R.id.ContactUsFragment_IM_Twitter)
    ImageView ContactUsFragmentIMTwitter;
    @BindView(R.id.ContactUsFragment_IM_Face_Book)
    ImageView ContactUsFragmentIMFaceBook;
    @BindView(R.id.ContactUsFragment_Progress_Bar)
    ProgressBar ContactUsFragmentProgressBar;
    Unbinder unbinder;
    private APIServices apiServices;
    private int idClick;
    private String saveStateRB = null;
    RememberMy remeberMy;
    String name = null;
    String phone = null;
    String email = null;
    String content = null;
    String fasebook;
    String intgram;
    String twittet;
    RememberMy rememberMy;

    public ContactUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        remeberMy = new RememberMy(getActivity());
        if (remeberMy.getAPIKey() != null) {
            ContactUsFragmentTIETEmail.setText(remeberMy.getEmailUser());
            ContactUsFragmentTIETName.setText(remeberMy.getNameUser());
            ContactUsFragmentTIETPhone.setText(remeberMy.getPhoneUser());
            ContactUsFragmentBTSendMessage.setEnabled(true);

        }else {
            Toast.makeText(getActivity(), getString(R.string.login_please), Toast.LENGTH_SHORT).show();
            ContactUsFragmentBTSendMessage.setEnabled(false);

        }
        rememberMy = new RememberMy(getActivity());
        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getSettings(rememberMy.getEmailUser(),rememberMy.getPassword()).enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                Settings settings = response.body();
                if (settings.getStatus() == 1) {
                    fasebook = settings.getData().getFacebook();
                    twittet = settings.getData().getTwitter();
                    intgram = settings.getData().getInstagram();
                } else {
                    Toast.makeText(getActivity(), settings.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        ContactUsFragmentRGContent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                idClick = ContactUsFragmentRGContent.getCheckedRadioButtonId();
                switch (idClick) {
                    case R.id.ContactUsFragment_RB_Suggest:
                        saveStateRB = getString(R.string.suggestion);
                        break;
                    case R.id.ContactUsFragment_RB_Problem:
                        saveStateRB = getString(R.string.complaint);
                        break;
                    case R.id.ContactUsFragment_RB_Estalam:
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

    @OnClick({R.id.ContactUsFragment_BT_Send_Message, R.id.ContactUsFragment_IM_Instgram, R.id.ContactUsFragment_IM_Twitter, R.id.ContactUsFragment_IM_Face_Book})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ContactUsFragment_BT_Send_Message:
                name = ContactUsFragmentTIETName.getText().toString().trim();
                phone = ContactUsFragmentTIETPhone.getText().toString().trim();
                email = ContactUsFragmentTIETEmail.getText().toString().trim();
                content = ContactUsFragmentTIETContent.getText().toString().trim();
                if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || content.isEmpty() || saveStateRB == null) {
                    Toast.makeText(getActivity(), getString(R.string.filed_request), Toast.LENGTH_SHORT).show();
                    return;
                }
                ContactUsFragmentProgressBar.setVisibility(View.VISIBLE);
                apiServices = getRetrofit().create(APIServices.class);
                apiServices.getContact(name, email, phone, saveStateRB, content).enqueue(new Callback<Contact>() {
                    @Override
                    public void onResponse(Call<Contact> call, Response<Contact> response) {
                        try {
                            if (response.body().getStatus() == 1) {
                                ContactUsFragmentProgressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            } else {
                                ContactUsFragmentProgressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            ContactUsFragmentProgressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Contact> call, Throwable t) {
                        ContactUsFragmentProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.ContactUsFragment_IM_Instgram:
                HelperMethod.openWebSite(getActivity(),intgram);
                break;
            case R.id.ContactUsFragment_IM_Twitter:
                HelperMethod.openWebSite(getActivity(),twittet);
                break;
            case R.id.ContactUsFragment_IM_Face_Book:
                HelperMethod.openWebSite(getActivity(),fasebook);
                break;
        }
    }
}