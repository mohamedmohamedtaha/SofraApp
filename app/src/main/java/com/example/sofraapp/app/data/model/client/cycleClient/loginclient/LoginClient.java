
package com.example.sofraapp.app.data.model.client.cycleClient.loginclient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginClient {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataLoginClient data;

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

    public DataLoginClient getData() {
        return data;
    }

    public void setData(DataLoginClient data) {
        this.data = data;
    }

}
