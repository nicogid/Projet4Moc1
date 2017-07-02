package com.example.gidon.myrealm;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by gidon on 26/06/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
