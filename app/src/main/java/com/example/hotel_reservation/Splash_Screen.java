package com.example.hotel_reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel_reservation.DatabaseAccess.HotelDA;
import com.example.hotel_reservation.DatabaseAccess.ReservationDA;
import com.example.hotel_reservation.DatabaseAccess.RoomDA;

import org.checkerframework.checker.units.qual.A;

public class Splash_Screen extends AppCompatActivity {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private Animation top, bottom,spin;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        img = findViewById(R.id.img);

        top = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        spin = AnimationUtils.loadAnimation(this,R.anim.spin_animation);



        AnimationSet s = new AnimationSet(false);
        s.addAnimation(top);
        s.addAnimation(spin);
        img.setAnimation(s);

//        AnimatorSet animset = new AnimatorSet();
//        animset.playSequentially(top,bottom);



        // Check shared preferences for login status
        setupPrefs();


        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLoggedIn) {
                    // User is logged in, go to MainActivity2
                    Intent intent = new Intent(Splash_Screen.this, MainMenu.class);
                    startActivity(intent);

                } else {
                    // User is not logged in, go to LoginActivity
                    Intent intent = new Intent(Splash_Screen.this, Login.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 5000);

        new RoomDA().setupAllRooms(this);
        new HotelDA().setupAllHotels(this);

    }
    private void setupPrefs(){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

}
