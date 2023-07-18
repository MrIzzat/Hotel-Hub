package com.example.hotel_reservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.hotel_reservation.DatabaseAccess.HotelDA;
import com.example.hotel_reservation.DatabaseAccess.ReservationDA;
import com.example.hotel_reservation.models.Hotel;
import com.example.hotel_reservation.models.Room;
import com.example.hotel_reservation.models.User;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, RecyclerViewInterface {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView mainNavBar;

    private RecyclerView recHotel;

    public static User LoggedUser;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();

    private boolean flag = false;
    public static final String FLAG = "FLAG";

    private ArrayList<Hotel> hotels = new ArrayList<Hotel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setupViews();


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hotel Reservation");

        if (mainNavBar != null) {
            mainNavBar.setNavigationItemSelectedListener(this);
        }

        addHotels();
        setupPrefs();
        checkPrefs();
        getReservations();
        setNavHeader();
    }

    private void getReservations() {
        ReservationDA resda = new ReservationDA();
        Log.d("LoggedUSER", LoggedUser.getEmail());
        resda.setupReservations(this, LoggedUser);
    }

    private void setupPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void checkPrefs() {

        String str = prefs.getString("USER", "");
        User user = gson.fromJson(str, User.class);

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String phoneNumber = user.getTelephone();

        LoggedUser = new User(firstName, lastName, email, phoneNumber);


    }

    private void addHotels() {
        HotelDA hotelda = new HotelDA();

        hotels = hotelda.getHotels();

        Hotel_Adapter Hadapter = new Hotel_Adapter(this, hotels, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recHotel.setLayoutManager(linearLayoutManager);
        recHotel.setAdapter(Hadapter);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            Intent intent = new Intent(MainMenu.this, Settings.class);
            startActivity(intent);
        } else {
            if (id == R.id.nav_username) {
                Intent intent = new Intent(MainMenu.this, Change_user_info.class);
                startActivity(intent);
            } else {
                if (id == R.id.nav_password) {
                    Intent intent = new Intent(MainMenu.this, Change_password.class);
                    startActivity(intent);
                } else {
                    if (id == R.id.nav_logout) {

                        // LoggedUser = null;

                        editor.putBoolean("isLoggedIn", false);
                        editor.putString("USER", "");
                        editor.commit();


                        Intent intent = new Intent(MainMenu.this, Login.class);
                        startActivity(intent);
                    }
                }
            }
        }
        return true;
    }

    private void setupViews() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        mainNavBar = findViewById(R.id.main_NavBar);
        recHotel = findViewById(R.id.recHotel);

    }

    private void setNavHeader() {
        View headerView = mainNavBar.getHeaderView(0);
        TextView headerUsername = (TextView) headerView.findViewById(R.id.header_username);
        headerUsername.setText(LoggedUser.getFirstName());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        } else {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int pos) {

        Hotel currHotel = hotels.get(pos);

        String hotelString = gson.toJson(currHotel);

        editor.putString("HOTEL", hotelString);
        editor.commit();

        Intent intent = new Intent(MainMenu.this, Rooms.class);
        startActivity(intent);


    }
}
