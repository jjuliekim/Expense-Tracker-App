package com.example.kim_j_project2;

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
}
