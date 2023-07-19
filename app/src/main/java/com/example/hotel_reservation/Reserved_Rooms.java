package com.example.hotel_reservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.hotel_reservation.DatabaseAccess.RoomDA;
import com.example.hotel_reservation.models.Room;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Reserved_Rooms extends AppCompatActivity implements RecyclerViewInterface {

    private RecyclerView recReserved;
    private TextView txtNoRes;
    private  ArrayList<Room> reservedRooms;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();

    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_rooms);

        fa=this;

        setupViews();
        setupPrefs();

        RoomDA roomda = new RoomDA();

        ArrayList<Room> allRooms = roomda.getRooms();
        reservedRooms = new ArrayList<Room>();

        for (int i=0; i<allRooms.size();i++){
            if(allRooms.get(i).getReservedBy().equals(MainMenu.LoggedUser.getEmail())){
                reservedRooms.add(allRooms.get(i));
            }
        }
        if(reservedRooms.size()==0){
            txtNoRes.setVisibility(View.VISIBLE);
        }

        Reserved_Room_Adapter Radapter = new Reserved_Room_Adapter(this, reservedRooms, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recReserved.setLayoutManager(layoutManager);
        recReserved.setAdapter(Radapter);
    }

    private void setupPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }


    private void setupViews() {
        recReserved = findViewById(R.id.recReserved);
        txtNoRes = findViewById(R.id.txtNoRes);
    }

    @Override
    public void onItemClick(int pos) {

        Room currRoom = reservedRooms.get(pos);

        String roomString = gson.toJson(currRoom);

        editor.putString("ROOM", roomString);
        editor.commit();

        Intent intent = new Intent(Reserved_Rooms.this, Cancel_Reservation.class);
        startActivity(intent);
    }


}