
package com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationsRestaurant {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataNotificationsRestaurant data;

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

    public DataNotificationsRestaurant getData() {
        return data;
    }

    public void setData(DataNotificationsRestaurant data) {
        this.data = data;
    }

}
