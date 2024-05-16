package com.example.kim_j_project2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // retrieve info from login activity
        Intent myIntent = getIntent();
        String username = myIntent.getStringExtra("username");
        String budget = myIntent.getStringExtra("budget");
        String expense = myIntent.getStringExtra("expense");
        String balance = myIntent.getStringExtra("balance");

        // set xml texts
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText(String.format("Welcome, %s!", username));
        if (!Objects.equals(budget, "0")) {
            EditText budgetText = findViewById(R.id.budgetText);
            budgetText.setText(budget);
        }
        TextView expenseText = findViewById(R.id.totalExpText);
        expenseText.setText(expense);
        TextView balanceText = findViewById(R.id.balanceText);
        balanceText.setText(balance);
    }
}