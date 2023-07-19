package com.example.hotel_reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotel_reservation.models.Hotel;
import com.example.hotel_reservation.models.Room;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class RoomInfo extends AppCompatActivity {

    private TextView txtRoomName;
    private ImageView imgRoom;
    private TextView txtRoomDesc;
    private TextView txtRoomPrice;
    private Button btnReserve;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();

    private Room currRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info);

        setupViews();
        setupSharedPrefs();

        String str = prefs.getString("ROOM", "");
        currRoom = gson.fromJson(str, Room.class);

        txtRoomDesc.setText(currRoom.getDesc());
        txtRoomName.setText("Room "+ currRoom.getName());
        txtRoomPrice.setText("Price Per Day: "+currRoom.getPrice());
        Picasso.with(this).load(currRoom.getImg()).into(imgRoom);

        if(currRoom.isReserved()){
            btnReserve.setEnabled(false);
            btnReserve.setText("Reserved");
        }

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RoomInfo.this, Personal.class);
                startActivity(intent);
                finish();
            }
        });



    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void setupViews() {
        txtRoomName = findViewById(R.id.txtRoomName);
        txtRoomDesc = findViewById(R.id.txtRoomDesc);
        imgRoom = findViewById(R.id.imgRoom);
        btnReserve = findViewById(R.id.btnReserve);
        txtRoomPrice = findViewById(R.id.txtRoomPrice);
    }
}