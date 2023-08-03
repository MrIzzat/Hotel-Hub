package com.example.hotel_reservation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_reservation.DatabaseAccess.HotelDA;
import com.example.hotel_reservation.DatabaseAccess.ReservationDA;
import com.example.hotel_reservation.models.Hotel;
import com.example.hotel_reservation.models.User;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

public class MainMenu extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, RecyclerViewInterface {

    private TextView txtNoHotel;

    private Spinner spinnerSort;
    private Spinner spinnerCity;
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

    private String[] cities = {"All Cities", "Nablus", "Ramallah", "Gaza", "Hebron", "Jenin", "Bethlehem", "Tulkarm", "Jerusalem"};
    private String[] sortOptions = {"Sort by", "(A-Z)", "(Z-A)", "Rating"};

    private HotelSorting hotelSort;
    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setupViews();
        hotelSort = new HotelSorting();
        fa = this;


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hotel Hub");

        if (mainNavBar != null) {
            mainNavBar.setNavigationItemSelectedListener(this);
        }

        addHotelBasedOnCity("All Cities");
        addHotels();
        setupPrefs();
        checkPrefs();
        getReservations();
        setNavHeader();
        setupSpinnerCity();
        spinnerSortFill();

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                addHotelBasedOnCity(parent.getItemAtPosition(position).toString());
                spinnerSort.setSelection(0);
                addHotels();


                // Toast.makeText(MainMenu.this,parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                } else {
                    if (position == 1) {
                        hotels = hotelSort.sortHotelsByNameDesc(hotels);
                        addHotels();
                    } else {
                        if (position == 2) {
                            hotels = hotelSort.sortHotelsByNameAsc(hotels);
                            addHotels();
                        }
                        if (position == 3) {
                            hotels = hotelSort.sortHotelsByRating(hotels);
                            addHotels();
                        }
                    }
                }
                // Toast.makeText(MainMenu.this,parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

    }


    private void addHotelBasedOnCity(String city) {

        HotelDA hotelda = new HotelDA();

        ArrayList<Hotel> allHotels = hotelda.getHotels();
        // hotels = (ArrayList) hotelda.getHotels().clone();

        hotels = new ArrayList<Hotel>();

        if (!city.equals("All Cities")) {
            for (int i = 0; i < allHotels.size(); i++) {
                if (allHotels.get(i).getCity().equals(city)) {
                    hotels.add(allHotels.get(i));
                }
            }
        } else {
            hotels = allHotels;
        }

    }

    private void spinnerSortFill() {

        ArrayList<String> sortOpt = new ArrayList<>();

        Collections.addAll(sortOpt, sortOptions);
        // Collections.sort(sortedCities);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.style_spinner, sortOpt);

        adapter.setDropDownViewResource(R.layout.style_spinner);

        spinnerSort.setAdapter(adapter);
    }

    private void setupSpinnerCity() {
        ArrayList<String> sortedCities = new ArrayList<>();

        Collections.addAll(sortedCities, cities);
        Collections.sort(sortedCities);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.style_spinner, sortedCities);

        adapter.setDropDownViewResource(R.layout.style_spinner);

        spinnerCity.setAdapter(adapter);
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

        boolean LoggedIn = prefs.getBoolean("LoggedIn", false);

        if(LoggedIn){
            String str = prefs.getString("USER", "");
            User user = gson.fromJson(str, User.class);

            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String email = user.getEmail();
            String phoneNumber = user.getTelephone();

            LoggedUser = new User(firstName, lastName, email, phoneNumber);
        }else{
            finish();
        }




    }

    private void addHotels() {


        if (hotels.size() == 0) {
            txtNoHotel.setVisibility(View.VISIBLE);
        } else {
            txtNoHotel.setVisibility(View.GONE);

        }

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

                        LoggedUser = null;

                        editor.putBoolean("isLoggedIn", false);
                        editor.putBoolean("LoggedIn", false);
                        editor.putString("USER", "");
                        editor.commit();



                        Intent intent = new Intent(MainMenu.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }
        return true;
    }

    private void setupViews() {

        txtNoHotel = findViewById(R.id.txtNoHotel);
        spinnerCity = findViewById(R.id.spinnerCity);
        spinnerSort = findViewById(R.id.spinnerSort);
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

//        Hotel currHotel = hotels.get(pos);
//
//        String hotelString = gson.toJson(currHotel);
//
//        editor.putString("HOTEL", hotelString);
//        editor.commit();



    }
}
