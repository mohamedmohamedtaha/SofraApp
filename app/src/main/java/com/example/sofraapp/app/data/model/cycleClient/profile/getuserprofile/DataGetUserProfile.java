
package com.example.sofraapp.app.data.model.cycleClient.profile.getuserprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataGetUserProfile {

    @SerializedName("user")
    @Expose
    private UserGetUserProfile user;

    public UserGetUserProfile getUser() {
        return user;
    }

    public void setUser(UserGetUserProfile user) {
        this.user = user;
    }

}
