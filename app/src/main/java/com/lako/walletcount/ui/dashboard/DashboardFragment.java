package com.lako.walletcount.ui.dashboard;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.lako.walletcount.R;
import com.lako.walletcount.RecyclerView;
import com.lako.walletcount.SetBudgetSheet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private Button setBudget;
    private TextView message1;
    private TextView message2;
    private TextView amountLeft;
    private ProgressBar progressBar;
    private String textbudget;
    private String lastMonth;
    private String curMonth;
    private String defbudget;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "textbudget";
    public static final String MONTH = "monthOfBudget";
    public static final String OGTEXT = "originalbudget";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        amountLeft = root.findViewById(R.id.textView7);
        message1 = root.findViewById(R.id.textView8);
        message2 = root.findViewById(R.id.textView9);
        setBudget = root.findViewById(R.id.button5);
        progressBar = root.findViewById(R.id.progressBar2);
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        curMonth = month_date.format(cal.getTime());

        setBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetBudgetSheet setBudgetSheet = new SetBudgetSheet();
                setBudgetSheet.show(getActivity().getSupportFragmentManager(), "setBudgetSheetLog");
            }
        });

        loadData();

        double num1 = Double.parseDouble(textbudget);
        double num2 = Double.parseDouble("0.00");
        if(num1<0){
        message1.setText("We can work on that.");
        message2.setText("You went over your budget!");
        }else {
            message1.setText("Good work!");
            message2.setText("You are under your budget!");
        }
        if (lastMonth != "None"){
            if(!curMonth.equals(lastMonth)) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(TEXT, defbudget);
                editor.putString(MONTH, curMonth);
                editor.apply();
                loadData();
            }
        }
        updateViews();


        return root;
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        textbudget = sharedPreferences.getString(TEXT, "0.00");
        lastMonth = sharedPreferences.getString(MONTH, "None");
        defbudget = sharedPreferences.getString(OGTEXT, "0.00");
    }

    public void updateViews(){
        amountLeft.setText(textbudget);
        double defb = Double.parseDouble(defbudget);
        double txtb = Double.parseDouble(textbudget);
        int txte = (int)Math.round(txtb);
        int defe = (int)Math.round(defb);
        progressBar.setMax(defe);
        progressBar.setProgress(txte);
    }
}
