package com.lako.walletcount.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lako.walletcount.AddFundsSheet;
import com.lako.walletcount.R;
import com.lako.walletcount.SpendFundsSheet;
import com.lako.walletcount.expenses.Expense;
import com.lako.walletcount.expenses.ExpenseAdapter;

import java.lang.reflect.Array;
import java.sql.Struct;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private String text;
    private TextView amount;
    private RecyclerView expenseRecycler;

    private ArrayList<Expense> expenses;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    public View onCreateView(@NonNull LayoutInflater inflater,
     ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        amount = root.findViewById(R.id.home_amountText);

        //TODO - Use official M3 colors, instead of current hacky solution.
        //TODO - Make RecyclerView and Header scrollable at **once**, not separately.
        expenseRecycler = root.findViewById(R.id.expense_recycler);
        expenses = new ArrayList<>();

        final Button addFundsButton = root.findViewById(R.id.home_addButton);
        addFundsButton.setOnClickListener(v -> {
            AddFundsSheet addFundsDialog = new AddFundsSheet();
            addFundsDialog.show(requireActivity().getSupportFragmentManager(), "addFundsDialogBox");
        });

        final Button spendFundsButton = root.findViewById(R.id.home_payButton);
        spendFundsButton.setOnClickListener(v -> {
            SpendFundsSheet  spendFundsDialog = new SpendFundsSheet();
            spendFundsDialog.show(requireActivity().getSupportFragmentManager(), "spendFundsDialogBox");
        });

        loadData();
        setAdapter();
        getExpenses();
        updateViews();
        return root;
    }

    private void loadData(){
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "0.00");
    }

    private void updateViews(){
        double amountDouble = 0.00;
        try {
            amountDouble = Objects.requireNonNull(NumberFormat.getInstance().parse(text.replaceAll("[^\\d.,-]", ""))).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        amount.setText(NumberFormat.getCurrencyInstance().format(amountDouble));
    }

    private void setAdapter()
    {
        ExpenseAdapter adapter = new ExpenseAdapter(expenses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());

        expenseRecycler.setLayoutManager(layoutManager);
        //TODO - Potentially use custom animator?
        expenseRecycler.setItemAnimator(new DefaultItemAnimator());
        expenseRecycler.setAdapter(adapter);
    }

    private void getExpenses()
    {
        //TODO - Add saving/loading of expenses.

        //Placeholders
        expenses.add(new Expense("Test Pay", 10.00, true));
        expenses.add(new Expense("Test Add", 25.00, false));
        expenses.add(new Expense("Trip to mall", 250.50, true));
        expenses.add(new Expense("Winning!", 5.00, false));
        expenses.add(new Expense("Paycheck", 150.00, false));
        expenses.add(new Expense("Rent", 250.00, true));
    }
}