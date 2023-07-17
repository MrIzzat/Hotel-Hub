package com.example.hotel_reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hotel_reservation.models.SettingsItem;

public class Settings extends AppCompatActivity {

    private ListView lvSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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
                    }
                }
            }
        };

        lvSettings.setOnItemClickListener(ItemClickListener);
    }

    private void setupViews() {
        lvSettings =findViewById(R.id.lvSettings);
    }
}