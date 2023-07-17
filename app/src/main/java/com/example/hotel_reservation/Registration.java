package com.example.hotel_reservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.QuerySnapshot;

import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


public class Registration extends AppCompatActivity {

    private Button logInButton;
    private Button signUp;
    private EditText first_name;
    private EditText last_name;
    private EditText email_;
    private EditText password_;
    private EditText phoneNumber_;
    private EditText confirm_password;
    private TextView error;


    private static  final String firstName="First Name";
    private static  final String lastName="Last Name";
    private static final  String email ="Email";
    private static final String password="Password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        signUp=findViewById(R.id.signIn);
        first_name=findViewById(R.id.email1);
        last_name=findViewById(R.id.lastName2);
        email_=findViewById(R.id.email);
        password_=findViewById(R.id.password);
        confirm_password=findViewById(R.id.password2);
        error=findViewById(R.id.errorM);
        phoneNumber_=findViewById(R.id.phoneNumber);
    }


    public void login(View view){
        Intent intent=new Intent(Registration.this,Login.class);
        startActivity(intent);
        finish();
    }
    public void signUp(View view) {
        String firstNameValue = first_name.getText().toString();
        String lastNameValue = last_name.getText().toString();
        String emailValue = email_.getText().toString();
        String phoneValue = phoneNumber_.getText().toString();

        String passwordValue = password_.getText().toString();
        String confirmPasswordValue = confirm_password.getText().toString();

        if (firstNameValue.isEmpty() || lastNameValue.isEmpty() || emailValue.isEmpty()
                || passwordValue.isEmpty() || confirmPasswordValue.isEmpty()) {
            error.setText("Fill in all fields");
        } else if (!passwordValue.equals(confirmPasswordValue)) {
            error.setText("Passwords do not match");
        } else {
            // Check if the email already exists in Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users")
                    .whereEqualTo("email", emailValue)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                Toast.makeText(Registration.this, "Email already exists", Toast.LENGTH_SHORT).show();

                            } else {

                                // Email does not exist, proceed to add user to Firestore
                                DocumentReference userRef = db.collection("users").document();
                                Map<String, Object> user = new HashMap<>();

                                user.put("first_name", firstNameValue);
                                user.put("last_name", lastNameValue);
                                user.put("email", emailValue);
                                user.put("password", passwordValue);
                                user.put("phoneNumber", phoneValue);


                                userRef.set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(Registration.this, firstNameValue+" Aded succesfuly", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Registration.this, "An error occurred while making the account", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registration.this, "An error occurred while checking the email", Toast.LENGTH_SHORT).show();
                            Log.d("ERROR",e.toString());
                        }
                    });
        }
    }



}

