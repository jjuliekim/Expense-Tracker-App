package com.example.kim_j_project2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

        // retrieve stored information
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String budget = sharedPreferences.getString(username + "_budget", "0");
        String expense = sharedPreferences.getString(username + "_expense", "0");

        // set dashboard texts
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText(String.format("Welcome, %s!", username));
        if (!Objects.equals(budget, "0")) {
            EditText budgetText = findViewById(R.id.budgetText);
            budgetText.setText(budget);
        }
        TextView expenseText = findViewById(R.id.totalExpText);
        expenseText.setText(expense);
        TextView balanceText = findViewById(R.id.balanceText);
        double balance = Integer.parseInt(budget) - Integer.parseInt(expense);
        balanceText.setText(String.valueOf(balance));
    }

    // send to add expense activity
    public void addExpense(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Intent myIntent = getIntent();
        String username = myIntent.getStringExtra("username");

        // save budget and expense
        EditText budgetText = findViewById(R.id.budgetText);
        editor.putString(username + "_budget", budgetText.getText().toString());
        TextView expenseText = findViewById(R.id.totalExpText);
        editor.putString(username + "_expense", expenseText.getText().toString());
        editor.apply();

        // go to add expense activity
        Intent nextIntent = new Intent(DashboardActivity.this, AddExpenseActivity.class);
        startActivity(nextIntent);
    }

    // send to expense list activity
    public void showExpenseList(View view) {
        // save budget
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Intent myIntent = getIntent();
        String username = myIntent.getStringExtra("username");
        EditText budgetText = findViewById(R.id.budgetText);
        editor.putString(username + "_budget", budgetText.getText().toString());
        editor.apply();
        // go to expense list activity
        Intent nextIntent = new Intent(DashboardActivity.this, ExpenseListActivity.class);
        nextIntent.putExtra("username", username);
        startActivity(nextIntent);
    }
}