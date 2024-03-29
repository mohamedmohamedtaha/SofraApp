
package com.example.sofraapp.app.data.model.restaurant.offers.newoffer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewOffer {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private NewOfferData data;

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

    public NewOfferData getData() {
        return data;
    }

    public void setData(NewOfferData data) {
        this.data = data;
    }

}
