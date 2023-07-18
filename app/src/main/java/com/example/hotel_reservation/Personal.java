package com.example.hotel_reservation;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel_reservation.models.Reservation;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Personal extends AppCompatActivity {
    Reservation reservation;
    private ProgressBar progressBar;
    private ImageView progress20, progress40, progress60, progress80;
    private ProgressControl progressControl;

    private Button startDateBtn, endDateBtn, removeBtn, addBtn, nextBtn;
    private TextView startDateTv, endDateTv, numberOfPeople;

    private EditText fNameEdtTxt, lNameEdtTxt;

    private Calendar startDate, endDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        String reservationJson = getIntent().getStringExtra("reservation");
        Gson gson = new Gson();
        if (reservationJson != null && !reservationJson.isEmpty()) {
            reservation = gson.fromJson(reservationJson, Reservation.class);
        } else {
            reservation = new Reservation();
        }


        connect();
        fill();

        progressControl = new ProgressControl(progressBar, progress20, progress40, progress60, progress80);

        addBtn.setOnClickListener(v -> {
            int currentNumberOfPeople = Integer.parseInt(numberOfPeople.getText().toString());
            if (currentNumberOfPeople < 6) {
                currentNumberOfPeople++;
                numberOfPeople.setText(String.valueOf(currentNumberOfPeople));
            }
            if (currentNumberOfPeople == 6) {
                addBtn.setEnabled(false);
            }
            removeBtn.setEnabled(true);
        });

        removeBtn.setOnClickListener(v -> {
            int currentNumberOfPeople = Integer.parseInt(numberOfPeople.getText().toString());
            if (currentNumberOfPeople > 1) {
                currentNumberOfPeople--;
                numberOfPeople.setText(String.valueOf(currentNumberOfPeople));
            }
            if (currentNumberOfPeople == 1) {
                removeBtn.setEnabled(false);
            }
            if (currentNumberOfPeople < 6) {
                addBtn.setEnabled(true);
            }
        });

        startDateBtn.setOnClickListener(v -> {
            openDialogStart();
        });

        endDateBtn.setOnClickListener(v -> {
            openDialogEnd();
        });

        nextBtn.setOnClickListener(v -> {
            String fName = fNameEdtTxt.getText().toString().trim();
            String lName = lNameEdtTxt.getText().toString().trim();
            String numPeople = numberOfPeople.getText().toString().trim();
            String startDateStr = startDateTv.getText().toString().trim();
            String endDateStr = endDateTv.getText().toString().trim();

            // Perform validation checks
            if (fName.isEmpty() || lName.isEmpty() || startDateStr.isEmpty() || endDateStr.isEmpty()) {
                Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Set the properties of the Reservation object
            reservation.setFirstName(fName);
            reservation.setLastName(lName);
            reservation.setNumberOfPeople(Integer.valueOf(numPeople));
            reservation.setStartDateStr(startDateStr);
            reservation.setEndDateStr(endDateStr);
//            reservation.setStartDate(startDate);
//            reservation.setEndDate(endDate);

            // Convert the Reservation object to a JSON string using Gson
            Gson gson2 = new Gson();
            String reservationJson2 = gson2.toJson(reservation);

            // Pass the Reservation JSON string as an extra to the next activity
            Intent intent = new Intent(Personal.this, Address.class);
            intent.putExtra("reservation", reservationJson2);
            startActivity(intent);
            finish();
        });
    }

    private void fill() {
        if (reservation != null) {
            fNameEdtTxt.setText(reservation.getFirstName());
            lNameEdtTxt.setText(reservation.getLastName());
            numberOfPeople.setText(String.valueOf(reservation.getNumberOfPeople()));
            startDateTv.setText(reservation.getStartDateStr());
            endDateTv.setText(reservation.getEndDateStr());
        }
    }

    private void openDialogStart() {
        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(selectedYear, selectedMonth, selectedDay);
            if (selectedDate.before(currentDate)) {
                Toast.makeText(this, "Start date cannot be before the current date.", Toast.LENGTH_SHORT).show();
            } else if (endDate != null && selectedDate.after(endDate)) {
                Toast.makeText(this, "Start date cannot be after the end date.", Toast.LENGTH_SHORT).show();
            } else {
                startDate = selectedDate;
                startDateTv.setText(formatDate(startDate));
            }
        }, year, month, day);
        dialog.show();
    }

    private void openDialogEnd() {
        if (startDate == null && endDateTv.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please choose a start date first.", Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(selectedYear, selectedMonth, selectedDay);
            selectedDate.add(Calendar.DAY_OF_MONTH, -0);
            if (selectedDate.before(startDate)) {
                Toast.makeText(this, "End date should be at least one day after the start date.", Toast.LENGTH_SHORT).show();
            } else {
                endDate = selectedDate;
                endDateTv.setText(formatDate(endDate));
            }
        }, year, month, day);
        dialog.show();
    }

    private void connect() {
        progressBar = findViewById(R.id.progressBar2);
        progress20 = findViewById(R.id.progress20);
        progress40 = findViewById(R.id.progress40);
        progress60 = findViewById(R.id.progress60);
        progress80 = findViewById(R.id.progress80);

        startDateBtn = findViewById(R.id.startDateBtn);
        endDateBtn = findViewById(R.id.endDateBtn);

        startDateTv = findViewById(R.id.startDateTv);
        endDateTv = findViewById(R.id.endDateTv);

        // Additional elements
        numberOfPeople = findViewById(R.id.numberOfPeople);
        removeBtn = findViewById(R.id.removeBtn);
        addBtn = findViewById(R.id.addBtn);
        fNameEdtTxt = findViewById(R.id.fNameEdtTxt);
        lNameEdtTxt = findViewById(R.id.lNameEdtTxt);

        nextBtn = findViewById(R.id.nextBtn);
    }

    private String formatDate(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        return dateFormat.format(calendar.getTime());
    }
}
