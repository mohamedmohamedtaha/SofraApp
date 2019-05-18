
package com.example.sofraapp.app.data.model.client.cycleClient.profile.editprofile;

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
