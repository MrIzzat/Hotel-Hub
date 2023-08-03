package com.example.hotel_reservation;

import com.example.hotel_reservation.models.Hotel;

import java.util.ArrayList;

public class HotelSorting {





    protected ArrayList<Hotel> sortHotelsByRating(ArrayList<Hotel> hotels) {
        Hotel temp;
        for (int i = 0; i < hotels.size() - 1; i++) {
            for (int j = i + 1; j < hotels.size(); j++) {
                if (hotels.get(i).getRating() < (hotels.get(j).getRating())) {
                    temp = hotels.get(i);
                    hotels.set(i, hotels.get(j));
                    hotels.set(j, temp);

                }

            }

        }
        return hotels;
    }

    protected ArrayList<Hotel> sortHotelsByNameAsc(ArrayList<Hotel> hotels) {

        Hotel temp;
        for (int i = 0; i < hotels.size() - 1; i++) {
            for (int j = i + 1; j < hotels.size(); j++) {
                if (hotels.get(i).getName().compareToIgnoreCase(hotels.get(j).getName()) < 0) {
                    temp = hotels.get(i);
                    hotels.set(i, hotels.get(j));
                    hotels.set(j, temp);

                }

            }
        }
        return hotels;
    }

    protected ArrayList<Hotel> sortHotelsByNameDesc(ArrayList<Hotel> hotels) {

        Hotel temp;
        for (int i = 0; i < hotels.size() - 1; i++) {
            for (int j = i + 1; j < hotels.size(); j++) {
                if (hotels.get(i).getName().compareToIgnoreCase(hotels.get(j).getName()) > 0) {
                    temp = hotels.get(i);
                    hotels.set(i, hotels.get(j));
                    hotels.set(j, temp);

                }

            }
        }
        return hotels;
    }

}
