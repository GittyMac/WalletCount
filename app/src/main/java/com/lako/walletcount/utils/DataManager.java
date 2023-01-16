package com.lako.walletcount.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.lako.walletcount.WalletCountApplication;

public class DataManager {

    private SharedPreferences sharedPreferences;
    private String legacyMoney = "0.00";
    private double money = 0;
    private double budget = 0;

    public DataManager()
    {
        sharedPreferences = WalletCountApplication.getInstance().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
    }

    private void loadData()
    {
        legacyMoney = sharedPreferences.getString("text", "0.00");
        money = sharedPreferences.getFloat("money", 0);
        budget = sharedPreferences.getFloat("budget", 0);
    }

    private void localizeData()
    {
        //TODO - Localization with custom currency settings. Symbol and type (period,comma,nothing).
    }
}
