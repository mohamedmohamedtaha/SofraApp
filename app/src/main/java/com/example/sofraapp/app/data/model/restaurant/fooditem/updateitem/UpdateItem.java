
package com.example.sofraapp.app.data.model.restaurant.fooditem.updateitem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateItem {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataUpdateItem data;

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

    public DataUpdateItem getData() {
        return data;
    }

    public void setData(DataUpdateItem data) {
        this.data = data;
    }

}
