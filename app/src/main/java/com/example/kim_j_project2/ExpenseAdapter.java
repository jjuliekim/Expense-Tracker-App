package com.example.kim_j_project2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpenseAdapter extends ArrayAdapter<Expense> {
    public ExpenseAdapter(Context context, ArrayList<Expense> expenses) {
        super(context, 0, expenses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Expense expense = getItem(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_expense, parent, false);
        TextView nameText = convertView.findViewById(R.id.expenseName);
        TextView amountText = convertView.findViewById(R.id.expenseAmt);
        nameText.setText(expense.getExpenseName());
        amountText.setText(String.valueOf(expense.getExpenseAmt()));

        return convertView;
    }
}
