package com.rsdt.strukton;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author Dingenis Sieger Sinke
 * @version 1.0
 * @since 22-7-2016
 * Description...
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent i = new Intent(context, SplashActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
