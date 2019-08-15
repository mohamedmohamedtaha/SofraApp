package com.example.sofraapp.app.data.room;

import android.app.Application;
import android.os.AsyncTask;

import com.example.sofraapp.app.data.model.general.restaurantitems.Data2RestaurantItems;

import java.util.List;

import androidx.lifecycle.LiveData;

public class OrderRepository {
    private OrdersDao ordersDao;
  //  private LiveData<List<Data2RestaurantItems>> allOrders;
    private List<Data2RestaurantItems> allOrders;

    OrderRepository(Application application){
        OrderRoomDatabase database = OrderRoomDatabase.getDatabase(application);
        ordersDao = database.ordersDao();
        allOrders = ordersDao.getAllFoods();
    }
   /* LiveData<List<Data2RestaurantItems>>getAllOrders(){
        return allOrders;
    }*/

    List<Data2RestaurantItems>getAllOrders(){
        return allOrders;
    }
    public void insert(Data2RestaurantItems food){
        new insertAsyncTack(ordersDao).execute(food);
    }
    public void update(Data2RestaurantItems food){
        new updateAsyncTack(ordersDao).execute(food);
    }

    private static class insertAsyncTack extends AsyncTask<Data2RestaurantItems,Void,Void>{
        private OrdersDao mAsyncTaskordersDao;
        insertAsyncTack(OrdersDao ordersDao){
            mAsyncTaskordersDao = ordersDao;
        }

        @Override
        protected Void doInBackground(Data2RestaurantItems... data2RestaurantItems) {
            mAsyncTaskordersDao.insert(data2RestaurantItems[0]);
            return null;
        }
    }
    private static class updateAsyncTack extends AsyncTask<Data2RestaurantItems,Void,Void>{
        private OrdersDao mAsyncTaskordersDao;
        updateAsyncTack(OrdersDao ordersDao){
            mAsyncTaskordersDao = ordersDao;
        }

        @Override
        protected Void doInBackground(Data2RestaurantItems... data2RestaurantItems) {
            mAsyncTaskordersDao.update(data2RestaurantItems[0]);
            return null;
        }
    }
}
