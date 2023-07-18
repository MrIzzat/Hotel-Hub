package com.example.hotel_reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel_reservation.DatabaseAccess.ReservationDA;
import com.example.hotel_reservation.DatabaseAccess.RoomDA;
import com.example.hotel_reservation.models.Reservation;
import com.example.hotel_reservation.models.Room;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Cancel_Reservation extends AppCompatActivity {

    private TextView txtRoomName2;
    private ImageView imgRoom2;
    private TextView txtRoomDesc2;
    private TextView txtRoomPrice2;
    private TextView txtResDate;
    private Button btnCancel;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();

    private Room currRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_reservation);

        setupViews();
        setupSharedPrefs();

        String str = prefs.getString("ROOM", "");

        currRoom = gson.fromJson(str, Room.class);

        Reservation currRes = new Reservation();
        ArrayList<Reservation> allRes = ReservationDA.reservations;

        Log.d("TEST","TEST");
        for(int i =0;i<allRes.size();i++){
            Log.d("RESERVATION",allRes.get(i).getRoomName());
            if(allRes.get(i).getRoomName().equals(currRoom.getName())&&
                    allRes.get(i).getHotel().equals(currRoom.getHotel())){
               currRes=allRes.get(i);
            }
        }
        long days =  daysBetweenDates(currRes.getStartDateStr(),currRes.getEndDateStr())+1;
        String roomInfo ="Hotel: "+ currRes.getHotel();
        String roomDate ="From: "+currRes.getStartDateStr()+"\nTo: "+currRes.getEndDateStr()+"\nDays: "+days;


        txtResDate.setText(roomDate);
        txtRoomDesc2.setText(roomInfo);
        txtRoomName2.setText("Room "+currRoom.getName());
        txtRoomPrice2.setText("Total amount: " + currRes.getPayAmount());
        Picasso.with(this).load(currRoom.getImg()).into(imgRoom2);

        RoomDA roomda = new RoomDA();



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                roomda.cancelReservation(Cancel_Reservation.this,currRoom);

                Toast.makeText(Cancel_Reservation.this, "Canceling Reservation....", Toast.LENGTH_SHORT).show();

                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Cancel_Reservation.this, "Going back to MainMenu", Toast.LENGTH_SHORT).show();
                    }
                },2000);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        roomda.setupAllRooms(Cancel_Reservation.this);

                        Intent intent = new Intent(Cancel_Reservation.this, MainMenu.class);
                        startActivity(intent);
                        finish();
                    }
                },5000);


            }
        });


    }

    static long daysBetweenDates(String date1, String date2)
    {
        DateTimeFormatter dtf = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dtf = DateTimeFormatter.ofPattern("yyyy MM dd");
        }

        String[] ss = (String.valueOf(date1) + "/"
                + String.valueOf(date2))
                .split("/");

        String year, month, day;
        year = ss[0];
        month = ss[1];
        day = ss[2];

        LocalDate start = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            start = LocalDate.parse(year + " " + month + " " + day , dtf);
        }

        year = ss[3];
        month = ss[4];
        day = ss[5];
        LocalDate end = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            end = LocalDate.parse(year + " " + month + " " + day, dtf);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return ChronoUnit.DAYS.between(start, end);
        }
        return -1;
    }


    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void setupViews() {
        txtRoomName2 = findViewById(R.id.txtRoomName2);
        txtRoomDesc2 = findViewById(R.id.txtRoomDesc2);
        imgRoom2 = findViewById(R.id.imgRoom2);
        btnCancel = findViewById(R.id.btnReserve2);
        txtRoomPrice2 = findViewById(R.id.txtRoomPrice2);
        txtResDate = findViewById(R.id.txtResDate);
    }
}