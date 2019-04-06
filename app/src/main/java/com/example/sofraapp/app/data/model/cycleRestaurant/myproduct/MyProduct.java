
package com.example.sofraapp.app.data.model.cycleRestaurant.myproduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyProduct {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataMyProduct data;

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

    public DataMyProduct getData() {
        return data;
    }

    public void setData(DataMyProduct data) {
        this.data = data;
    }

}
