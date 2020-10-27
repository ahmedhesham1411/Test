package com.uriallab.haat.haat.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsListener extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();

        Object[] pdus = (Object[]) data.get("pdus");

        for (int i = 0; i < pdus.length; i++) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            String sender = smsMessage.getDisplayOriginatingAddress();
            //ToDo check your sender
            String messageBody = smsMessage.getDisplayMessageBody();

            Intent updateTokenIntent = new Intent("UPDATE_OTP");
            updateTokenIntent.putExtra("msg", getVerificationCode(messageBody));
            context.sendBroadcast(updateTokenIntent);
        }

    }

    private String getVerificationCode(String message) {
        if (message == null) {
            return null;
        }

//        int index = message.indexOf("code");
        int index_last_length = message.indexOf(" ");
//        if (index != -1) {
//            int start = index + 3;
//            return message.substring(index_last_length);
//        }

        return message.substring(index_last_length);
    }

}