package com.example.sofraapp.app.data.room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.sofraapp.app.data.model.general.restaurantitems.Data2RestaurantItems;

import java.util.List;
@Dao
public interface RoomDao {

    @Insert
    void insertItemToCar(Data2RestaurantItems... productData);

    @Update
    void updateItemToCar(Data2RestaurantItems ... productData);

    @Delete
    void deleteItemToCar(Data2RestaurantItems ... productData);

    @Query("Delete from cart_table")
    void deleteAllItemToCar();

    @Query("Select * from  cart_table")
    List<Data2RestaurantItems> getAllItem();
}
