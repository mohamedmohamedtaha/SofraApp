
package com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.profile.restaurantprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataRestaurantProfile {

    @SerializedName("user")
    @Expose
    private UserRestaurantProfile user;

    public UserRestaurantProfile getUser() {
        return user;
    }

    public void setUser(UserRestaurantProfile user) {
        this.user = user;
    }

}
