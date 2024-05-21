package com.example.kim_j_project2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ExpenseDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expense_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setEditText();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEditText();
    }

    // set edit text objects
    private void setEditText() {
        // original expense information
        Intent myIntent = getIntent();
        String ogName = myIntent.getStringExtra("ogName");
        double ogAmt = myIntent.getDoubleExtra("ogAmt", 0.00);

        // set the original text
        EditText nameText = findViewById(R.id.name);
        EditText amountText = findViewById(R.id.amount);
        nameText.setText(ogName);
        amountText.setText(String.valueOf(ogAmt));
    }

    // update expense (button clicked)
    public void updateExpense(View view) {
        Intent myIntent = getIntent();
        String username=  myIntent.getStringExtra("username");

        EditText nameText = findViewById(R.id.name);
        EditText amountText = findViewById(R.id.amount);

        try {
            Double.parseDouble(amountText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid Budget", Toast.LENGTH_SHORT).show();
            amountText.setText(String.valueOf(myIntent.getDoubleExtra("ogAmt", 0.00)));
            return;
        }

        ArrayList<Expense> expenseList = JsonManager.loadExpenses(this, username);
        Expense expense = null;
        for (Expense item : expenseList) {
            if (item.getExpenseName().equals(nameText.getText().toString())) {
                expense = item;
            }
        }
        expense.setExpenseName(nameText.getText().toString());
        expense.setExpenseAmt(Double.parseDouble(amountText.getText().toString()));
        JsonManager.saveExpenses(this, expenseList, username);

        // go back to dashboard
        Intent nextIntent = new Intent(ExpenseDetailsActivity.this, DashboardActivity.class);
        nextIntent.putExtra("username", username);
        startActivity(nextIntent);
    }
}