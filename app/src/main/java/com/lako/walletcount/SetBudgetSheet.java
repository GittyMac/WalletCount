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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class SetBudgetSheet extends BottomSheetDialogFragment {
    private TextInputEditText inputEditText;
    private TextView budget;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "textbudget";
    public static final String OGTEXT = "originalbudget";
    public static final String MONTH = "monthOfBudget";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setStyle(STYLE_NORMAL, R.style.BottomSheet);
        View view = inflater.inflate(R.layout.budget_bottomsheet, container, false);
        inputEditText = view.findViewById(R.id.inputtext2);
        Button setBudget = view.findViewById(R.id.button6);
        budget = requireActivity().findViewById(R.id.textView7);
        inputEditText.requestFocus();
        requireDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        setBudget.setOnClickListener(v -> {
            if(Objects.requireNonNull(inputEditText.getText()).toString().length() == 0){
                inputEditText.setText("0");
            }
            try {
                double num1 = 0;
                try {
                    num1 = Objects.requireNonNull(NumberFormat.getInstance(Locale.getDefault()).parse(inputEditText.getText().toString().replaceAll("[^\\d.,-]", ""))).doubleValue();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                budget.setText(NumberFormat.getCurrencyInstance().format(num1));
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat month_date = new SimpleDateFormat("MMMM", Locale.getDefault());
                String month_name = month_date.format(cal.getTime());
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(TEXT, budget.getText().toString());
                editor.putString(OGTEXT, budget.getText().toString());
                editor.putString(MONTH, month_name);
                editor.apply();
                dismiss();
            }catch(NumberFormatException exception){
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
