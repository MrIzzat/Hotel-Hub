package com.example.hotel_reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel_reservation.DatabaseAccess.ReservationDA;
import com.example.hotel_reservation.DatabaseAccess.RoomDA;
import com.example.hotel_reservation.models.Reservation;
import com.example.hotel_reservation.models.Room;
import com.google.gson.Gson;

import java.net.InetAddress;

public class Confirm extends AppCompatActivity {

    private boolean flag;
    Reservation reservation;

    private ProgressBar progressBar;
    private ImageView progress20, progress40, progress60, progress80;

    private ProgressControl progressControl;

    private TextView name, start, end, number, pay;

    private Button finishBtn, backBtn;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private Room currRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);


        setupSharedPrefs();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flag = isInternetAvailable();
            }
        },200);


        String reservationJson = getIntent().getStringExtra("reservation");
        Gson gson = new Gson();
        reservation = gson.fromJson(reservationJson, Reservation.class);

        String str = prefs.getString("ROOM", "");
        currRoom = gson.fromJson(str, Room.class);

        connect();
        fill();

        reservation.setHotel(currRoom.getHotel());
        reservation.setRoomName(currRoom.getName());

        progressControl = new ProgressControl(progressBar, progress20, progress40, progress60, progress80);
        progressControl.setProgress(80);


        backBtn.setOnClickListener(v -> {
            Gson gson2 = new Gson();
            String reservationJson2 = gson2.toJson(reservation);

            // Pass the Reservation object as an extra to the next activity
            Intent intent = new Intent(Confirm.this, Payment.class);
            intent.putExtra("reservation", reservationJson2);
            startActivity(intent);
            finish();
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag){
                    reservation.setUser(MainMenu.LoggedUser.getEmail());
                    RoomDA roomDa = new RoomDA();
                    roomDa.reserveRoom(Confirm.this,currRoom,MainMenu.LoggedUser,reservation);



                    Toast.makeText(Confirm.this, "Reserving....", Toast.LENGTH_SHORT).show();



                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Confirm.this, "Going Back to Main Menu", Toast.LENGTH_SHORT).show();
                        }
                    },2000);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            roomDa.setupAllRooms(Confirm.this);
                            new ReservationDA().setupReservations(Confirm.this,MainMenu.LoggedUser);
                            Intent intent = new Intent(Confirm.this, MainMenu.class);
                            startActivity(intent);
                            finish();
                        }
                    },5000);

                }else{
                    Toast.makeText(Confirm.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }





            }
        });
    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }


    private void fill() {
        if (reservation != null) {
            name.setText("Name: " + reservation.getFirstName() + " " + reservation.getLastName());
            start.setText("Start Date: " + reservation.getStartDateStr());
            end.setText("End Date: " + reservation.getEndDateStr());
            number.setText("Number of people: " + reservation.getNumberOfPeople());
            pay.setText("Pay amount: " + reservation.getPayAmount());
        }
    }




    private void connect() {
        progressBar = findViewById(R.id.progressBar2);
        progress20 = findViewById(R.id.progress20);
        progress40 = findViewById(R.id.progress40);
        progress60 = findViewById(R.id.progress60);
        progress80 = findViewById(R.id.progress80);

        name = findViewById(R.id.name);
        start = findViewById(R.id.start);
        end = findViewById(R.id.end);
        number = findViewById(R.id.number);
        pay= findViewById(R.id.pay);


        finishBtn = findViewById(R.id.finishBtn);
        backBtn = findViewById(R.id.backBtn);
    }

    public boolean isInternetAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}