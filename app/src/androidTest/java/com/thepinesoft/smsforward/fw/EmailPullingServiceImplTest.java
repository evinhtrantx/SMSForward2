package com.thepinesoft.smsforward.fw;


import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by FRAMGIA\tran.xuan.vinh on 04/04/2017.
 */
@RunWith(AndroidJUnit4.class)
public class EmailPullingServiceImplTest {
    @Test
    public void testExecute() {
        PullEmailServiceImpl pullEmailService = new PullEmailServiceImpl();
        pullEmailService.setHost("192.168.5.50");
        pullEmailService.setUsername("never.use.this.pls@gmail.com");
        pullEmailService.setPassword("12345");
        pullEmailService.setPort("1100");
        pullEmailService.setProtocol("pop3");
        System.out.println("ABCD EFGH");
        ServiceErrorCode ret = pullEmailService.execute(null);
        Assert.assertEquals(ServiceErrorCode.OK,ret);
    }
}
