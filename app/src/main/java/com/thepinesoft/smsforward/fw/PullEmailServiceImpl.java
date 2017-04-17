package com.thepinesoft.smsforward.fw;


import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sun.mail.pop3.POP3Folder;
import com.sun.mail.pop3.POP3Store;
import com.thepinesoft.smsforward.MyApplication;
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

public class PullEmailServiceImpl extends IntentService  implements ApplicationService{
    public PullEmailServiceImpl(){
        super("PullEmailService");
    }

    public int getMaxMailDownload() {
        return maxMailDownload;
    }

    public void setMaxMailDownload(int maxMailDownload) {
        this.maxMailDownload = maxMailDownload;
    }

    private int maxMailDownload;

    private String host;
    private String username;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    private String protocol;
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    private String port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        execute(null);
    }

    private enum Type {
        POP3, IMAP
    }

        private SQLiteDatabase smsDb;
    private Type type;


        private final String messageTitleExtractor = "(\\+*[0-9]+)\\s+(.*)";
    private final Pattern extractor = Pattern.compile(messageTitleExtractor);

    @Override
    public ServiceErrorCode execute(ContentValues params) {
        smsDb = Autowired.getMsgDatabase();
        if(maxMailDownload <=0){
            maxMailDownload = 50;
        }
        MyApplication app;
        try {
            app = (MyApplication) getApplication();
            if (app == null) {
                app = MyApplication.getApplication();
            }
        }catch(ClassCastException ex){
            return ServiceErrorCode.cannot_get_app_reference;
        }
        SharedPreferences prefs =  app.getApplicationPreferences();
        if(host == null){
            host = prefs.getString("mail.store.host",null);
        }
        if(username == null){
            username = prefs.getString("mail.store.user",null);
        }
        if(protocol == null){
            protocol =prefs.getString("mail.store.protocol","pop3s");
        }
        if(password == null){
            password = prefs.getString("mail.store.password",null);
        }
        Properties props = new Properties();
        props.setProperty("mail.pop3.host", host);
        props.setProperty("mail.pop3.user", username);
        props.setProperty("mail.store.protocol",protocol);

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
            store = (POP3Store) session.getStore("pop3");
            store.connect();
        } catch (NoSuchProviderException e) {
            return ServiceErrorCode.no_mail_provider;
        } catch (ClassCastException e) {
            return ServiceErrorCode.inbox_folder_not_found;
        } catch (MessagingException e) {
            return ServiceErrorCode.mail_connection_error;
        }
        POP3Folder inbox;
        try {
            inbox = (POP3Folder) store.getDefaultFolder();
            inbox.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
            int msgCount = inbox.getSize();
            for (int i = 0; i < msgCount && i < maxMailDownload; ++i) {
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
            return ServiceErrorCode.mail_exception;
        }

        return ServiceErrorCode.OK;
    }

}
