package com.example.sofraapp.app.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.adapter.AdapterRestaurantItems;
import com.example.sofraapp.app.data.model.general.restaurantitems.Data2RestaurantItems;
import com.example.sofraapp.app.data.model.general.restaurantitems.RestaurantItems;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.SaveData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofraapp.app.data.rest.RetrofitClient.getRetrofit;
import static com.example.sofraapp.app.helper.HelperMethod.GET_DATA;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFoodFragment extends Fragment {


    @BindView(R.id.ListFoodFragment_LV)
    ListView ListFoodFragmentLV;
    @BindView(R.id.ListFoodFragment_TV_Empty_View)
    TextView ListFoodFragmentTVEmptyView;
    @BindView(R.id.ListFoodFragment_Loading_Indicator)
    ProgressBar ListFoodFragmentLoadingIndicator;
    Unbinder unbinder;
    private APIServices apiServices;
    private AdapterRestaurantItems adapterRestaurantItems;
    SaveData saveData;

    public ListFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_food, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        ListFoodFragmentLV.setEmptyView(ListFoodFragmentTVEmptyView);
         apiServices = getRetrofit().create(APIServices.class);
         apiServices.getRestaurantItems(saveData.getId_position(),1).enqueue(new Callback<RestaurantItems>() {
             @Override
             public void onResponse(Call<RestaurantItems> call, Response<RestaurantItems> response) {
                 List<Data2RestaurantItems> data2RestaurantItemsArrayList = response.body().getData().getData();
                 RestaurantItems restaurantItems = response.body();
                 ListFoodFragmentLoadingIndicator.setVisibility(View.VISIBLE);
                 if (restaurantItems.getStatus()== 1){
                      adapterRestaurantItems = new AdapterRestaurantItems(getActivity(), data2RestaurantItemsArrayList, new AdapterRestaurantItems.showDetial() {
                          @Override
                          public void itemShowDetail(Data2RestaurantItems position) {
                              int detailRestaurant = position.getId();
                              DetailesOrderFragment detailesOrderFragment = new DetailesOrderFragment();
                              saveData = new SaveData(saveData.getSave_state(),detailRestaurant);
                              HelperMethod.replece(detailesOrderFragment,getActivity().getSupportFragmentManager(),R.id.Cycle_Home_contener,null,null,saveData);
                          }
                      });
                     ListFoodFragmentLV.setAdapter(adapterRestaurantItems);
                     ListFoodFragmentLoadingIndicator.setVisibility(View.GONE);

                 }else {
                     Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                     ListFoodFragmentLoadingIndicator.setVisibility(View.GONE);

                 }
             }

             @Override
             public void onFailure(Call<RestaurantItems> call, Throwable t) {
                 Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                 ListFoodFragmentLoadingIndicator.setVisibility(View.GONE);

             }
         });
         ListFoodFragmentLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 DetailesOrderFragment detailesOrderFragment = new DetailesOrderFragment();
                 HelperMethod.replece(detailesOrderFragment,getActivity().getSupportFragmentManager(),R.id.Cycle_Home_contener,null,null,saveData);
                 Toast.makeText(getActivity(), " position:" +id, Toast.LENGTH_SHORT).show();
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
