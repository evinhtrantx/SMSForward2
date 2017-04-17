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
        pullEmailService.setHost("pop.gmail.com");
        pullEmailService.setUsername("never.use.this.pls@gmail.com");
        pullEmailService.setPassword("3dUP0W07dQ");
        pullEmailService.setPort("995");
        pullEmailService.setProtocol("pop3s");
        ServiceErrorCode ret = pullEmailService.execute(null);
        Assert.assertEquals(ServiceErrorCode.OK,ret);
    }
}
