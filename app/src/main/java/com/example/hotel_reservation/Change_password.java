package com.example.hotel_reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Change_password extends AppCompatActivity {


    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private Button btnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        setupViews();
    }

    private void setupViews() {
    }
}