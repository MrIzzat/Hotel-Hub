package com.example.hotel_reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hotel_reservation.DatabaseAccess.UserDA;
import com.example.hotel_reservation.models.SettingsItem;

public class Settings extends AppCompatActivity {

    private ListView lvSettings;
    public static Activity fa;
    private UserDA userda;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if(MainMenu.LoggedUser==null)
            finish();

        fa=this;
        userda = new UserDA();

        getSupportActionBar().setTitle("Settings");
        setupViews();

        SettingsListAdapter adapter = new SettingsListAdapter(this,R.layout.adapter_view_layout_settings, SettingsItem.setting_items);
        lvSettings.setAdapter(adapter);


        AdapterView.OnItemClickListener ItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent intent = new Intent(Settings.this,Reserved_Rooms.class);
                    startActivity(intent);
                }else{
                    if (position==1){
                        Intent intent = new Intent(Settings.this,About_Us.class);
                        startActivity(intent);
                    }else{
                        if (position==2){
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case DialogInterface.BUTTON_POSITIVE:

                                            userda.deleteUser(Settings.this, MainMenu.LoggedUser);

                                            MainMenu.LoggedUser=null;
                                            MainMenu.fa.finish();

                                            setupPrefs();
                                            editor.putBoolean("isLoggedIn", false);
                                            editor.putBoolean("LoggedIn", false);
                                            editor.putString("USER", "");
                                            editor.commit();

                                            Intent intent = new Intent(Settings.this,Login.class);
                                            startActivity(intent);
                                            finish();
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:

                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).show();
                        }
                    }
                }
            }
        };

        lvSettings.setOnItemClickListener(ItemClickListener);
    }

    private void setupPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void setupViews() {
        lvSettings =findViewById(R.id.lvSettings);
    }
}