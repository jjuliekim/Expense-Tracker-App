package com.example.kim_j_project2;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class Expense {
    private String expenseName;
    private double expenseAmt;

    // constructor
    public Expense(String expenseName, double expenseAmt) {
        this.expenseName = expenseName;
        this.expenseAmt = expenseAmt;
    }

    // getters and setters
    public String getExpenseName() {
        return expenseName;
    }

    public double getExpenseAmt() {
        return expenseAmt;
    }

    public void setExpenseName(String name) {
        expenseName = name;
    }

    public void setExpenseAmt(double amount) {
        expenseAmt = amount;
    }
}