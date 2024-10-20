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

//sdfsdfsd

        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);

        String savedEmail = sharedPreferences.getString("DefaultEmail", "email@domain.com");

        EditText emailSpace = findViewById(R.id.emailSpace);
        emailSpace.setText(savedEmail);

    }
public void login(View view) {
    EditText emailSpace = findViewById(R.id.emailSpace);
    EditText passwordSpace = findViewById(R.id.passwordSpace);

    String email = emailSpace.getText().toString().trim();
    String password = passwordSpace.getText().toString().trim();

    if (email.isEmpty()) {
        emailSpace.setError("Email cannot be empty");
        return;
    }
    if (!isValidEmail(email)) {
        emailSpace.setError("Invalid email format");
        return;
    }
    if (password.isEmpty()) {
        passwordSpace.setError("Password cannot be empty");
        return;
    }

    SharedPreferences sharedPreferences1 = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences1.edit();
    editor.putString("DefaultEmail", email);
    editor.apply();

    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
    startActivity(intent);
}

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
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

