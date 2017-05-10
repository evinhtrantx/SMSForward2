package com.thepinesoft.smsforward;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

import com.thepinesoft.smsforward.global.Autowired;

import static android.preference.PreferenceManager.*;

/**
 * Created by FRAMGIA\tran.xuan.vinh on 29/03/2017.
 */

public class MyApplication extends Application {
    private static MyApplication thiz;
    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();
        thiz = this;
        Autowired.setContext(context);
    }

    public static MyApplication getApplication() {
        return thiz;
    }
    public SharedPreferences getApplicationPreferences(){
        return
                getDefaultSharedPreferences(this);

    }

}
