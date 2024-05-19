package com.example.kim_j_project2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
        EditText userInput = findViewById(R.id.userInput);
        String username = userInput.getText().toString();
        EditText pwInput = findViewById(R.id.pwInput);
        String password = pwInput.getText().toString();

        // check if entries are empty
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
            userInput.setText("");
            pwInput.setText("");
            return;
        }
        // check if username already exists
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        Intent myIntent = new Intent(LoginActivity.this, DashboardActivity.class);
        if (sharedPreferences.contains(username)) {
            // validate password
            String sharedPrefPw = sharedPreferences.getString(username + "_password", null);
            if (password.equals(sharedPrefPw)) { // successfully logged in
                Toast.makeText(view.getContext(), "Logging In", Toast.LENGTH_LONG).show();
                // pass username and load dashboard activity
                startActivity(myIntent);
            } else { // incorrect login
                Toast.makeText(view.getContext(), "Invalid Login", Toast.LENGTH_SHORT).show();
                userInput.setText("");
                pwInput.setText("");
            }
        } else {
            // register user
            Toast.makeText(view.getContext(), "Registered", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // save data using shared preferences
            editor.putString(username, username);
            editor.putString(username + "_password", password);
            editor.putString(username + "_budget", "0.0");
            editor.putString(username + "_expense", "0.0");
            editor.apply();
            // pass data using intent put extra
            myIntent.putExtra("username", username);
            Log.i("Debug", "sent username: " + username);
            startActivity(myIntent);
        }
    }
}