package com.lako.walletcount.expenses;

public class Expense {
    private String expenseName;
    private double expenseCost;
    private boolean isExpensePay;

    public Expense(String n, double c, boolean p)
    {
        expenseName = n;
        expenseCost = c;
        isExpensePay = p;
    }

    public String getExpenseName()
    {
        return expenseName;
    }

    public double getExpenseCost()
    {
        return expenseCost;
    }

    public boolean getIsExpensePay()
    {
        return isExpensePay;
    }
}
