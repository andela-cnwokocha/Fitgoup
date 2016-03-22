package com.example.andela.fitgoup.notification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.andela.fitgoup.R;
import com.example.andela.fitgoup.activities.HomeDashboard;


/**
 * Created by andela on 3/18/16.
 */
public class UserAlarmService extends IntentService {
  private NotificationManager nm;
  public static final int NOTIFY_ID = 5;
  private static boolean isNotificationActive = false;

  public static final String CREATE = "CREATE";

  public UserAlarmService() {
    super("user-service");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    Intent intent1 = new Intent(this, HomeDashboard.class);

    nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
    taskStackBuilder.addParentStack(HomeDashboard.class);
    taskStackBuilder.addNextIntent(intent1);
    PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

    Notification notification = new NotificationCompat.Builder(this)
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
  }

  private void stopNotification() {
    if(isNotificationActive) {
      nm.cancel(NOTIFY_ID);
    }
  }
}
