package com.example.hotel_reservation.models;

import java.util.ArrayList;

public class Hotel {

    private String name;
    private ArrayList<Room> rooms;

    private String img;

    private String desc;
    private double rating;
    public Hotel(){

    }

    public Hotel(String name, String img,String desc,double rating) {
        this.name = name;
        this.rating=rating;
        this.img = img;
        this.desc = desc;
        rooms = new ArrayList<Room>();
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
