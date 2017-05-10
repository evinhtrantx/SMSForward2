package com.thepinesoft.smsforward.fw;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import com.thepinesoft.smsforward.global.Autowired;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by FRAMGIA\tran.xuan.vinh on 17/04/2017.
 */

@RunWith(AndroidJUnit4.class)
public final class GenerateSampleDbData {
    @Test
    public final void genDBSampleData(){
        SQLiteDatabase smsDb = Autowired.getMsgDatabase();
        if(smsDb == null){
            return;
        }
        smsDb.beginTransaction();
        ContentValues cv = new ContentValues();
        cv.put("content", "");
        cv.put("fr_no","TEST");
        //cv.put("to_no","+84904240255");
        cv.put("to_no","+841654240685");
        cv.put("fr_email","never.use.this.pls@gmail.com");
        cv.put("to_email","never.use.this.pls+to@gmail.com");
        cv.put("status","A");
        smsDb.insert("msg", null, cv);
        smsDb.setTransactionSuccessful();
        smsDb.endTransaction();
    }
}
