package com.example.sofraapp.app.helper;

import android.os.Parcel;
import android.os.Parcelable;

public class Model  implements Parcelable {
    private String photo;
    private String price;
    private String title;
    private String Total_price;
    private String describe;
    private int quantity;
    private String preparing_time;

    public String getDelivery_cost() {
        return delivery_cost;
    }

    public void setDelivery_cost(String delivery_cost) {
        this.delivery_cost = delivery_cost;
    }

    private String delivery_cost;

    public int getId_restuarant() {
        return id_restuarant;
    }

    public void setId_restuarant(int id_restuarant) {
        this.id_restuarant = id_restuarant;
    }

    private int id_restuarant;

    private int id_item;

    public Model(String photo, String price, String title, String Total_price, String describe,int quantity,String preparing_time, int id) {
        this.photo = photo;
        this.price = price;
        this.title = title;
        this.Total_price = Total_price;
        this.describe = describe;
        this.quantity = quantity;
        this.id_item = id_item;
        this.preparing_time= preparing_time;
    }

    public Model(String photo, String price, String title, String describe,int quantity,String preparing_time, int id_item,int id_restuarant,String delivery_cost) {
        this.photo = photo;
        this.price = price;
        this.title = title;
        this.describe = describe;
        this.quantity = quantity;
        this.id_item = id_item;
        this.id_restuarant = id_restuarant;
        this.preparing_time= preparing_time;
        this.delivery_cost= delivery_cost;

    }

    public String getPreparing_time() {
        return preparing_time;
    }

    public void setPreparing_time(String preparing_time) {
        preparing_time = preparing_time;
    }

    public int getIdItem() {
        return id_item;
    }

    public void setdItem(int id_item) {
        this.id_item = id_item;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    protected Model(Parcel in) {
        photo = in.readString();
        price = in.readString();
        title = in.readString();
        Total_price = in.readString();
        preparing_time= in.readString();
        describe = in.readString();
        id_item = in.readInt();
        quantity = in.readInt();
        id_restuarant = in.readInt();
        delivery_cost = in.readString();


    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotal_price() {
        return Total_price;
    }

    public void setTotal_price(String period_delivery) {
        this.Total_price = period_delivery;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(photo);
        dest.writeString(price);
        dest.writeString(title);
        dest.writeString(Total_price);
        dest.writeString(describe);
        dest.writeString(preparing_time);
        dest.writeInt(id_item);
        dest.writeInt(id_restuarant);
        dest.writeInt(quantity);
        dest.writeString(delivery_cost);

    }
}
