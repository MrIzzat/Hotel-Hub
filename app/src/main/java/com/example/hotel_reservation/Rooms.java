package com.example.hotel_reservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel_reservation.DatabaseAccess.RoomDA;
import com.example.hotel_reservation.models.Hotel;
import com.example.hotel_reservation.models.Room;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Rooms extends AppCompatActivity implements RecyclerViewInterface {

    private RecyclerView recRooms;
    private TextView txtNoRooms;
    private Hotel currHotel;
    private ArrayList<Room> rooms = new ArrayList<Room>();

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();
    private RoomDA roomda;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        if(MainMenu.LoggedUser==null)
            finish();

        setupSharedPrefs();



        String str = prefs.getString("HOTEL", "");
        currHotel = gson.fromJson(str, Hotel.class);
        getSupportActionBar().setTitle(currHotel.getName());
        Toast.makeText(Rooms.this, currHotel.getName(), Toast.LENGTH_SHORT).show();



        roomda = new RoomDA();


        setupViews();

        addRooms();

    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void addRooms() {


        ArrayList<Room> allRooms = roomda.getRooms();
        for (int i = 0; i < allRooms.size(); i++) {
            if (allRooms.get(i).getHotel().equals(currHotel.getName())) {
                rooms.add(allRooms.get(i));
            }
        }


        if (rooms.size() == 0) {
            txtNoRooms.setVisibility(View.VISIBLE);
        } else {




            Log.d("TEST", "TEST");

            Log.d("ROOMS", rooms.size() + "");


            Room_Adapter Radapter = new Room_Adapter(this, rooms, this);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);

            recRooms.setLayoutManager(layoutManager);
            recRooms.setAdapter(Radapter);
        }
    }


    private void setupViews() {
        recRooms = findViewById(R.id.recRooms);
        txtNoRooms = findViewById(R.id.txtNoRooom);

    }

    @Override
    public void onItemClick(int pos) {
        Room currRoom = rooms.get(pos);

        String roomString = gson.toJson(currRoom);

        editor.putString("ROOM", roomString);
        editor.commit();

        Intent intent = new Intent(Rooms.this, RoomInfo.class);
        startActivity(intent);

       // Toast.makeText(Rooms.this, currRoom.getName(), Toast.LENGTH_SHORT).show();


    }
}