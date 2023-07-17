package com.example.hotel_reservation.DatabaseAccess;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hotel_reservation.Login;
import com.example.hotel_reservation.MainMenu;
import com.example.hotel_reservation.R;
import com.example.hotel_reservation.models.Room;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RoomDA {

    private static final ArrayList<Room> rooms = new ArrayList<Room>();

    public RoomDA() {


        // get rooms from database
        // rooms.add( new Room("Best Room ever","asdasdas", R.drawable.millenium_hotel,122,"Caramel Hotel",false));
        // rooms.add(new Room("Caramel Hotel","asdasdsa",R.drawable.caramel_hotel,130,"Mellinium Hotel",false));


    }

    public void setupAllRooms(Context C){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Rooms")
                // .whereEqualTo("hotel", hotel)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                                DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(i);
                                Room room = document.toObject(Room.class);
                                Log.d("SUCC", room.getName() + " ADDED");
                                rooms.add(room);
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
    public ArrayList<Room> getRooms() {
        return rooms;
    }
}
