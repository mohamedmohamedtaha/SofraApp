
package com.example.sofraapp.app.data.model.client.order.neworder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewOrder {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataNewOrder data;

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

    public DataNewOrder getData() {
        return data;
    }

    public void setData(DataNewOrder data) {
        this.data = data;
    }

}
