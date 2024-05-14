package com.example.kim_j_project2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        // if username exists, check if matches password
        // if incorrect password -> Toast ("Invalid Log In")
        // if new username, save info to Shared Preferences -> Toast ("Registered")
        // direct to dashboard activity
    }
}