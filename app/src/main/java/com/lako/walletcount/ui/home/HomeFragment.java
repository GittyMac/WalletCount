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

import com.lako.walletcount.AddFundsSheet;
import com.lako.walletcount.R;
import com.lako.walletcount.SpendFundsSheet;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private String text;
    private TextView amount;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    public View onCreateView(@NonNull LayoutInflater inflater,
     ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        amount = root.findViewById(R.id.textView);
        final Button addFundsButton = root.findViewById(R.id.button2);
        final Button spendFundsButton = root.findViewById(R.id.button3);
        addFundsButton.setOnClickListener(v -> {
            AddFundsSheet addFundsDialog = new AddFundsSheet();
            addFundsDialog.show(requireActivity().getSupportFragmentManager(), "addFundsDialogBox");
        });
        spendFundsButton.setOnClickListener(v -> {
            SpendFundsSheet  spendFundsDialog = new SpendFundsSheet();
            spendFundsDialog.show(requireActivity().getSupportFragmentManager(), "spendFundsDialogBox");
        });
        loadData();
        updateViews();
        return root;
    }

    public void loadData(){
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "0.00");
    }

    public void updateViews(){
        double amountDouble = 0.00;
        try {
            amountDouble = Objects.requireNonNull(NumberFormat.getInstance().parse(text.replaceAll("[^\\d.,-]", ""))).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        amount.setText(NumberFormat.getCurrencyInstance().format(amountDouble));
    }
}