package com.example.hotel_reservation.DatabaseAccess;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hotel_reservation.R;
import com.example.hotel_reservation.models.Hotel;
import com.example.hotel_reservation.models.Room;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;

public class HotelDA {


    private Hotel hotel;

    private static final ArrayList<Hotel> hotels = new  ArrayList<Hotel>();

    public HotelDA() {
//        hotels.add( new Hotel("Millenium Hotel", R.drawable.millenium_hotel));
//        hotels.add(new Hotel("Caramel Hotel",R.drawable.caramel_hotel));
        //get hotels from database
    }

    public void setupAllHotels(Context C){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Hotel")
                // .whereEqualTo("hotel", hotel)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                                DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(i);
                                Hotel hotel = document.toObject(Hotel.class);
                                Log.d("SUCC", hotel.getName() + " ADDED");
                                hotels.add(hotel);
                            }
                        } else {
                            // Toast.makeText(C, "COULD NOT CONNECT PROPERLY", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(C, "No Rooms available", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public ArrayList<Hotel> getHotels(){

        return hotels;
    }










    public HotelDA(Hotel hotel) {
        this.hotel = hotel;
    }
}
