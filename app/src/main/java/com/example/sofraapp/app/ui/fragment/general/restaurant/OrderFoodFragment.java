package com.example.sofraapp.app.ui.fragment.general.restaurant;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.adapter.AdapterOrderFood;
import com.example.sofraapp.app.data.model.general.citynotpaginated.CityNotPaginated;
import com.example.sofraapp.app.data.model.general.citynotpaginated.DataCityNotPaginated;
import com.example.sofraapp.app.data.model.general.restaurants.Data2Restaurants;
import com.example.sofraapp.app.data.model.general.restaurants.Restaurants;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.OnEndless;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofraapp.app.data.rest.RetrofitClient.getRetrofit;
import static com.example.sofraapp.app.ui.activity.MainActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFoodFragment extends Fragment {
    @BindView(R.id.OrderFoodFragment_Recycler_View)
    RecyclerView OrderFoodFragmentRecyclerView;
    @BindView(R.id.IV_Empty_Image)
    ImageView IVEmptyImage;
    @BindView(R.id.RL_Empty_View_OrderFoodFragment)
    RelativeLayout RLEmptyViewOrderFoodFragment;
    @BindView(R.id.PB_Loading_Indicator)
    ProgressBar PBLoadingIndicator;
    Unbinder unbinder;
    @BindView(R.id.OrderFoodFragment_Iv_Search)
    ImageView OrderFoodFragmentIvSearch;
    @BindView(R.id.OrderFoodFragment_Et_Search)
    EditText OrderFoodFragmentEtSearch;
    @BindView(R.id.OrderFoodFragment_Sp_City)
    Spinner OrderFoodFragmentSpCity;
    @BindView(R.id.OrderFoodFragment_Ll_Container)
    LinearLayout OrderFoodFragmentLlContainer;
    private APIServices apiServices;
    private ArrayList<Data2Restaurants> restaurantsArrayList = new ArrayList<>();
    private AdapterOrderFood adapterOrderFood;
    public static final String DETAILS_RESTUARANT = "details_restaurant";
    ArrayList<String> cityName = new ArrayList();
    ArrayList<Integer> cityId = new ArrayList();
    int maxPage = 0;
    private OnEndless onEndless;


    public OrderFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_food, container, false);
        unbinder = ButterKnife.bind(this, view);
        //restaurantsArrayList.clear();
        apiServices = getRetrofit().create(APIServices.class);
        //PBLoadingIndicator.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        OrderFoodFragmentRecyclerView.setHasFixedSize(true);
        OrderFoodFragmentRecyclerView.setLayoutManager(linearLayoutManager);
        onEndless = new OnEndless(linearLayoutManager,1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage){
                    getResturant(apiServices.getRestaurants(current_page));

                }

            }
        };
        OrderFoodFragmentRecyclerView.addOnScrollListener(onEndless);
        adapterOrderFood = new AdapterOrderFood(getActivity(), restaurantsArrayList, new AdapterOrderFood.showDetial() {
            @Override
            public void itemShowDetail(Data2Restaurants position) {
                DetailRestaurantFragment detailRestaurantFragment = new DetailRestaurantFragment();
                Bundle bundle = new Bundle();
                bundle.putString("dev",new Gson().toJson(position));
                detailRestaurantFragment.setArguments(bundle);
                HelperMethod.replece(detailRestaurantFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.list_food));
            }
        });
        OrderFoodFragmentRecyclerView.setAdapter(adapterOrderFood);


        getResturant(apiServices.getRestaurants(1));

        getCategories();

        return view;
    }
    private void getResturant(Call<Restaurants>restaurantsCall){
        PBLoadingIndicator.setVisibility(View.VISIBLE);

        restaurantsArrayList.clear();
        restaurantsCall.enqueue(new Callback<Restaurants>() {
            @Override
            public void onResponse(Call<Restaurants> call, Response<Restaurants> response) {
                try {
                    Restaurants restaurants = response.body();
                    maxPage = restaurants.getData().getLastPage();
                    if (restaurants.getStatus() == 1) {
                        if (restaurants.getData().getTotal() > 0){
                            OrderFoodFragmentRecyclerView.setVisibility(View.VISIBLE);
                            PBLoadingIndicator.setVisibility(View.GONE);
                            RLEmptyViewOrderFoodFragment.setVisibility(View.GONE);
                            restaurantsArrayList.addAll(restaurants.getData().getData());
                            adapterOrderFood.notifyDataSetChanged();
                        }else {
                            OrderFoodFragmentRecyclerView.setVisibility(View.GONE);
                            PBLoadingIndicator.setVisibility(View.GONE);
                            RLEmptyViewOrderFoodFragment.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(getActivity(), restaurants.getMsg(), Toast.LENGTH_SHORT).show();
                        OrderFoodFragmentRecyclerView.setVisibility(View.GONE);
                        PBLoadingIndicator.setVisibility(View.GONE);
                        RLEmptyViewOrderFoodFragment.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    OrderFoodFragmentRecyclerView.setVisibility(View.GONE);
                    PBLoadingIndicator.setVisibility(View.GONE);
                    RLEmptyViewOrderFoodFragment.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Restaurants> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                OrderFoodFragmentRecyclerView.setVisibility(View.GONE);
                PBLoadingIndicator.setVisibility(View.GONE);
                RLEmptyViewOrderFoodFragment.setVisibility(View.VISIBLE);
            }
        });

    }

    private void getCategories() {
        apiServices.getCityNotPaginated().enqueue(new Callback<CityNotPaginated>() {
            @Override
            public void onResponse(Call<CityNotPaginated> call, Response<CityNotPaginated> response) {
                try {
                    cityName = new ArrayList<>();
                    cityId = new ArrayList<>();
                    cityName.add(getString(R.string.select_city));
                    cityId.add(0);
                    List<DataCityNotPaginated> allCategories = response.body().getData();
                    for (int i = 0; i < allCategories.size(); i++) {
                        cityName.add(allCategories.get(i).getName());
                        cityId.add(allCategories.get(i).getId());
                    }
                    HelperMethod.showGovernorates(cityName, getActivity(), OrderFoodFragmentSpCity);
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<CityNotPaginated> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.OrderFoodFragment_Iv_Search)
    public void onViewClicked() {
        HelperMethod.hideKeyboard(getActivity(),getView());
        String search = OrderFoodFragmentEtSearch.getText().toString().trim();
        if (search.isEmpty() && OrderFoodFragmentSpCity.getSelectedItemPosition() == 0){
            Toast.makeText(getActivity(), getString(R.string.shold_select), Toast.LENGTH_SHORT).show();
        }else if (search.isEmpty() && OrderFoodFragmentSpCity.getSelectedItemPosition() != 0){
            getResturant(apiServices.getRestaurants(search,cityId.get(OrderFoodFragmentSpCity.getSelectedItemPosition()),1));
        }else if (!search.isEmpty() && OrderFoodFragmentSpCity.getSelectedItemPosition() ==0){
            getResturant(apiServices.getRestaurants(search,1));
        }else {
            getResturant(apiServices.getRestaurants(search,cityId.get(OrderFoodFragmentSpCity.getSelectedItemPosition()),1));

        }
    }
}