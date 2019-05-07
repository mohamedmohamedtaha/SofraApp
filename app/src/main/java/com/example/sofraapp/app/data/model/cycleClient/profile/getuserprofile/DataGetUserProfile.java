
package com.example.sofraapp.app.data.model.cycleClient.profile.getuserprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataGetUserProfile {

    @SerializedName("client")
    @Expose
    private ClientGetUserProfile client;

    public ClientGetUserProfile getClient() {
        return client;
    }

    public void setClient(ClientGetUserProfile client) {
        this.client = client;
    }

}
