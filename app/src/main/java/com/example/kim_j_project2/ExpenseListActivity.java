package com.example.kim_j_project2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;


public class ExpenseListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expense_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadList();
    }

    // display list of expenses
    private void loadList() {
        Intent myIntent = getIntent();
        String username = myIntent.getStringExtra("username");
        Intent nextIntent = new Intent(ExpenseListActivity.this, ExpenseDetailsActivity.class);
        nextIntent.putExtra("username", username);

        // load expense list and use custom adapter
        ArrayList<Expense> expenseList = JsonManager.loadExpenses(this, username);
        ListView listView = findViewById(R.id.listView);
        ExpenseAdapter adapter = new ExpenseAdapter(this, expenseList);
        listView.setAdapter(adapter);

        // set on click listener
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Expense expense = (Expense) parent.getItemAtPosition(position);
            nextIntent.putExtra("ogName", expense.getExpenseName());
            nextIntent.putExtra("ogAmt", expense.getExpenseAmt());
            startActivity(nextIntent);
        });
    }
}