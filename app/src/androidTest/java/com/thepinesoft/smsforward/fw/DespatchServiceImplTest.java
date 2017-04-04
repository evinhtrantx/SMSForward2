package com.thepinesoft.smsforward.fw;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by FRAMGIA\tran.xuan.vinh on 04/04/2017.
 */

@RunWith(AndroidJUnit4.class)
public class DespatchServiceImplTest {
    @Test
    public void testDespatch(){
        DespatchServiceImpl service = new DespatchServiceImpl();
        ServiceErrorCode errorCode = service.execute(null);
        Assert.assertEquals(ServiceErrorCode.OK,errorCode);
    }

}
