package com.guerra.hexagonal.application.core.domain;

public class Address {

    private String steet;
    private String city;
    private String state;

    public Address() {
    }

    public Address(String steet, String city, String state) {
        this.steet = steet;
        this.city = city;
        this.state = state;
    }

    public String getSteet() {
        return steet;
    }

    public void setSteet(String steet) {
        this.steet = steet;
    }

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
}
