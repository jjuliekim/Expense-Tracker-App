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

import java.util.ArrayList;

public class AddExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_expense);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // send back to dashboard and update expense and remaining balance
    public void saveExpense(View view) {
        // get input
        EditText expenseText = findViewById(R.id.name);
        EditText amountText = findViewById(R.id.amount);

        // check for valid input
        if (expenseText.getText().toString().isEmpty() || amountText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
            expenseText.setText("");
            amountText.setText("");
            return;
        }

        try {
            Double.parseDouble(amountText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid Budget", Toast.LENGTH_SHORT).show();
            amountText.setText("");
            amountText.setHint("0.00");
            return;
        }

        Expense myExpense = new Expense(expenseText.getText().toString(), Double.parseDouble(amountText.getText().toString()));

        Intent myIntent = getIntent();
        String username = myIntent.getStringExtra("username");
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // update expense and balance
        double expenseSum = 0.0;
        ArrayList<Expense> expenseList = JsonManager.loadExpenses(this, username);
        for (Expense item : expenseList) {
            expenseSum += item.getExpenseAmt();
        }
        double totalExpense = myExpense.getExpenseAmt() + expenseSum;
        String setBudget = sharedPreferences.getString(username + "_budget", "0.0");
        double balance = Double.parseDouble(setBudget) - myExpense.getExpenseAmt();
        editor.putString(username + "_balance", String.valueOf(balance));
        editor.apply();

        expenseList.add(myExpense);
        JsonManager.saveExpenses(this, expenseList, username);

        Log.d("DEBUG", "currExpense: " + expenseSum + " newExpense: " + totalExpense + " setBudget: " + setBudget + " balance: " + balance);

        // go back to dashboard
        Intent nextIntent = new Intent(AddExpenseActivity.this, DashboardActivity.class);
        nextIntent.putExtra("username", username);
        startActivity(nextIntent);
    }
}