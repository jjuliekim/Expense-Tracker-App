package com.example.kim_j_project2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

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
    }

    // handle login process
    public void checkLogin(View view) {
        EditText userInput = view.findViewById(R.id.userInput);
        String username = userInput.getText().toString();
        EditText pwInput = view.findViewById(R.id.pwInput);
        String password = pwInput.getText().toString();

        // check if username already exists
        SharedPreferences sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        Intent myIntent = new Intent(LoginActivity.this, DashboardActivity.class); // from, to
        if (sharedPreferences.contains(username)) {
            // validate password
            String sharedPrefPw = sharedPreferences.getString(username, null);
            if (password.equals(sharedPrefPw)) {
                Toast.makeText(view.getContext(), "Logging In", Toast.LENGTH_LONG).show();
                startActivity(myIntent);
            } else {
                Toast.makeText(view.getContext(), "Invalid Login", Toast.LENGTH_SHORT).show();
            }
        } else {
            // if new username, save info to Shared Preferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(username, password);
            editor.apply();
            Toast.makeText(view.getContext(), "Registered", Toast.LENGTH_SHORT).show();
            startActivity(myIntent);
        }
    }
}