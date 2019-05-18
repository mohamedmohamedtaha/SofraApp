
package com.example.sofraapp.app.data.model.restaurant.fooditem.myitems;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyItems {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataMyItems data;

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

    public DataMyItems getData() {
        return data;
    }

    public void setData(DataMyItems data) {
        this.data = data;
    }

}
