package com.example.hotel_reservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_reservation.models.Hotel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Hotel_Adapter extends RecyclerView.Adapter<Hotel_Adapter.ViewHolder>{

    private final RecyclerViewInterface recyclerViewInterface;
    private final Context context;
    private final ArrayList<Hotel> hotelArrayList;

    public Hotel_Adapter(Context context, ArrayList<Hotel> hotelArrayList,
                         RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.hotelArrayList = hotelArrayList;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        Hotel model = hotelArrayList.get(position);
        holder.txtHotelName.setText(model.getName());
        Picasso.with(context).load(model.getImg()).into(holder.imgHotel);
        holder.ratingHotel.setRating((float)model.getRating());
      //  holder.imgHotel.setImageResource(model.getImg());
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return hotelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgHotel;
        private final TextView txtHotelName;
        private final RatingBar ratingHotel;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface)  {
            super(itemView);
            imgHotel = itemView.findViewById(R.id.imgHotel);
            txtHotelName = itemView.findViewById(R.id.txthotelName);
            ratingHotel = itemView.findViewById(R.id.ratingHotel);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos!= RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }

                    }
                }
            });

        }
    }


}
