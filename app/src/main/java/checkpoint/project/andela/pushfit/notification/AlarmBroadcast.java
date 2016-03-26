package checkpoint.project.andela.pushfit.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by andela on 3/18/16.
 */
public class AlarmBroadcast extends BroadcastReceiver {
  SharedPreferences preferences;
  @Override
  public void onReceive(Context context, Intent intent) {
    preferences = PreferenceManager.getDefaultSharedPreferences(context);

   if(preferences.getBoolean("pushup_time", true)) {
     Intent intends = new Intent(context, UserAlarmService.class);
     context.startService(intends);
   } else {
     Log.i("FRET", "Stop ser call");
     cancelService(context);
   }
  }

  private void cancelService(Context context) {
    Intent stopIntent = new Intent(context, UserAlarmService.class);
    context.stopService(stopIntent);
  }
}
