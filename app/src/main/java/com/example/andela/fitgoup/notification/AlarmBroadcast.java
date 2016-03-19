package com.example.andela.fitgoup.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.andela.fitgoup.R;
import com.example.andela.fitgoup.activities.HomeDashboard;

/**
 * Created by andela on 3/18/16.
 */
public class AlarmBroadcast extends BroadcastReceiver {
  public static final int REQUEST_CODE = 12345;
  public static final int NOTIFY_ID = 5;
  private static boolean isNotificationActive = false;
  private NotificationManager nm;

  @Override
  public void onReceive(Context context, Intent intent) {
    Intent intends = new Intent(context, UserAlarmService.class);

    nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    Intent intent1 = new Intent(context, HomeDashboard.class);

    TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
    taskStackBuilder.addParentStack(HomeDashboard.class);
    taskStackBuilder.addNextIntent(intent1);
    PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

    Notification notification = new NotificationCompat.Builder(context)
        .setContentTitle("Push ups time!")
        .setContentText("It is time to do Push ups")
        .setSmallIcon(R.drawable.ic_fitness_center)
        .setContentIntent(pendingIntent)
        .setDefaults(NotificationCompat.DEFAULT_SOUND)
        .setAutoCancel(true)
        .build();
    nm.notify(NOTIFY_ID, notification);
    isNotificationActive = false;
    stopNotification();
    context.startService(intends);
  }

  private void stopNotification() {
    if(isNotificationActive) {
      nm.cancel(NOTIFY_ID);
    }
  }
}
