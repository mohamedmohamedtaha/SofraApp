
package com.example.sofraapp.app.data.model.cycleClient.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataEditProfile {

    @SerializedName("user")
    @Expose
    private UserEditProfile user;

    public UserEditProfile getUser() {
        return user;
    }

    public void setUser(UserEditProfile user) {
        this.user = user;
    }

}
