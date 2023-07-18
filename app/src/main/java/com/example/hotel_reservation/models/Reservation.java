package com.example.hotel_reservation.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Reservation {

    private String user;
    private String firstName;
    private String lastName;
    private int numberOfPeople = 1;
//    private String startDate;
//    private String endDate;
    private String startDateStr;
    private String endDateStr;
    private String city;
    private String town;
    private String street;
    private String payAmount;
    private boolean payCash;
    private String cardNumber;
    private String holderName;
    private String expDate;
    private String cvv;

    private String roomName;

    private String hotel;

    public Reservation() {
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
//    public void setStartDate(Calendar startDate) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
//        this.startDate = dateFormat.format(startDate.getTime());
//    }
//    public void setEndDate(Calendar endDate) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
//        this.endDate = dateFormat.format(endDate.getTime());
//    }
    public String getStartDateStr() {
        return startDateStr;
    }

//    public void setStartDate(String startDate) {
//        this.startDate = startDate;
//    }
//
//    public void setEndDate(String endDate) {
//        this.endDate = endDate;
//    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setTown(String town) {
        this.town = town;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }
    public void setPayCash(boolean payCash) {
        this.payCash = payCash;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }
    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public int getNumberOfPeople() {
        return numberOfPeople;
    }
//    public String getStartDate() {
//        return startDate;
//    }
//    public String getEndDate() {
//        return endDate;
//    }
    public String getCity() {
        return city;
    }
    public String getTown() {
        return town;
    }
    public String getStreet() {
        return street;
    }
    public String getPayAmount() {
        return payAmount;
    }
    public boolean isPayCash() {
        return payCash;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public String getHolderName() {
        return holderName;
    }
    public String getExpDate() {
        return expDate;
    }
    public String getCvv() {
        return cvv;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Reservation(String user,String firstName, String lastName, int numberOfPeople, String startDateStr, String endDateStr, String city, String town, String street, String payAmount, boolean payCash, String cardNumber, String holderName, String expDate, String cvv, String roomName, String hotel) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberOfPeople = numberOfPeople;
//        this.startDate = startDate;
//        this.endDate = endDate;
        this.startDateStr = startDateStr;
        this.endDateStr = endDateStr;
        this.city = city;
        this.town = town;
        this.street = street;
        this.payAmount = payAmount;
        this.payCash = payCash;
        this.cardNumber = cardNumber;
        this.holderName = holderName;
        this.expDate = expDate;
        this.cvv = cvv;
        this.roomName = roomName;
        this.hotel = hotel;
    }
}
