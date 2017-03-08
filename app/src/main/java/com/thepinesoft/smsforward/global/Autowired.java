package com.thepinesoft.smsforward.global;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by vtran on 8/3/17.
 */

public abstract class Autowired {
    private static Context context = null;
    public static Context getContext(){
        return context;
    }
    public static void setContext(Context c){
        if(context != null)
            context = c;
    };
    public static SQLiteDatabase getMsgDatabase(){
        return null;
    }

}
