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
        EditText userInput = findViewById(R.id.userInput);
        String username = userInput.getText().toString();
        EditText pwInput = findViewById(R.id.pwInput);
        String password = pwInput.getText().toString();

        // check if entries are empty
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
            userInput.setText("");
            pwInput.setText("");
        }
        // check if username already exists
        SharedPreferences sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        Intent myIntent = new Intent(LoginActivity.this, DashboardActivity.class); // from, to
        if (sharedPreferences.contains(username + "_password")) {
            // validate password
            String sharedPrefPw = sharedPreferences.getString(username + "_password", null);
            if (password.equals(sharedPrefPw)) { // successfully logged in
                Toast.makeText(view.getContext(), "Logging In", Toast.LENGTH_LONG).show();
                myIntent.putExtra("username", username);
                String budget = sharedPreferences.getString(username + "_budget", "0");
                String expense = sharedPreferences.getString(username + "_expense", "0");
                String balance = sharedPreferences.getString(username + "_balance", "0");
                myIntent.putExtra("budget", budget);
                myIntent.putExtra("expense", expense);
                myIntent.putExtra("balance", balance);
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
            editor.putString(username + "_password", password);
            editor.putString(username + "_budget", "0");
            editor.putString(username + "_expense", "0");
            editor.putString(username + "_balance", "0");
            editor.apply();
            myIntent.putExtra("username", username);
            myIntent.putExtra("budget", "0");
            myIntent.putExtra("expense", "0");
            myIntent.putExtra("balance", "0");
            startActivity(myIntent);
        }
    }
}