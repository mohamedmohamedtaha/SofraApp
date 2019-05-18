
package com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.cyclelogin.registerasrestaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataRegisterAsRestaurant {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("data")
    @Expose
    private Data2RegisterAsRestaurant data;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public Data2RegisterAsRestaurant getData() {
        return data;
    }

    public void setData(Data2RegisterAsRestaurant data) {
        this.data = data;
    }

}
