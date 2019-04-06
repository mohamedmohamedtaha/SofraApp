
package com.example.sofraapp.app.data.model.cycleRestaurant.myoffers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOffers {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataMyOffers data;

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

    public DataMyOffers getData() {
        return data;
    }

    public void setData(DataMyOffers data) {
        this.data = data;
    }

}
