package com.example.task1_api_transactions.application;

import android.app.Application;

import com.example.task1_api_transactions.BuildConfig;

import dagger.hilt.android.HiltAndroidApp;
import timber.log.Timber;

@HiltAndroidApp
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // planted timber for debug build
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
