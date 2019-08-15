
package com.example.sofraapp.app.data.model.client.order.neworder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataNewOrder {

    @SerializedName("order")
    @Expose
    private OrderNewOrder order;

    public OrderNewOrder getOrder() {
        return order;
    }

    public void setOrder(OrderNewOrder order) {
        this.order = order;
    }

}
