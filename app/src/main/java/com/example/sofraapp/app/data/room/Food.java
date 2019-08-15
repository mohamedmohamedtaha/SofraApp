package com.example.sofraapp.app.data.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_table")
public class Food {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "id_restaurant")
    private int id_restaurant;
    @ColumnInfo(name = "items")
    private int items;
    @ColumnInfo(name = "path")
    private String path;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "price")
    private String price;
    @ColumnInfo(name = "quantity")
    private String quantity;
    @ColumnInfo(name = "total_price")
    private String total_price;
    @ColumnInfo(name = "delivery_cost")
    private String delivery_cost;
    public String getDelivery_cost() {
        return delivery_cost;
    }

    public void setDelivery_cost(String delivery_cost) {
        this.delivery_cost = delivery_cost;
    }
    public Food(String path,String title,String price,String total_price,String quantity,int id_restaurant,int items,String delivery_cost){
        this.path = path;
        this.title = title;
        this.price = price;
        this.total_price =total_price;
        this.quantity = quantity;
        this.id_restaurant = id_restaurant;
        this.items = items;
        this.delivery_cost = delivery_cost;
    }
    public int getId_restaurant() {
        return id_restaurant;
    }

    public void setId_restaurant(int id_restaurant) {
        this.id_restaurant = id_restaurant;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public String getFood(){
        return this.title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    public void setPath(@NonNull String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}
