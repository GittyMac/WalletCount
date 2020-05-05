package com.lako.walletcount;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

public class SpendFundsSheet extends BottomSheetDialogFragment {
    private TextInputEditText fundsToRemove;
    private TextView amount;
    private Button spendFunds;
    private TextView budget;

    private String text;
    private String textbudget;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String TEXTBUDGET = "textbudget";
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setStyle(STYLE_NORMAL, R.style.BottomSheet);
        View view = inflater.inflate(R.layout.spend_funds_bottomsheet, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        spendFunds = view.findViewById(R.id.button4);
        amount = getActivity().findViewById(R.id.textView);
        budget = getActivity().findViewById(R.id.textView7);
        fundsToRemove = view.findViewById(R.id.inputText);
        textbudget = sharedPreferences.getString(TEXTBUDGET, "0.00");
        spendFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fundsToRemove.getText().toString().length() == 0){
                    fundsToRemove.setText("0");
                }
                double num1 = Double.parseDouble(fundsToRemove.getText().toString());
                double num2 = Double.parseDouble(amount.getText().toString());
                double num3 = Double.parseDouble(textbudget);
                double budgetsum = num3 - num1;
                double sum = num2 - num1;
                textbudget = String.format("%.2f", budgetsum);
                amount.setText(String.format("%.2f",sum));
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(TEXT, amount.getText().toString());
                editor.putString(TEXTBUDGET, textbudget);
                editor.apply();
            }
        });
        return view;
    }
}
