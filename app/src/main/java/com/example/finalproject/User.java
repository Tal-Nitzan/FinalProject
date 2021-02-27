package com.example.finalproject;

import android.net.Uri;

public class User {
    private String gmailAddress;
    private int numOfActiveDeliveries;
    private int numOfCancelledDeliveries;
    private int numOfCompletedDeliveries;

    User() {}

    public String getGmailAddress() {
        return gmailAddress;
    }

    public User setGmailAddress(String gmailAddress) {
        this.gmailAddress = gmailAddress;
        return this;
    }

    public int getNumOfActiveDeliveries() {
        return numOfActiveDeliveries;
    }

    public User setNumOfActiveDeliveries(int numOfActiveDeliveries) {
        this.numOfActiveDeliveries = numOfActiveDeliveries;
        return this;
    }

    public int getNumOfCancelledDeliveries() {
        return numOfCancelledDeliveries;
    }

    public User setNumOfCancelledDeliveries(int numOfCancelledDeliveries) {
        this.numOfCancelledDeliveries = numOfCancelledDeliveries;
        return this;
    }

    public int getNumOfCompletedDeliveries() {
        return numOfCompletedDeliveries;
    }

    public User setNumOfCompletedDeliveries(int numOfCompletedDeliveries) {
        this.numOfCompletedDeliveries = numOfCompletedDeliveries;
        return this;
    }
}
