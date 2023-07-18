package com.example.hotel_reservation.DatabaseAccess;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hotel_reservation.MainMenu;
import com.example.hotel_reservation.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


public class UserDA {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private User currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public UserDA() {
    }


    public void updateUser(Context C,String newFirstName,String newLastName,String newPhoneNumber,String email){
        db.collection("users")
                .whereEqualTo("email", email)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            DocumentReference doc = queryDocumentSnapshots.getDocuments().get(0).getReference();

                            Map<String, Object> map = new HashMap<>();
                            map.put("first_name", newFirstName);
                            map.put("last_name", newLastName);
                            map.put("phoneNumber",newPhoneNumber);
                            doc.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    setupPrefs(C);
                                    Toast.makeText(C, "Successfully Changed Information", Toast.LENGTH_SHORT).show();
                                    MainMenu.LoggedUser = new User(newFirstName, newLastName,
                                            email, newPhoneNumber);

                                    Gson gson = new Gson();
                                    String user = gson.toJson(MainMenu.LoggedUser);
                                    editor.putString("USER", user);
                                    editor.commit();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(C, "Failed to Change Information", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(C, "Could not find user", Toast.LENGTH_SHORT).show();
                    }
                });


    }
    private void setupPrefs(Context C) {
        prefs = PreferenceManager.getDefaultSharedPreferences(C);
        editor = prefs.edit();
    }
    public void changePassword(Context C, String newPassword,String email){
        db.collection("users")
                .whereEqualTo("email", email)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            DocumentReference doc = queryDocumentSnapshots.getDocuments().get(0).getReference();

                            Map<String, Object> map = new HashMap<>();
                            map.put("password", newPassword);
                            doc.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(C, "Successfully Changed Password", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(C, "Failed to Change Password", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(C, "Could not find user", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
