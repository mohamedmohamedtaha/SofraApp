package com.example.sofraapp.app.data.room;

import android.content.Context;
import android.os.AsyncTask;

import com.example.sofraapp.app.data.model.general.restaurantitems.Data2RestaurantItems;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Data2RestaurantItems.class}, version = 4, exportSchema = false)
@TypeConverters(DataTypeConverter.class)
public abstract class OrderRoomDatabase extends RoomDatabase {
    public abstract OrdersDao ordersDao();

    private static volatile OrderRoomDatabase INSTANCE;

     static OrderRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (OrderRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            OrderRoomDatabase.class, "cart_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRommDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRommDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsyn(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsyn extends AsyncTask<Void, Void, Void> {
        private final OrdersDao ordersDao;

        PopulateDbAsyn(OrderRoomDatabase db) {
            ordersDao = db.ordersDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
        //    ordersDao.deleteAll();
           // Food food = new Food("hello", "mohamed", "20", "40", "2");
           // ordersDao.insert(food);

            return null;
        }
    }
}
