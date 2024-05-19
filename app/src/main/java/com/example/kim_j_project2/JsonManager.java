package com.example.kim_j_project2;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonManager {

    // save list to json and shared prefs
    public static void saveExpenses(Context context, ArrayList<Expense> expenseList, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonList = gson.toJson(expenseList);
        editor.putString(username + "_expenseList", jsonList);
        editor.apply();
    }

    // load list from shared prefs json
    public static ArrayList<Expense> loadExpenses(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(username + "_expenseList", null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Expense>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
