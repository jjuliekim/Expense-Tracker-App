package com.example.kim_j_project2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<Expense> {

    public ExpenseAdapter(Context context, List<Expense> expenseList) {
        super(context, 0, expenseList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Expense expense = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.expense_item, parent, false);

        TextView nameText = convertView.findViewById(R.id.exp_name);
        TextView amtText = convertView.findViewById(R.id.exp_amt);
        nameText.setText(expense.getExpenseName());
        amtText.setText(String.format("$%.2f", expense.getExpenseAmt()));

        return convertView;
    }
}
