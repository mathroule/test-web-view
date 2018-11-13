package com.mathroule.testwebview;

import android.app.Application;

import com.good.gd.GDAndroid;
import com.mathroule.testwebview.activity.GDStateListenerImpl;

import timber.log.Timber;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        GDAndroid.getInstance().setGDStateListener(new GDStateListenerImpl());

        GDAndroid.getInstance().applicationInit(this);
    }
}
