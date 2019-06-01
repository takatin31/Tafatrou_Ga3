package com.example.light.ramdanapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Jam3iya {

    String name;
    String email;
    String phone;
    LatLng location;
    int number;
    ArrayList<ProductModel> needsList;


    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public ArrayList<ProductModel> getNeedsList() {
        return needsList;
    }

    public void setNeedsList(ArrayList<ProductModel> needsList) {
        this.needsList = needsList;
    }

    public Jam3iya(String name, String email, String phone, int number) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.number = number;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
