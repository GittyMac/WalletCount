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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.lako.walletcount.AddFundsSheet;
import com.lako.walletcount.R;
import com.lako.walletcount.SpendFundsSheet;

public class HomeFragment extends Fragment {

    private String text;
    private TextView amount;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        amount = root.findViewById(R.id.textView);
        final Button addFundsButton = root.findViewById(R.id.button2);
        final Button spendFundsButton = root.findViewById(R.id.button3);
        addFundsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFundsSheet addFundsDialog = new AddFundsSheet();
                addFundsDialog.show(getActivity().getSupportFragmentManager(), "addFundsDialogBox");
            }
        });
        spendFundsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpendFundsSheet  spendFundsDialog = new SpendFundsSheet();
                spendFundsDialog.show(getActivity().getSupportFragmentManager(), "spendFundsDialogBox");
            }
        });
        loadData();
        updateViews();
        return root;
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        text = sharedPreferences.getString(TEXT, "0.00");
    }

    public void updateViews(){
        amount.setText(text);
    }
}
