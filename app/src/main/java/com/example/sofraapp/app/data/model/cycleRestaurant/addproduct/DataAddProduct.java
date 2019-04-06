
package com.example.sofraapp.app.data.model.cycleRestaurant.addproduct;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataAddProduct {

    @SerializedName("photo")
    @Expose
    private List<String> photo = null;

    public List<String> getPhoto() {
        return photo;
    }

    public void setPhoto(List<String> photo) {
        this.photo = photo;
    }

}
