package com.example.feedjungle.utils;

import android.app.Application;

    public class SharedPrefApplication extends Application {
    private static SharedPrefApplication _instance;

    @Override
    public void onCreate() {


        super.onCreate();
        _instance = this;

    }


    public static synchronized SharedPrefApplication getInstance() {
        return _instance;

    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
