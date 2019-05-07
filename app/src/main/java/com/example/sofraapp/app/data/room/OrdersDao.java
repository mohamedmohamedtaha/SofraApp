package com.example.sofraapp.app.data.room;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

public interface OrdersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert();
    @Query("SELECT * FROM orders_table ")
}
