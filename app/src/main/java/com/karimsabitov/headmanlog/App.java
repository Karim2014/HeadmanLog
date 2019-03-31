package com.karimsabitov.headmanlog;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by User on 13.09.2018.
 */

public class App extends Application {
    private static App sSelf;

    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        sSelf = this;
    }

    public static App self() {
        return sSelf;
    }
}