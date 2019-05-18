
package com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.cyclelogin.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataLogin {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("user")
    @Expose
    private UserLogin user;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public UserLogin getUser() {
        return user;
    }

    public void setUser(UserLogin user) {
        this.user = user;
    }

}
