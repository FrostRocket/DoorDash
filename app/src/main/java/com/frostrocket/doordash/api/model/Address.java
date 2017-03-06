package com.frostrocket.doordash.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("street")
    @Expose
    private String street;

    @SerializedName("lat")
    @Expose
    private Double latitude;

    @SerializedName("lng")
    @Expose
    private Double longitude;

    @SerializedName("printable_address")
    @Expose
    private String printableAddress;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPrintableAddress() {
        return printableAddress;
    }

    public void setPrintableAddress(String printableAddress) {
        this.printableAddress = printableAddress;
    }
}