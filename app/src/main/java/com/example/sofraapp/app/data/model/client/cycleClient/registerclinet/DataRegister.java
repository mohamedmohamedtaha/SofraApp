
package com.example.sofraapp.app.data.model.client.cycleClient.registerclinet;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataRegister {

    @SerializedName("email")
    @Expose
    private List<String> email = null;

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

}
