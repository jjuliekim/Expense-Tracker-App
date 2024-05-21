package com.example.kim_j_project2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

        // load expense list and use custom adapter
        ArrayList<Expense> expenseList = JsonManager.loadExpenses(this, username);
        ListView listView = findViewById(R.id.listView);
        ExpenseAdapter adapter = new ExpenseAdapter(this, expenseList);
        listView.setAdapter(adapter);

        // set on click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Expense clickedExpense = (Expense) parent.getItemAtPosition(position);
                Toast.makeText(ExpenseListActivity.this, "Clicked: " + clickedExpense.getExpenseName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}