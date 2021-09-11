package com.lako.walletcount;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;

public class AddFundsSheet extends BottomSheetDialogFragment {
    private TextInputEditText fundsToAdd;
    private TextView amount;
    private Button addFunds;

    private String text;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setStyle(STYLE_NORMAL, R.style.BottomSheet);
        View view = inflater.inflate(R.layout.add_funds_bottomsheet, container, false);

        addFunds = view.findViewById(R.id.button);
        amount = getActivity().findViewById(R.id.textView);
        fundsToAdd = view.findViewById(R.id.textInputEditText2);
        fundsToAdd.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        addFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fundsToAdd.getText().toString().length() == 0){
                    fundsToAdd.setText("0");
                }
                try {
                    double num1 = Double.parseDouble(fundsToAdd.getText().toString().replaceAll(",", "."));
                    double num2 = Double.parseDouble(amount.getText().toString().replaceAll(",", "."));
                    double sum = num1 + num2;
                    amount.setText(String.format("%.2f", sum));
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(TEXT, amount.getText().toString());
                    editor.apply();
                }catch(NumberFormatException exception){
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Error")
                            .setMessage("Invalid number was entered.")
                            .setNeutralButton("OK", null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
        return view;
    }

}
