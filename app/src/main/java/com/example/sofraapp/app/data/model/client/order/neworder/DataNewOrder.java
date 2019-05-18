
package com.example.sofraapp.app.data.model.client.order.neworder;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataNewOrder {

    @SerializedName("items")
    @Expose
    private List<String> items = null;

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

}
