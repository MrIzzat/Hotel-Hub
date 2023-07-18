package com.example.hotel_reservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel_reservation.models.Reservation;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

public class Address extends AppCompatActivity {
    private Reservation reservation;

    private ProgressBar progressBar;
    private ImageView progress20, progress40, progress60, progress80;
    private ProgressControl progressControl;

    private Spinner citySpin;

    private EditText townTxt, streetTxt;

    private Button nextBtn, backBtn;

    private String[] cities = {"Nablus", "Ramallah", "Gaza", "Hebron", "Jenin", "Bethlehem", "Tulkarm", "Jerusalem"};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        String reservationJson = getIntent().getStringExtra("reservation");
        Gson gson = new Gson();
        reservation = gson.fromJson(reservationJson, Reservation.class);

        citySpinnerFill();
        connect();
        fill();

        progressControl = new ProgressControl(progressBar, progress20, progress40, progress60, progress80);
        progressControl.setProgress(40);


        nextBtn.setOnClickListener(v -> {
            String city = citySpin.getSelectedItem().toString();
            String town = townTxt.getText().toString().trim();
            String street = streetTxt.getText().toString().trim();

            // Perform validation checks
            if (town.isEmpty() || street.isEmpty()) {
                Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Set the properties of the Reservation object
            reservation.setCity(city);
            reservation.setTown(town);
            reservation.setStreet(street);

            // Convert the Reservation object to a JSON string using Gson
            Gson gson2 = new Gson();
            String reservationJson2 = gson2.toJson(reservation);

            // Pass the Reservation object as an extra to the next activity
            Intent intent = new Intent(Address.this, Payment.class);
            intent.putExtra("reservation", reservationJson2);
            startActivity(intent);
            finish();
        });


        backBtn.setOnClickListener(v -> {
            String city = citySpin.getSelectedItem().toString();
            String town = townTxt.getText().toString().trim();
            String street = streetTxt.getText().toString().trim();


            reservation.setCity(city);
            reservation.setTown(town);
            reservation.setStreet(street);

            // Convert the Reservation object to a JSON string using Gson
            Gson gson2 = new Gson();
            String reservationJson2 = gson2.toJson(reservation);

            // Pass the Reservation object as an extra to the next activity
            Intent intent = new Intent(Address.this, Personal.class);
            intent.putExtra("reservation", reservationJson2);
            startActivity(intent);
            finish();
        });

    }


    private void fill() {
        if (reservation != null) {
            citySpin.setSelection(getCityIndex(reservation.getCity()));
            townTxt.setText(reservation.getTown());
            streetTxt.setText(reservation.getStreet());
        }
    }
    private int getCityIndex(String city) {
        for (int i = 0; i < citySpin.getCount(); i++) {
            if (citySpin.getItemAtPosition(i).toString().equals(city)) {
                return i;
            }
        }
        return 0; // Default index
    }


    private void citySpinnerFill() {
        ArrayList<String> sortedCities = new ArrayList<>();
        Collections.addAll(sortedCities, cities);
        Collections.sort(sortedCities);

        citySpin = findViewById(R.id.citySpin); // Initialize citySpin Spinner

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.style_spinner, sortedCities);

        adapter.setDropDownViewResource(R.layout.style_spinner);

        citySpin.setAdapter(adapter);
    }



    private void connect() {
        progressBar = findViewById(R.id.progressBar2);
        progress20 = findViewById(R.id.progress20);
        progress40 = findViewById(R.id.progress40);
        progress60 = findViewById(R.id.progress60);
        progress80 = findViewById(R.id.progress80);

        citySpin = findViewById(R.id.citySpin);
        townTxt = findViewById(R.id.townTxt);
        streetTxt = findViewById(R.id.streetTxt);

        nextBtn = findViewById(R.id.nextBtn);
        backBtn = findViewById(R.id.backBtn);
    }
}