package checkpoint.project.andela.pushfit.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by andela on 3/19/16.
 */

public class AlarmResetter extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    Intent service = new Intent(context, UserAlarmService.class);
    service.setAction(UserAlarmService.CREATE);
    context.startService(service);
  }

}