package com.example.kim_j_project2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        Expense myExpense = new Expense(expenseText.getText().toString(), Double.parseDouble(amountText.getText().toString()));

        Intent myIntent = getIntent();
        String username = myIntent.getStringExtra("username");
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // update expense and balance
        String currExpense = sharedPreferences.getString(username + "_expense", "0.0");
        double totalExpense = myExpense.getExpenseAmt() + Double.parseDouble(currExpense);
        String currBudget = sharedPreferences.getString(username + "_budget", "0.0");
        double balance = Double.parseDouble(currBudget) - myExpense.getExpenseAmt();
        Log.d("DEBUG", "currExpense: " + currExpense + " newExpense: " + totalExpense + " currBudget: " + currBudget + " balance: " + balance);
        editor.putString(username + "_expense", String.valueOf(totalExpense));
        editor.putString(username + "_balance", String.valueOf(balance));

        // save expense to list

        // go back to dashboard
        editor.apply();
        Intent nextIntent = new Intent(AddExpenseActivity.this, DashboardActivity.class);
        nextIntent.putExtra("username", username);
        startActivity(nextIntent);
    }
}