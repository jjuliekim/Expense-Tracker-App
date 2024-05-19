package com.example.kim_j_project2;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class Expense {
    private final String expenseName;
    private final double expenseAmt;

    // constructor
    public Expense(String expenseName, double expenseAmt) {
        this.expenseName = expenseName;
        this.expenseAmt = expenseAmt;
    }

    // getters
    public String getExpenseName() {
        return expenseName;
    }

    public double getExpenseAmt() {
        return expenseAmt;
    }

    // json load/save
    public static void saveExpenses(Context context, ArrayList<Expense> expense, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonList = gson.toJson(expense);
        editor.putString(username + "_expenseList", jsonList);
        editor.apply();
    }

    public static ArrayList<Expense> loadExpenses(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(username + "_expenseList", null);
        Type type = new TypeToken<ArrayList<Expense>>() {}.getType();
        return gson.fromJson(json, type);
    }
}