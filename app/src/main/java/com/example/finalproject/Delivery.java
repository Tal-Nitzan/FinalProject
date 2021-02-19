package com.example.finalproject;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Delivery {

    String id;
    String receiverName;
    String address;
    String phoneNumber;
    float weight;
    Date deliveryDate;
    String deliveryDateString;

    public Delivery() {
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
}
