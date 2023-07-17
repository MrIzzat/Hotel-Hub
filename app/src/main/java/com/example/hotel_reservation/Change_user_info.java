package com.example.hotel_reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.hotel_reservation.DatabaseAccess.UserDA;
import com.example.hotel_reservation.MainMenu;

import com.example.hotel_reservation.models.User;
import com.google.gson.Gson;

public class Change_user_info extends AppCompatActivity {


    private EditText edtFirstName;
    private EditText edtLastName;
    private EditText edtEmail;
    private EditText edtPhoneNumber;
    private Button btnUpdate;

    private boolean flag = false;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private UserDA userda;

    public static final String FLAG = "FLAG";

    private Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);

        setupViews();

        User LoggedUser = MainMenu.LoggedUser;

        edtFirstName.setText(LoggedUser.getFirstName());
        edtLastName.setText(LoggedUser.getLastName());
        edtEmail.setText(LoggedUser.getEmail());
        edtPhoneNumber.setText(LoggedUser.getTelephone());



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newFirstName = edtFirstName.getText().toString();
                String newLastName = edtLastName.getText().toString();
                String newEmailName = edtEmail.getText().toString();
                String newPhoneNumber = edtPhoneNumber.getText().toString();

                userda.changeUserInfo(newFirstName,newLastName,newEmailName,newPhoneNumber);
            }
        });
    }


    private void setupViews() {

        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        btnUpdate = findViewById(R.id.btnUpdate);
    }
}