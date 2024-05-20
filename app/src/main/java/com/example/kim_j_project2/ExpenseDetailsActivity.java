package com.example.kim_j_project2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        // get information
//        Intent myIntent = getIntent();
//        Expense expense = myIntent.getStringExtra();

        EditText nameText = findViewById(R.id.name);
        EditText amountText = findViewById(R.id.amount);

        try {
            Double.parseDouble(amountText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid Budget", Toast.LENGTH_SHORT).show();
//            amountText.setText(String.valueOf(expense.getAmount()));
            return;
        }

//        String updatedName =

        // if nothing inputted, keep the same information

    }

    // update and save expense from user input
    public void updateExpense(View view) {

    }
}