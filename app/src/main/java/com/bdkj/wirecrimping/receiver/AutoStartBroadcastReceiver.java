package com.bdkj.wirecrimping.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.bdkj.wirecrimping.Constant;
import com.bdkj.wirecrimping.activity.LoginActivity;
import com.bdkj.wirecrimping.activity.MainActivity;
import com.bdkj.wirecrimping.util.SPUtil;

public class AutoStartBroadcastReceiver extends BroadcastReceiver {
    static final String action_boot = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(action_boot)) {
            if (TextUtils.isEmpty(SPUtil.getInstance(context).getString(Constant.USERNAME))) {
                Intent sayHelloIntent = new Intent(context, LoginActivity.class);
                sayHelloIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(sayHelloIntent);
            } else {
                Intent sayHelloIntent = new Intent(context, MainActivity.class);
                sayHelloIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(sayHelloIntent);
            }


        }
    }
}
