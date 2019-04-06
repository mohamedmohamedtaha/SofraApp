
package com.example.sofraapp.app.data.model.cycleClient.myordersasuser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOrdersAsUser {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataMyOrdersAsUser data;

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

    public DataMyOrdersAsUser getData() {
        return data;
    }

    public void setData(DataMyOrdersAsUser data) {
        this.data = data;
    }

}
