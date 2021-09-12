package com.lako.walletcount;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Objects;

public class SpendFundsSheet extends BottomSheetDialogFragment {
    private TextInputEditText fundsToRemove;
    private TextView amount;
    private String textBudget;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String TEXTBUDGET = "textbudget";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setStyle(STYLE_NORMAL, R.style.BottomSheet);
        View view = inflater.inflate(R.layout.spend_funds_bottomsheet, container, false);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        Button spendFunds = view.findViewById(R.id.button4);
        amount = requireActivity().findViewById(R.id.textView);
        fundsToRemove = view.findViewById(R.id.inputText);
        fundsToRemove.requestFocus();
        requireDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        textBudget = sharedPreferences.getString(TEXTBUDGET, "0.00");
        spendFunds.setOnClickListener(v -> {
            if(Objects.requireNonNull(fundsToRemove.getText()).toString().length() == 0){ fundsToRemove.setText("0"); }
            try {
                double num1 = Objects.requireNonNull(NumberFormat.getInstance().parse(fundsToRemove.getText().toString().replaceAll("[^\\d.,-]", ""))).doubleValue();
                double num2 = Objects.requireNonNull(NumberFormat.getInstance().parse(amount.getText().toString().replaceAll("[^\\d.,-]", ""))).doubleValue();
                double num3 = Objects.requireNonNull(NumberFormat.getInstance().parse(textBudget.replaceAll("[^\\d.,-]", ""))).doubleValue();
                double budgetSum = num3 - num1;
                double sum = num2 - num1;
                textBudget = NumberFormat.getCurrencyInstance().format(budgetSum);
                amount.setText(NumberFormat.getCurrencyInstance().format(sum));
                SharedPreferences sharedPreferences1 = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putString(TEXT, amount.getText().toString());
                editor.putString(TEXTBUDGET, textBudget);
                editor.apply();
                dismiss();
            }catch(NumberFormatException | ParseException exception){
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Error")
                        .setMessage("Invalid number was entered.")
                        .setNeutralButton("OK", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        return view;
    }
}