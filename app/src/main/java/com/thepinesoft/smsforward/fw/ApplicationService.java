package com.thepinesoft.smsforward.fw;

import android.content.ContentValues;

/**
 * Created by FRAMGIA\tran.xuan.vinh on 07/04/2017.
 */

public interface ApplicationService {
    public ServiceErrorCode execute(ContentValues params);
}
