package com.example.hotel_reservation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel_reservation.models.Reservation;
import com.example.hotel_reservation.models.Room;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Payment extends AppCompatActivity {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Reservation reservation;

    private ProgressControl progressControl;
    private ProgressBar progressBar;
    private ImageView progress20, progress40, progress60, progress80;

    private TextView payAmount;

    private RadioButton radioButton1, radioButton2;

    private LinearLayout creditLayout;

    private EditText cardNumberEditText, cardholderNameEditText, expiryDateEditText, cvvEditText;

    private Button nextBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        setupPrefs();

        String reservationJson = getIntent().getStringExtra("reservation");
        Gson gson = new Gson();
        reservation = gson.fromJson(reservationJson, Reservation.class);

        String str = prefs.getString("ROOM","");

        Room currRoom = gson.fromJson(str, Room.class);

        connect();
        fill();

        long numOfDays = daysBetweenDates(reservation.getStartDateStr(),reservation.getEndDateStr())+1;

        double price = numOfDays*currRoom.getPrice();

        payAmount.setText(price+"");

        progressControl = new ProgressControl(progressBar, progress20, progress40, progress60, progress80);
        progressControl.setProgress(60);


        radioButton1.setOnClickListener(v -> {
            creditLayout.setVisibility(View.VISIBLE);
        });

        radioButton2.setOnClickListener(v -> {
            creditLayout.setVisibility(View.INVISIBLE);
        });

        nextBtn.setOnClickListener(v -> {
            String payAmountStr = payAmount.getText().toString().trim();
            boolean cash = radioButton2.isChecked();
            String cardNumber = cardNumberEditText.getText().toString().trim();
            String holderName = cardholderNameEditText.getText().toString().trim();
            String expDate = expiryDateEditText.getText().toString().trim();
            String cvv = cvvEditText.getText().toString().trim();

            // Perform validation checks
            if (radioButton1.isChecked()) {
                // Credit card payment is selected
                if (cardNumber.isEmpty() || holderName.isEmpty() || expDate.isEmpty() || cvv.isEmpty()) {
                    Toast.makeText(this, "Please fill in all the credit card details.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Update the Reservation object with payment details
            reservation.setPayAmount(payAmountStr);
            reservation.setPayCash(cash);
            reservation.setCardNumber(cardNumber);
            reservation.setHolderName(holderName);
            reservation.setExpDate(expDate);
            reservation.setCvv(cvv);

            // Convert the Reservation object to a JSON string using Gson
            Gson gson2 = new Gson();
            String reservationJson2 = gson2.toJson(reservation);

            // Pass the Reservation object as an extra to the next activity
            Intent intent = new Intent(Payment.this, Confirm.class);
            intent.putExtra("reservation", reservationJson2);
            startActivity(intent);
            finish();
        });


        backBtn.setOnClickListener(v -> {
            String payAmountStr = payAmount.getText().toString().trim();
            boolean cash = radioButton2.isChecked();
            String cardNumber = cardNumberEditText.getText().toString().trim();
            String holderName = cardholderNameEditText.getText().toString().trim();
            String expDate = expiryDateEditText.getText().toString().trim();
            String cvv = cvvEditText.getText().toString().trim();


            // Update the Reservation object with payment details
            reservation.setPayAmount(payAmountStr);
            reservation.setPayCash(cash);
            reservation.setCardNumber(cardNumber);
            reservation.setHolderName(holderName);
            reservation.setExpDate(expDate);
            reservation.setCvv(cvv);

            // Convert the Reservation object to a JSON string using Gson
            Gson gson2 = new Gson();
            String reservationJson2 = gson2.toJson(reservation);

            // Pass the Reservation object as an extra to the next activity
            Intent intent = new Intent(Payment.this, Address.class);
            intent.putExtra("reservation", reservationJson2);
            startActivity(intent);
            finish();

        });


    }

    private void setupPrefs(){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }
    // Function to find the number of days
    // between given two dates
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

    private void fill() {
        if (reservation != null) {
            payAmount.setText(reservation.getPayAmount());
            if (reservation.isPayCash()) {
                radioButton2.setChecked(true);
                creditLayout.setVisibility(View.INVISIBLE);
            } else {
                radioButton1.setChecked(true);
                creditLayout.setVisibility(View.VISIBLE);
                cardNumberEditText.setText(reservation.getCardNumber());
                cardholderNameEditText.setText(reservation.getHolderName());
                expiryDateEditText.setText(reservation.getExpDate());
                cvvEditText.setText(reservation.getCvv());
            }
        }
    }


    private void connect() {
        progressBar = findViewById(R.id.progressBar2);
        progress20 = findViewById(R.id.progress20);
        progress40 = findViewById(R.id.progress40);
        progress60 = findViewById(R.id.progress60);
        progress80 = findViewById(R.id.progress80);

        payAmount = findViewById(R.id.payAmount);

        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);

        creditLayout = findViewById(R.id.creditLayout);

        cardNumberEditText = findViewById(R.id.cardNumberEditText);
        cardholderNameEditText = findViewById(R.id.cardholderNameEditText);
        expiryDateEditText = findViewById(R.id.expiryDateEditText);
        cvvEditText = findViewById(R.id.cvvEditText);

        nextBtn = findViewById(R.id.nextBtn);
        backBtn = findViewById(R.id.backBtn);

    }
}
