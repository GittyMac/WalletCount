package com.lako.walletcount.expenses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lako.walletcount.R;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {
    private ArrayList<Expense> expenses;

    public ExpenseAdapter(ArrayList<Expense> expenses)
    {
        this.expenses = expenses;
    }

    public class ExpenseViewHolder extends  RecyclerView.ViewHolder
    {
        private TextView expenseTitle;
        private TextView expenseCost;

        public ExpenseViewHolder(final View view)
        {
            super(view);
            expenseTitle = view.findViewById(R.id.expense_title);
            expenseCost = view.findViewById(R.id.expense_cost);
        }
    }

    @NonNull
    @Override
    public ExpenseAdapter.ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item, parent, false);
        return new ExpenseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        String expenseTitle = expenses.get(position).getExpenseName();
        double expenseCost = expenses.get(position).getExpenseCost();

        holder.expenseTitle.setText(expenseTitle);
        if(expenses.get(position).getIsExpensePay())
        {
            holder.expenseCost.setText("-" + expenseCost);
        }else
        {
            holder.expenseCost.setText("+" + expenseCost);
        }
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }
}
