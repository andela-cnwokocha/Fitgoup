package com.example.andela.fitgoup.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by andela on 3/18/16.
 */
public class AlarmBroadcast extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

   if(preferences.getBoolean("pushup_time", true)) {
     Intent intends = new Intent(context, UserAlarmService.class);
     context.startService(intends);
   } else {
     cancelService(context);
   }
  }

  private void cancelService(Context context) {
    Intent stopIntent = new Intent(context, UserAlarmService.class);
    context.stopService(stopIntent);
  }
}
