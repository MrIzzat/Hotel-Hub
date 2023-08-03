package com.example.hotel_reservation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_reservation.DatabaseAccess.ReservationDA;
import com.example.hotel_reservation.DatabaseAccess.RoomDA;
import com.example.hotel_reservation.models.Reservation;
import com.example.hotel_reservation.models.Room;
import com.squareup.picasso.Picasso;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


public class Reserved_Room_Adapter extends RecyclerView.Adapter<Reserved_Room_Adapter.ViewHolder>{

    private final RecyclerViewInterface recyclerViewInterface;
    private static Context context = null;

    private final ArrayList<Room> roomArrayList;

    public Reserved_Room_Adapter(Context context, ArrayList<Room> roomArrayList,
                                 RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.roomArrayList = roomArrayList;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout3, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        Room model = roomArrayList.get(position);
        holder.txtRoomName.setText("Room "+model.getName());
        holder.txtRoomInfo.setText(model.getHotel());
        holder.txtRoomResDate.setText("Room "+model.getName());

        Reservation currRes = new Reservation();
        ArrayList<Reservation> allRes = ReservationDA.reservations;

        Log.d("TEST","TEST");
        for(int i =0;i<allRes.size();i++){
            Log.d("RESERVATION",allRes.get(i).getRoomName());
            if(allRes.get(i).getRoomName().equals(model.getName())&&
                    allRes.get(i).getHotel().equals(model.getHotel())){
                currRes=allRes.get(i);
            }
        }
        holder.txtRoomResPrice.setText("Total amount: " + currRes.getPayAmount());

        long days =  daysBetweenDates(currRes.getStartDateStr(),currRes.getEndDateStr())+1;
        String roomDate ="From: "+currRes.getStartDateStr()+"\nTo: "+currRes.getEndDateStr()+"\nDays: "+days;

        holder.txtRoomResDate.setText(roomDate);
        Picasso.with(context).load(model.getImg()).into(holder.imgRoom);
        holder.currRoom = model;

        //  Picasso.get().load(model.getImg()).into(holder.imgHotel);
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return roomArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgRoom;
        private final TextView txtRoomName;
        private final TextView txtRoomInfo;
        private final TextView txtRoomResDate;
        private final TextView txtRoomResPrice;

        private final Button btnCancel;
        private EasyFlipView easyFlipView;
        private RoomDA roomda;
        private Room currRoom;



        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface)  {
            super(itemView);
            imgRoom = itemView.findViewById(R.id.imgResRoom);
            txtRoomName = itemView.findViewById(R.id.txtRoomName);
            txtRoomInfo = itemView.findViewById(R.id.txtRoomInfo);
            easyFlipView = itemView.findViewById(R.id.FlipResRoom);
            txtRoomResDate = itemView.findViewById(R.id.txtRoomResDate);
            txtRoomResPrice = itemView.findViewById(R.id.txtRoomResPrice);
            btnCancel = itemView.findViewById(R.id.btnCancel);
            roomda = new RoomDA();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        easyFlipView.flipTheView();

                        if(pos!= RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }

                    }
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    roomda.cancelReservation(context,currRoom);

                    Toast.makeText(context, "Canceling Reservation....", Toast.LENGTH_SHORT).show();

                    Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Settings.fa.finish();
                            Toast.makeText(context, "Going back to MainMenu", Toast.LENGTH_SHORT).show();
                        }
                    },2000);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            roomda.setupAllRooms(context);

                            Intent intent = new Intent(context, MainMenu.class);
                            context.startActivity(intent);
                            ((Activity)context).finish();
                        }
                    },5000);


                }
            });

        }
    }
    static long daysBetweenDates(String date1, String date2)
    {
        DateTimeFormatter dtf = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dtf = DateTimeFormatter.ofPattern("yyyy MM dd");
        }

        String[] ss = (String.valueOf(date1) + "/"
                + String.valueOf(date2))
                .split("/");

        String year, month, day;
        year = ss[0];
        month = ss[1];
        day = ss[2];

        LocalDate start = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            start = LocalDate.parse(year + " " + month + " " + day , dtf);
        }

        year = ss[3];
        month = ss[4];
        day = ss[5];
        LocalDate end = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            end = LocalDate.parse(year + " " + month + " " + day, dtf);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return ChronoUnit.DAYS.between(start, end);
        }
        return -1;
    }

}
