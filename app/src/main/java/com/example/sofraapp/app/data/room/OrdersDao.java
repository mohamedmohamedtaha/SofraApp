package com.example.sofraapp.app.data.room;

import com.example.sofraapp.app.data.model.general.restaurantitems.Data2RestaurantItems;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface OrdersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Data2RestaurantItems ... data2RestaurantItems);

    @Update
    void updateItemToCar(Data2RestaurantItems... data2RestaurantItems);
    @Delete
    void deleteItemToCar(Data2RestaurantItems... data2RestaurantItems);

    @Query("DELETE FROM cart_table")
    void deleteAll();

    @Query("SELECT * from cart_table ORDER BY id ASC")
   List<Data2RestaurantItems>getAllFoods();

  //  @Query("SELECT * from cart_table ORDER BY id ASC")
//    LiveData<List<Data2RestaurantItems>>getAllFoodsText();


    @Update
    void update(Data2RestaurantItems data2RestaurantItems);

    }





















