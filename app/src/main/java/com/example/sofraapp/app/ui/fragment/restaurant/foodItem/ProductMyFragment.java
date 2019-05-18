package com.example.sofraapp.app.ui.fragment.restaurant.foodItem;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.adapter.AdapterMyProducts;

import com.example.sofraapp.app.data.model.restaurant.fooditem.myitems.Data2MyItems;
import com.example.sofraapp.app.data.model.restaurant.fooditem.myitems.MyItems;
import com.example.sofraapp.app.data.rest.APIServices;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.SaveData;
import com.example.sofraapp.app.ui.fragment.restaurant.foodItem.AddProductFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofraapp.app.data.rest.RetrofitClient.getRetrofit;
import static com.example.sofraapp.app.helper.HelperMethod.GET_DATA;
import static com.example.sofraapp.app.ui.activity.MainActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductMyFragment extends Fragment {

    @BindView(R.id.ProductMyFragment_Recycler_View)
    RecyclerView ProductMyFragmentRecyclerView;
    @BindView(R.id.ProductMyFragment_Empty_Image)
    ImageView ProductMyFragmentEmptyImage;
    @BindView(R.id.ProductMyFragment_TV_Empty_View)
    TextView ProductMyFragmentTVEmptyView;
    @BindView(R.id.ProductMyFragment_RL)
    RelativeLayout ProductMyFragmentRL;
    @BindView(R.id.ProductMyFragment_Loading_Indicator)
    ProgressBar ProductMyFragmentLoadingIndicator;
    Unbinder unbinder;
    AdapterMyProducts adapterMyProducts;
    ArrayList<Data2MyItems> myProductArrayList = new ArrayList<>();
    APIServices apiServices;
    @BindView(R.id.ProductMyFragment_BT_Add_New_Product)
    Button ProductMyFragmentBTAddNewProduct;

    public ProductMyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        myProductArrayList.clear();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        ProductMyFragmentRecyclerView.setLayoutManager(linearLayoutManager);

        adapterMyProducts = new AdapterMyProducts(getActivity(), myProductArrayList);
        ProductMyFragmentRecyclerView.setAdapter(adapterMyProducts);

        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getMyProducts("EuqQtEiKiG4OfshU49UltxUnvySicD3T1eW4BBjdjIlMqyGJPlYauzTOH0lv", 1).enqueue(new Callback<MyItems>() {
            @Override
            public void onResponse(Call<MyItems> call, Response<MyItems> response) {
                ProductMyFragmentLoadingIndicator.setVisibility(View.VISIBLE);
                MyItems myProduct = response.body();
                if (myProduct.getStatus() == 1) {
                    ProductMyFragmentRecyclerView.setVisibility(View.VISIBLE);
                    ProductMyFragmentLoadingIndicator.setVisibility(View.GONE);
                    ProductMyFragmentRL.setVisibility(View.GONE);
                    myProductArrayList.addAll(myProduct.getData().getData());
                    adapterMyProducts.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), myProduct.getMsg(), Toast.LENGTH_SHORT).show();
                    getProperties();
                }
            }

            @Override
            public void onFailure(Call<MyItems> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                getProperties();

            }
        });


        return view;
    }

    public void getProperties() {
        ProductMyFragmentRecyclerView.setVisibility(View.GONE);
        ProductMyFragmentLoadingIndicator.setVisibility(View.GONE);
        ProductMyFragmentRL.setVisibility(View.VISIBLE);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ProductMyFragment_BT_Add_New_Product)
    public void onViewClicked() {
        AddProductFragment addProductFragment = new AddProductFragment();
        HelperMethod.replece(addProductFragment,getActivity().getSupportFragmentManager(),R.id.Cycle_Home_contener,toolbar,getString(R.string.product_my));
    }
}
