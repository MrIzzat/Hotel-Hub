package com.example.hotel_reservation.models;

import java.util.ArrayList;

public class Hotel {

    private String name;
    private ArrayList<Room> rooms;

    private String img;

    private String desc;
    private double rating;

    private String address;
    private String city;
    private boolean hasRooms;
    public Hotel(){

    }

    public Hotel(String name, String img, String desc, double rating, String address, String city,boolean hasRooms) {
        this.name = name;
        this.img = img;
        this.desc = desc;
        this.rating = rating;
        this.address = address;
        this.city = city;
        this.hasRooms=hasRooms;
        rooms = new ArrayList<Room>();
    }

    public boolean isHasRooms() {
        return hasRooms;
    }

    public void setHasRooms(boolean hasRooms) {
        this.hasRooms = hasRooms;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
