package com.example.hotel_reservation.models;

import java.util.ArrayList;

public class Hotel {

    private String name;
    private ArrayList<Room> rooms;

    private String img;

    public Hotel(){

    }

    public Hotel(String name, String img) {
        this.name = name;
        this.img = img;
        rooms = new ArrayList<Room>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
