package com.example.sofraapp.app.data.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.sofraapp.app.data.model.general.restaurantitems.Data2RestaurantItems;

@Database(entities = {Data2RestaurantItems.class}, version = 1, exportSchema = false)
@TypeConverters({DataTypeConverter.class})
public abstract class RoomManger extends RoomDatabase {

    private static RoomManger roomManger;

    public abstract RoomDao roomDao();

    public static synchronized RoomManger getInstance(Context context) {
        if (roomManger == null) {
            roomManger = Room.databaseBuilder(context.getApplicationContext(), RoomManger.class,
                    "sofra_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return roomManger;
    }

}
