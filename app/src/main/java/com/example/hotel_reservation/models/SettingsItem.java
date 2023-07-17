package com.example.hotel_reservation.models;

import com.example.hotel_reservation.R;

public class SettingsItem {
    private String name;
    private String img;

    public static SettingsItem[] setting_items ={
            new SettingsItem("Reserved Rooms", "drawable://" + R.drawable.room_list),
            new SettingsItem("About Us", "drawable://" + R.drawable.about_us)
    };

    public SettingsItem(String name, String img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
