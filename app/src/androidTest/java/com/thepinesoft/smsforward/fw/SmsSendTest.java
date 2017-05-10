package com.thepinesoft.smsforward.fw;

import android.support.test.runner.AndroidJUnit4;
import android.telephony.SmsManager;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by vtran on 10/5/17.
 */
@RunWith(AndroidJUnit4.class)
public class SmsSendTest {
    @Test
    public final void smsSendTest() {
        String toNo = "+84904240255";
        String msg = "This is a test message";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(toNo, null, msg, null, null);
    }

}

