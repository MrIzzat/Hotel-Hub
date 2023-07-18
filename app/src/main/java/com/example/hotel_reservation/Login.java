package com.example.hotel_reservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hotel_reservation.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private Button signUp;
    private Button signIn;
    private EditText email_;
    private EditText password_;

    private FirebaseAuth auth;

    private CheckBox chkRemember;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupViews();
        setupPrefs();

    }

    private void setupPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void setupViews() {
        signIn = findViewById(R.id.signIn);
        signUp = findViewById(R.id.signUpBtn);
        email_ = findViewById(R.id.email1);
        password_ = findViewById(R.id.password);
        chkRemember = findViewById(R.id.chkRemember);
    }

    public void signIn(View view) {
        String email = email_.getText().toString();
        String password = password_.getText().toString();

        // Perform validation checks
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);

                            String storedPassword = document.getString("password");
                            String storedPhone = document.getString("phoneNumber");
                            String email = document.getString("email");
                            String FirstName = document.getString("first_name");
                            String LastName = document.getString("last_name");

                            User d = new User(FirstName, LastName, email, storedPhone);

                            Log.d("USERNAME", d.getFirstName());

                            if (password.equals(storedPassword)) {
                                Toast.makeText(Login.this, "Sign-in successful", Toast.LENGTH_SHORT).show();
                                // Store user credentials in shared preferences


//                                editor.putString("username", email);
//                                editor.putString("password", password);
//                                editor.putString("phoneNumber", storedPhone);

                                if (chkRemember.isChecked()) {
                                    editor.putBoolean("isLoggedIn", true);
                                }

                                // Create JSON element with user information
                                Gson gson = new Gson();
                                String user = gson.toJson(d);
//                                    try {
//                                        userJson.put("firstName", document.getString("firstName"));
//                                        userJson.put("lastName", document.getString("lastName"));
//                                        userJson.put("email", email);
//                                        userJson.put("phone", document.getString("phoneNumber"));
//
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }

                                // Store the JSON element as a string in shared preferences
                                editor.putString("USER", user);

                                editor.commit();


                                // Redirect the user to the desired activity (e.g., MainMenu)
                                Intent intent = new Intent(Login.this, MainMenu.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Login.this, "User does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Error fetching user data", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void signUpClk(View view) {
        Intent intent = new Intent(Login.this, Registration.class);
        startActivity(intent);
        finish();
    }
}
