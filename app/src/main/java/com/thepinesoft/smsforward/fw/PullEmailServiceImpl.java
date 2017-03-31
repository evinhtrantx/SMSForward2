package com.thepinesoft.smsforward.fw;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.util.Log;

import com.sun.mail.imap.IMAPBodyPart;
import com.sun.mail.pop3.POP3Folder;
import com.sun.mail.pop3.POP3Store;
import com.thepinesoft.smsforward.global.Autowired;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 * Created by FRAMGIA\tran.xuan.vinh on 28/03/2017.
 */

public class PullEmailServiceImpl {
    private String host;
    private String username;
    private String password;

    private enum Type {
        POP3, IMAP
    }

    ;
    private SQLiteDatabase smsDb;
    private Type type;

    public enum ErrorCode {
        no_mail_provider,
        inbox_folder_not_found,
        mail_exception,
        OK,
    }

    ;
    private final String messageTitleExtractor = "(\\+*[0-9]+)\\s+(.*)";
    private final Pattern extractor = Pattern.compile(messageTitleExtractor);

    public ErrorCode pull() {
        smsDb = Autowired.getMsgDatabase();
        Properties props = new Properties();
        props.setProperty("mail.pop3.host", host);
        props.setProperty("mail.pop3.user", username);
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                PasswordAuthentication passwordAuthentication = new PasswordAuthentication(username, password);
                return passwordAuthentication;
            }
        };
        Session session = Session.getDefaultInstance(props, authenticator);
        POP3Store store;
        try {
            store = (POP3Store) session.getStore();
        } catch (NoSuchProviderException e) {
            return ErrorCode.no_mail_provider;
        } catch (ClassCastException e) {
            return ErrorCode.inbox_folder_not_found;
        }
        POP3Folder inbox;
        try {
            inbox = (POP3Folder) store.getDefaultFolder();
            inbox.open(Folder.READ_WRITE);
            int msgCount = inbox.getSize();
            for (int i = 0; i < msgCount; ++i) {
                Message message = inbox.getMessage(i);
                if (message.isExpunged()) continue;
                String subject = message.getSubject();//number message
                if (subject != null) {
                    Matcher matcher = extractor.matcher(subject);
                    if (matcher.find()) {
                        String toNo = matcher.group(1).trim();
                        String msg = matcher.group(2).trim();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("to_no", toNo);
                        contentValues.put("status", SmsDb.STATUS_UNREAD);//unread
                        contentValues.put("content", msg);
                        long insertedCount = smsDb.insert(SmsDb.TABLE_NAME, null, contentValues);
                        if (insertedCount != 1) {
                            Log.e("PullEmailServiceImpl", "cannot insert record:" + subject);
                        }
                    }
                }
            }
            inbox.close(true);
        } catch (MessagingException e) {
            return ErrorCode.mail_exception;
        }

        return ErrorCode.OK;
    }

}
