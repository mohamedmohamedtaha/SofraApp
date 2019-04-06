
package com.example.sofraapp.app.data.model.cycleRestaurant.register;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataRegister {

    @SerializedName("email")
    @Expose
    private List<String> email = null;
    @SerializedName("photo")
    @Expose
    private List<String> photo = null;

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getPhoto() {
        return photo;
    }

    public void setPhoto(List<String> photo) {
        this.photo = photo;
    }

}
