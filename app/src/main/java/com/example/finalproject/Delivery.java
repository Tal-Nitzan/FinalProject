package com.example.finalproject;

import android.location.Location;

public class Delivery {

    String receiverName;
    String address;
    String phoneNumber;
    float weight;

    public Delivery() {}

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
