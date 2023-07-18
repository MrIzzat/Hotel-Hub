package com.example.hotel_reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hotel_reservation.DatabaseAccess.UserDA;

public class Change_password extends AppCompatActivity {


    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private Button btnChange;
    private UserDA userda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        setupViews();

        userda = new UserDA();

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();

                if(password.equals("")||confirmPassword.equals(""))
                    Toast.makeText(Change_password.this, "Make Sure Both Fields are not Empty", Toast.LENGTH_SHORT).show();
                else{
                    if(!password.equals(confirmPassword)){
                        Toast.makeText(Change_password.this, "Passwords do not Match", Toast.LENGTH_SHORT).show();
                    }else{
                        userda.changePassword(Change_password.this,password,MainMenu.LoggedUser.getEmail());
                    }
                }

            }
        });
    }

    private void setupViews() {

        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnChange = findViewById(R.id.btnChange);
    }
}