
package com.example.sofraapp.app.data.model.general.citynotpaginated;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityNotPaginated {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<DataCityNotPaginated> data = null;

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

    public List<DataCityNotPaginated> getData() {
        return data;
    }

    public void setData(List<DataCityNotPaginated> data) {
        this.data = data;
    }

}
