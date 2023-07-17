package com.example.hotel_reservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_reservation.models.Room;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Room_Adapter extends RecyclerView.Adapter<Room_Adapter.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private final Context context;

    private final ArrayList<Room> roomArrayList;

    public Room_Adapter(Context context, ArrayList<Room> roomArrayList,
                         RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.roomArrayList = roomArrayList;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout2, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        Room model = roomArrayList.get(position);
        holder.txtHotelName.setText(model.getName());
        Picasso.with(context).load(model.getImg()).into(holder.imgVHotel);

      //  Picasso.get().load(model.getImg()).into(holder.imgHotel);
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return roomArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgVHotel;
        private final TextView txtHotelName;



        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface)  {
            super(itemView);
            imgVHotel = itemView.findViewById(R.id.imgHotel);
            txtHotelName = itemView.findViewById(R.id.txthotelName);


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
