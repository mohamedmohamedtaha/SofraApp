
package com.example.sofraapp.app.data.model.cycleClient.loginclient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataLoginClient {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("client")
    @Expose
    private ClientLoginClient client;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public ClientLoginClient getClient() {
        return client;
    }

    public void setClient(ClientLoginClient client) {
        this.client = client;
    }

}
