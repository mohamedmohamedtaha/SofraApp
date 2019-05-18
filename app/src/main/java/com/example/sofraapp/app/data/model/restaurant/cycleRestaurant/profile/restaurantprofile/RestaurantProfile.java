
package com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.profile.restaurantprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantProfile {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataRestaurantProfile data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataRestaurantProfile getData() {
        return data;
    }

    public void setData(DataRestaurantProfile data) {
        this.data = data;
    }

}
