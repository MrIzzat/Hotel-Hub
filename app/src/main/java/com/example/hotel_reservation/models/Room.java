package com.example.hotel_reservation.models;

public class Room {

    private String name;
    private String desc;
    private String img;
    private double price;

    private String hotel;

    private boolean reserved;

    private String reservedBy;

    public Room(){

    }

    public Room(String name, String desc, String img, double price, String hotel, boolean reserved,String reservedBy) {
        this.name = name;
        this.desc = desc;
        this.img = img;
        this.price = price;
        this.hotel = hotel;
        this.reserved = reserved;
        this.reservedBy=reservedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public String getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(String reservedBy) {
        this.reservedBy = reservedBy;
    }
}
