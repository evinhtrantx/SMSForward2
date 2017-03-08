package com.thepinesoft.smsforward.fw;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thepinesoft.smsforward.global.Autowired;

/**
 * Created by vtran on 8/3/17.
 */

public class SmsDb extends SQLiteOpenHelper{
    public SmsDb(){
        super(Autowired.getContext(),"SMSFORWARDDB",null,0);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlcreate = "CREATE TABLE msg(id INTEGER PRIMARY KEY AUTOINCREMENT, content VARCHAR(255), fr_no VARCHAR(50), " +
                " to_no VARCHAR(50), fr_email VARCHAR(50), to_email VARCHAR(50), status CHAR(1))";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
