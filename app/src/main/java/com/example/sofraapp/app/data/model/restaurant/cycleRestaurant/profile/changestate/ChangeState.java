
package com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.profile.changestate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeState {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataChangeState data;

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

    public DataChangeState getData() {
        return data;
    }

    public void setData(DataChangeState data) {
        this.data = data;
    }

}
