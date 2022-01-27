package com.eden.test.domain;


import com.eden.annotation.TransferLabel;

public class AddressB {
    @TransferLabel("location")
    private String locationB;

    public String getLocationB() {
        return locationB;
    }

    public void setLocationB(String locationB) {
        this.locationB = locationB;
    }

    @Override
    public String toString() {
        return "AddressB{" +
                "locationB='" + locationB + '\'' +
                '}';
    }
}
