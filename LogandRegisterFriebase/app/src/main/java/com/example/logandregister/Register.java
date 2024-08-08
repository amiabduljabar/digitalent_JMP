package com.example.logandregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private EditText etUsername, etEmail, etPassword;
    private Button btnRegister;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etRegEmail);
        etPassword = findViewById(R.id.etRegPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // Initialize Firebase Database reference
        database = FirebaseDatabase.getInstance().getReference("users");

        // Set click listener for the register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get input values
                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Validate input
                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ada Data Yang Masih Kosong!!", Toast.LENGTH_SHORT).show();
                } else {
                    // Save user data to Firebase
                    database.child(username).child("username").setValue(username);
                    database.child(username).child("email").setValue(email);
                    database.child(username).child("password").setValue(password);

                    Toast.makeText(getApplicationContext(), "Register Berhasil", Toast.LENGTH_SHORT).show();

                    // Start Login activity
                    Intent register = new Intent(getApplicationContext(), Login.class);
                    startActivity(register);
                    finish(); // Optional: Finish the Register activity so the user can't return to it by pressing back
                }
            }
        });
    }
}