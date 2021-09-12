package com.lako.walletcount.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.lako.walletcount.R;
import com.lako.walletcount.SetBudgetSheet;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class DashboardFragment extends Fragment {

    private TextView amountLeft;
    private ProgressBar progressBar;
    private String textBudget;
    private String lastMonth;
    private String defBudget;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "textbudget";
    public static final String MONTH = "monthOfBudget";
    public static final String OGTEXT = "originalbudget";

    public View onCreateView(@NonNull LayoutInflater inflater,
     ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        amountLeft = root.findViewById(R.id.budget_AmountText);
        TextView message1 = root.findViewById(R.id.budget_feedbackHeading);
        TextView message2 = root.findViewById(R.id.budget_feedbackDetails);
        Button setBudget = root.findViewById(R.id.budget_adjustButton);
        progressBar = root.findViewById(R.id.budgetRing);
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM", Locale.getDefault());
        String curMonth = month_date.format(cal.getTime());

        setBudget.setOnClickListener(v -> {
            SetBudgetSheet setBudgetSheet = new SetBudgetSheet();
            setBudgetSheet.show(requireActivity().getSupportFragmentManager(), "setBudgetSheetLog");
        });

        loadData();

        if (!lastMonth.equals("None")){
            if(!curMonth.equals(lastMonth)) {
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(TEXT, defBudget);
                editor.putString(MONTH, curMonth);
                editor.apply();
                loadData();
            }
        }
        double num1 = 0;
        try {
            num1 = Objects.requireNonNull(NumberFormat.getInstance(Locale.getDefault()).parse((textBudget.replaceAll("[^\\d.,-]", "")))).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(num1<0){
        message1.setText(R.string.NegativeBudgetTitle);
        message2.setText(R.string.NegativeBudgetText);
        }else {
            message1.setText(R.string.PositiveBudgetTitle);
            message2.setText(R.string.PositiveBudgetText);
        }
        updateViews();


        return root;
    }

    public void loadData(){
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        textBudget = sharedPreferences.getString(TEXT, "0.00");
        lastMonth = sharedPreferences.getString(MONTH, "None");
        defBudget = sharedPreferences.getString(OGTEXT, "0.00");
    }

    public void updateViews(){
        double defB = 0;
        double txtB = 0;
        try {
            defB = Objects.requireNonNull(NumberFormat.getInstance().parse(defBudget.replaceAll("[^\\d.,-]", ""))).doubleValue();
            txtB = Objects.requireNonNull(NumberFormat.getInstance().parse(textBudget.replaceAll("[^\\d.,-]", ""))).doubleValue();
            amountLeft.setText(NumberFormat.getCurrencyInstance().format(txtB));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int txtE = (int)Math.round(txtB);
        int defE = (int)Math.round(defB);
        progressBar.setMax(defE);
        progressBar.setProgress(txtE);
    }
}
