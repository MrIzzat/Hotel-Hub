package com.example.hotel_reservation.DatabaseAccess;

import com.example.hotel_reservation.models.User;

public class UserDA {

    private User currentUser;

    public UserDA(User currentUser) {
        this.currentUser = currentUser;
    }

    public void changeUserFirstName(String newInput){
        currentUser.setFirstName(newInput);

    }
    public void changeUserLastName(String newInput){
        currentUser.setLastName(newInput);
    }

    public void changeUserPassword(String newInput){

    }

    public void changeUserTelephone(String newInput){
        currentUser.setTelephone(newInput);
    }
    public void changeUserInfo(String FirstName, String LastName, String Email, String PhoneNumber){
        currentUser = new User(FirstName,LastName,Email,PhoneNumber);
    }
}
