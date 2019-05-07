package com.example.sofraapp.app.helper;

import android.os.Parcel;
import android.os.Parcelable;

public class Model  implements Parcelable {
    private String photo;
    private String price;
    private String title;
    private String period_delivery;
    private String describe;
    private int id;

    public Model(String photo, String price, String title, String period_delivery, String describe, int id) {
        this.photo = photo;
        this.price = price;
        this.title = title;
        this.period_delivery = period_delivery;
        this.describe = describe;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    protected Model(Parcel in) {
        photo = in.readString();
        price = in.readString();
        title = in.readString();
        period_delivery = in.readString();
        describe = in.readString();
        id = in.readInt();

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

    public String getPeriod_delivery() {
        return period_delivery;
    }

    public void setPeriod_delivery(String period_delivery) {
        this.period_delivery = period_delivery;
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
        dest.writeString(period_delivery);
        dest.writeString(describe);
        dest.writeInt(id);
    }
}
