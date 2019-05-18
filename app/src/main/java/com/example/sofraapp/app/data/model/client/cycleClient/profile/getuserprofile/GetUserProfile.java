
package com.example.sofraapp.app.data.model.client.cycleClient.profile.getuserprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserProfile {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataGetUserProfile data;

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

    public DataGetUserProfile getData() {
        return data;
    }

    public void setData(DataGetUserProfile data) {
        this.data = data;
    }

}
