package com.lako.walletcount;

import android.app.Application;
import android.content.res.Configuration;

public class WalletCountApplication extends Application {
    private static WalletCountApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static WalletCountApplication getInstance()
    {
        return instance;
    }
}
