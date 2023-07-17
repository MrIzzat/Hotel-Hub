package com.example.hotel_reservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.hotel_reservation.DatabaseAccess.RoomDA;
import com.example.hotel_reservation.models.Room;

import java.util.ArrayList;

public class Reserved_Rooms extends AppCompatActivity implements RecyclerViewInterface {

    private RecyclerView recReserved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_rooms);

        setupViews();

        RoomDA roomda = new RoomDA();

        ArrayList<Room> allRooms = roomda.getRooms();
        ArrayList<Room> reservedRooms = new ArrayList<Room>();

        for (int i=0; i<allRooms.size();i++){
            if(allRooms.get(i).getReservedBy().equals(MainMenu.LoggedUser.getEmail())){
                reservedRooms.add(allRooms.get(i));
            }
        }

        Reserved_Room_Adapter Radapter = new Reserved_Room_Adapter(this, reservedRooms, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recReserved.setLayoutManager(layoutManager);
        recReserved.setAdapter(Radapter);
    }

    private void setupViews() {
        recReserved = findViewById(R.id.recReserved);
    }

    @Override
    public void onItemClick(int pos) {

    }
}