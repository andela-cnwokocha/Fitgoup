package com.example.andela.fitgoup.notification;

import android.app.IntentService;
import android.content.Intent;


/**
 * Created by andela on 3/18/16.
 */
public class UserAlarmService extends IntentService {

  public static final String CREATE = "CREATE";

  public UserAlarmService() {
    super("user-service");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
  }
}
