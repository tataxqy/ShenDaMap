package com.example.aces.shendamap.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Aces on 2018/6/27.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
