package com.example.sofraapp.app.helper;

import android.os.Parcel;
import android.os.Parcelable;

public class SaveData implements Parcelable {
    private int id;
    private String api_token;
    private String name;
    private String phone;
    private String email;
    private int region_id;
    private String cityId;
    private String address;
    private int save_state;
    private String hayId;
    private String password;
    private String retryPassword;

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

    private int id_position;

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

    public SaveData(int id, String api_token, String name, String phone, String email, int region_id, String cityId, String address, int save_state) {
        this.id = id;
        this.api_token = api_token;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.region_id = region_id;
        this.cityId = cityId;
        this.address = address;
        this.save_state = save_state;
    }

    protected SaveData(Parcel in) {
        id = in.readInt();
        api_token = in.readString();
        name = in.readString();
        phone = in.readString();
        email = in.readString();
        region_id = in.readInt();
        cityId = in.readString();
        address = in.readString();
        save_state = in.readInt();
        id_position = in.readInt();
        hayId = in.readString();
        password = in.readString();
        retryPassword = in.readString();

    }

    public static final Creator<SaveData> CREATOR = new Creator<SaveData>() {
        @Override
        public SaveData createFromParcel(Parcel in) {
            return new SaveData(in);
        }

        @Override
        public SaveData[] newArray(int size) {
            return new SaveData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(api_token);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeInt(region_id);
        dest.writeString(cityId);
        dest.writeString(address);
        dest.writeInt(save_state);
        dest.writeInt(id_position);
        dest.writeString(hayId);
        dest.writeString(password);
        dest.writeString(retryPassword);
    }
}
