package com.example.hotel_reservation.DatabaseAccess;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hotel_reservation.Login;
import com.example.hotel_reservation.MainMenu;
import com.example.hotel_reservation.R;
import com.example.hotel_reservation.models.Reservation;
import com.example.hotel_reservation.models.Room;
import com.example.hotel_reservation.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoomDA {

    private static ArrayList<Room> rooms = new ArrayList<Room>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public RoomDA() {


        // get rooms from database
        // rooms.add( new Room("Best Room ever","asdasdas", R.drawable.millenium_hotel,122,"Caramel Hotel",false));
        // rooms.add(new Room("Caramel Hotel","asdasdsa",R.drawable.caramel_hotel,130,"Mellinium Hotel",false));


    }

    public void setupAllRooms(Context C) {

        rooms = new ArrayList<Room>();

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

    public void reserveRoom(Context C, Room room, User u, Reservation r) {


        updateRoom(room, C, u);

        addReservation(room, u, C, r);


    }


    private void updateRoom(Room room, Context C, User u) {
        db.collection("Rooms")
                .whereEqualTo("name", room.getName())
                .whereEqualTo("hotel", room.getHotel())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        DocumentReference doc = queryDocumentSnapshots.getDocuments().get(0).getReference();

                        String email = u.getEmail();

                        Map<String, Object> map = new HashMap<>();
                        map.put("reserved", true);
                        map.put("reservedBy", email);

                        doc.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(C, "Successfully Reserved", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(C, "Failed to reserve", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(C, "Failed to reserve room try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addReservation(Room room, User u, Context C, Reservation r) {
        DocumentReference resRef = db.collection("reservations").document();

        resRef.set(r).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(C, "Added Reservation", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(C, "Failed to add reservation", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cancelReservation(Context C, Room room) {
        cancelRoomRes(C, room);
        removeReservation(C, room);

    }

    private void removeReservation(Context C, Room r) {
        db.collection("reservations")
                .whereEqualTo("roomName", r.getName())
                .whereEqualTo("hotel", r.getHotel())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            DocumentReference doc = queryDocumentSnapshots.getDocuments().get(0).getReference();
                            doc.delete();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(C, "Could not find room", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void cancelRoomRes(Context C, Room room) {

        db.collection("Rooms")
                .whereEqualTo("name", room.getName())
                .whereEqualTo("hotel", room.getHotel())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        DocumentReference doc = queryDocumentSnapshots.getDocuments().get(0).getReference();

                        Map<String, Object> map = new HashMap<>();
                        map.put("reserved", false);
                        map.put("reservedBy", "notreserved");

                        doc.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(C, "Successfully Cancelled Reservation", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(C, "Failed to Cancel Reservation", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(C, "Could not find Room", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}

