
package com.example.sofraapp.app.data.model.restaurant.fooditem.newitem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewItem {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataNewItem data;

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

    public DataNewItem getData() {
        return data;
    }

    public void setData(DataNewItem data) {
        this.data = data;
    }

}
