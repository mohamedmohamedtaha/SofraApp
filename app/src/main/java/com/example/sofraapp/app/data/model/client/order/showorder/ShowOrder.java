
package com.example.sofraapp.app.data.model.client.order.showorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowOrder {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataShowOrder data;

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

    public DataShowOrder getData() {
        return data;
    }

    public void setData(DataShowOrder data) {
        this.data = data;
    }

}
