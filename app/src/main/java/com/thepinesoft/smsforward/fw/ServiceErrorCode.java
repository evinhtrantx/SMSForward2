package com.thepinesoft.smsforward.fw;

/**
 * Created by FRAMGIA\tran.xuan.vinh on 31/03/2017.
 */

public enum ServiceErrorCode {
    OK,
    ERROR,
    no_mail_provider,
    inbox_folder_not_found,
    mail_exception,
    mail_connection_error,
    cannot_get_app_reference,
}
