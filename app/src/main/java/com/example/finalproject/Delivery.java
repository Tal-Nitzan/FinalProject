package com.example.finalproject;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;


enum STATE {PENDING,COMPLETED, CANCELLED}

public class Delivery {

    String id;
    String receiverName;
    String address;
    String phoneNumber;
    float weight;
    Date deliveryDate;
    String deliveryDateString;
    STATE state;
    double latitude;
    double longitude;


    public Delivery() {
    }

    public STATE getState() {
        return state;
    }

    public Delivery setState(STATE state) {
        this.state = state;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public Delivery setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public String getDeliveryDateString() {
        return deliveryDateString;
    }

    public Delivery setDeliveryDateString(String deliveryDateString) {
        this.deliveryDateString = deliveryDateString;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Delivery setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public Delivery setReceiverName(String receiverName) {
        this.receiverName = receiverName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Delivery setAddress(String address) {
        this.address = address;
        return this;
    }

    public float getWeight() {
        return weight;
    }

    public Delivery setWeight(float weight) {
        this.weight = weight;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public Delivery setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Delivery setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }
}
