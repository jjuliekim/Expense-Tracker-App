package com.example.kim_j_project2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
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

        // set dashboard objects
        updateDashboard();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDashboard();
    }

    private void updateDashboard() {
        // retrieve stored information
        Intent myIntent = getIntent();
        String username = myIntent.getStringExtra("username");
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String budget = sharedPreferences.getString(username + "_budget", "0.0");

        // load expense list details
        double expenseSum = 0.0;
        ArrayList<Expense> expenseList;
        if (sharedPreferences.contains(username + "_expenseList")) {
            expenseList = JsonManager.loadExpenses(this, username);
            for (Expense item : expenseList) {
                expenseSum += item.getExpenseAmt();
            }
        } else {
            expenseList = new ArrayList<>();
            expenseSum = 0.0;
            JsonManager.saveExpenses(this, expenseList, username);
        }
        Log.i("Debug", "number of expense items: " + expenseList.size());
        Log.i("LoadDashboardDebug", "budget: " + budget + " expense: " + expenseSum);

        // set dashboard texts
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText(String.format("Welcome, %s!", username));
        if (!Objects.equals(budget, "0.0")) {
            EditText budgetText = findViewById(R.id.budgetText);
            budgetText.setText(budget);
        }
        TextView expenseText = findViewById(R.id.totalExpText);
        expenseText.setText(String.valueOf(expenseSum));
        TextView balanceText = findViewById(R.id.balanceText);
        double balance = Double.parseDouble(budget) - expenseSum;
        balanceText.setText(String.valueOf(Math.round(balance * 100.0) / 100.0));
    }

    // send to add expense activity
    public void addExpense(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Intent myIntent = getIntent();
        String username = myIntent.getStringExtra("username");

        // save budget input
        EditText budgetText = findViewById(R.id.budgetText);
        String budget = budgetText.getText().toString();
        try {
            Integer.parseInt(budget);
            budget = budget + ".0";
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid Budget", Toast.LENGTH_SHORT).show();
            budgetText.setText("");
            return;
        }
        try {
            Double.parseDouble(budget);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid Budget", Toast.LENGTH_SHORT).show();
            budgetText.setText("");
            return;
        }
        editor.putString(username + "_budget", budget);
        editor.apply();

        // go to add expense activity
        Intent nextIntent = new Intent(DashboardActivity.this, AddExpenseActivity.class);
        nextIntent.putExtra("username", username);
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