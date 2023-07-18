package com.example.hotel_reservation.DatabaseAccess;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hotel_reservation.models.Reservation;
import com.example.hotel_reservation.models.Room;
import com.example.hotel_reservation.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReservationDA {

   public static ArrayList<Reservation>  reservations = new ArrayList<Reservation>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ReservationDA(){

    }

    public void setupReservations(Context C, User u){

        reservations = new ArrayList<Reservation>();


        db.collection("reservations")
                .whereEqualTo("user", u.getEmail())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {

                            for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                                DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(i);
                                Reservation res = document.toObject(Reservation.class);
                                reservations.add(res);

                            }
                        } else {
                            // Toast.makeText(C, "COULD NOT CONNECT PROPERLY", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(C, "No Rooms available", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
