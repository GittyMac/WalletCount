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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SetBudgetSheet extends BottomSheetDialogFragment {
    private Button setBudget;
    private TextInputEditText inputEditText;
    private TextView budget;

    private String textbudget;

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
        setBudget = view.findViewById(R.id.button6);
        budget = getActivity().findViewById(R.id.textView7);
        setBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputEditText.getText().toString().length() == 0){
                    inputEditText.setText("0");
                }
                double num1 = Double.parseDouble(inputEditText.getText().toString());
                double num2 = Double.parseDouble(budget.getText().toString());
                double sum = num1 + num2;
                budget.setText(String.format("%.2f",sum));
                Calendar cal=Calendar.getInstance();
                SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                String month_name = month_date.format(cal.getTime());
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(TEXT, budget.getText().toString());
                editor.putString(OGTEXT, budget.getText().toString());
                editor.putString(MONTH, month_name);
                editor.apply();
            }
        });
        return view;
    }
}
