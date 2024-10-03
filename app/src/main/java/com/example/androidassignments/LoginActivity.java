package com.example.androidassignments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);

        String savedEmail = sharedPreferences.getString("DefaultEmail", "email@domain.com");

        EditText emailSpace = findViewById(R.id.emailSpace);
        emailSpace.setText(savedEmail);

        Button loginButton = findViewById(R.id.loginButton);
    }

//    public void login(View view) {
//        EditText emailSpace = findViewById(R.id.emailSpace);
//        EditText passwordSpace = findViewById(R.id.passwordSpace);
//
//        SharedPreferences sharedPreferences1;
//        sharedPreferences1=getSharedPreferences("LoginPrefs", MODE_PRIVATE);
//
//        SharedPreferences.Editor editor = sharedPreferences1.edit();
//        editor.putString("DefaultEmail", emailSpace.getText().toString().trim());
//        editor.apply();
//
//        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        startActivity(intent);
//    }
public void login(View view) {
    EditText emailSpace = findViewById(R.id.emailSpace);
    EditText passwordSpace = findViewById(R.id.passwordSpace);

    // Get email and password values
    String email = emailSpace.getText().toString().trim();
    String password = passwordSpace.getText().toString().trim();

    // Validate email format
    if (email.isEmpty()) {
        emailSpace.setError("Email cannot be empty");
        return; // Stop further execution if email is empty
    }
    if (!isValidEmail(email)) {
        emailSpace.setError("Invalid email format");
        return; // Stop further execution if email is not valid
    }
    // Validate password
    if (password.isEmpty()) {
        passwordSpace.setError("Password cannot be empty");
        return; // Stop further execution if password is empty
    }

    // Save email to SharedPreferences
    SharedPreferences sharedPreferences1 = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences1.edit();
    editor.putString("DefaultEmail", email);
    editor.apply();

    // Start MainActivity
    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
    startActivity(intent);
}

    // Method to check if the email format is valid
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    public void Back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void Forward(View view) {
        Intent intent = new Intent(this, ListItemsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LoginActivity", "onCreate called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LoginActivity", "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LoginActivity", "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LoginActivity", "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LoginActivity", "onDestroy called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("LoginActivity", "onSaveInstanceState called");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("LoginActivity", "onRestoreInstanceState called");
    }
}

