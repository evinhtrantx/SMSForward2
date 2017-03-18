package com.thepinesoft.smsforward.global;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thepinesoft.smsforward.fw.SmsDb;

import junit.framework.Assert;

/**
 * Created by vtran on 8/3/17.
 */

public abstract class Autowired {
    private static Context context = null;
    private static SQLiteDatabase db = null;
    public static Context getContext(){
        return context;
    }
    public static void setContext(Context c){
        if(context == null) {
            context = c;
        }
    };
    public static SQLiteDatabase getMsgDatabase(){
        if(db == null) {
            SQLiteOpenHelper helper = new SmsDb();
            db = helper.getWritableDatabase();
            if(db.isOpen()) return db;
            return  null;
        }else{
            if(db.isOpen()) return db;
            return null;
        }
    }

}
