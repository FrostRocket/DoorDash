package com.frostrocket.doordash;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.frostrocket.doordash.utils.AppSharedPreferences;

import timber.log.Timber;

public class DoorDash extends Application {
    private static DoorDash instance;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        context = getApplicationContext();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static synchronized DoorDash getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return context;
    }

    public static SharedPreferences getAppSharedPreferences() {
        return getAppContext().getSharedPreferences(AppSharedPreferences.class.getName(), Context.MODE_PRIVATE);
    }
}