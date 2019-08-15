package com.example.sofraapp.app.data.room;

import android.app.Application;

import com.example.sofraapp.app.data.model.general.restaurantitems.Data2RestaurantItems;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class OrdersViewModel extends AndroidViewModel {
    private OrderRepository orderRepository;
   // private LiveData<List<Data2RestaurantItems>> allOrders;
    private List<Data2RestaurantItems> allOrders;

    public OrdersViewModel( Application application) {
        super(application);
        orderRepository = new OrderRepository(application);
        allOrders = orderRepository.getAllOrders();
    }
/*    public LiveData<List<Data2RestaurantItems>>getAllOrders(){
        return allOrders;
    }*/

    public List<Data2RestaurantItems>getAllOrders(){
        return allOrders;
    }
    public void insert(Data2RestaurantItems food){
        orderRepository.insert(food);
    }
    public void update(Data2RestaurantItems food){
        orderRepository.update(food);
    }
}
