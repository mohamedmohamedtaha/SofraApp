
package com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.cyclelogin.registerasrestaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterAsRestaurant {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataRegisterAsRestaurant data;

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

    public DataRegisterAsRestaurant getData() {
        return data;
    }

    public void setData(DataRegisterAsRestaurant data) {
        this.data = data;
    }

}
