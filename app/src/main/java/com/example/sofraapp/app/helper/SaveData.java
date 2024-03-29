package com.example.sofraapp.app.helper;

import android.os.Parcel;
import android.os.Parcelable;
@org.parceler.Parcel
public class SaveData {
    public int id;
    public String api_token;
    public String name;
    public String phone;
    public String email;
    public int region_id;
    public String cityId;
    public String address;
    public int save_state;
    public String hayId;
    public String password;
    public String retryPassword;
    public String delivery_cost;
    public int id_position;

    public String getDelivery_cost() {
        return delivery_cost;
    }

    public void setDelivery_cost(String delivery_cost) {
        this.delivery_cost = delivery_cost;
    }


    public String getHayId() {
        return hayId;
    }

    public void setHayId(String hayId) {
        this.hayId = hayId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRetryPassword() {
        return retryPassword;
    }

    public void setRetryPassword(String retryPassword) {
        this.retryPassword = retryPassword;
    }

    public int getId_position() {
        return id_position;
    }

    public void setId_position(int id_position) {
        this.id_position = id_position;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSave_state() {
        return save_state;
    }

    public void setSave_state(int save_state) {
        this.save_state = save_state;
    }

    public SaveData(int save_state, int id_position) {
        this.save_state = save_state;
        this.id_position = id_position;
    }
    public SaveData() {

    }
    public SaveData( int id_position) {
        this.id_position = id_position;
    }

    public SaveData(int id, String api_token, String name, String phone, String email, int region_id, String cityId, String address) {
        this.id = id;
        this.api_token = api_token;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.region_id = region_id;
        this.cityId = cityId;
        this.address = address;
    }

   }
