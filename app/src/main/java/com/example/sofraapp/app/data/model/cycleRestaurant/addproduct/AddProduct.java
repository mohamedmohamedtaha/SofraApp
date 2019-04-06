
package com.example.sofraapp.app.data.model.cycleRestaurant.addproduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddProduct {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataAddProduct data;

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

    public DataAddProduct getData() {
        return data;
    }

    public void setData(DataAddProduct data) {
        this.data = data;
    }

}
