package com.example.sofraapp.app.ui.fragment.mainCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.data.model.general.settings.Settings;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofraapp.app.data.rest.RetrofitClient.getRetrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutAppFragment extends Fragment {


    @BindView(R.id.TV_Show_Title)
    TextView TVShowTitle;
    Unbinder unbinder;
    @BindView(R.id.about_app_progress)
    ProgressBar aboutAppProgress;
    private APIServices apiServices;

    public AboutAppFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_app, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = getRetrofit().create(APIServices.class);
        aboutAppProgress.setVisibility(View.VISIBLE);

        apiServices.getSettings().enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                Settings settings = response.body();
                if (settings.getStatus() == 1) {
                    TVShowTitle.setText(settings.getData().getAboutApp());
                    aboutAppProgress.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getActivity(), settings.getMsg(), Toast.LENGTH_SHORT).show();
                    aboutAppProgress.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                aboutAppProgress.setVisibility(View.GONE);


            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
