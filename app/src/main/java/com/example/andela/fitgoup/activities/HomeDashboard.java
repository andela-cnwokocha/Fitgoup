package com.example.andela.fitgoup.activities;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.example.andela.fitgoup.R;
import com.example.andela.fitgoup.fragments.CalendarFragment;
import com.example.andela.fitgoup.fragments.ExerciseFragment;
import com.example.andela.fitgoup.fragments.StatisticsFragment;
import com.example.andela.fitgoup.fragments.SettingsFragment;
import com.example.andela.fitgoup.fragments.InfoFragment;
import com.example.andela.fitgoup.model.PushUpModel;
import com.example.andela.fitgoup.notification.AlarmBroadcast;
import com.example.andela.fitgoup.notification.UserAlarmService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeDashboard extends AppCompatActivity {
  private ViewPager mViewPager;
  private TabLayout tabs;
  private int[] tabIcons = {R.drawable.ic_fitness_center,
      R.drawable.ic_help_outline,
      R.drawable.ic_settings_white,
      R.drawable.ic_show_chart,
      R.drawable.ic_calendar};
  private PendingIntent pendingIntent;
  private AlarmManager alarmManager;
  private SharedPreferences preferences;
  private Calendar calendar;

  public HomeDashboard() {}

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_dashboard);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setTitle("PushFit");
    setSupportActionBar(toolbar);
    mViewPager = (ViewPager) findViewById(R.id.container);
    setUpPager(mViewPager);
    tabs = (TabLayout) findViewById(R.id.tabs);
    tabs.setupWithViewPager(mViewPager);
    setTabIcons();

    // Initialize preferences
    //PreferenceManager.setDefaultValues(this, R.xml.pref_setting, false);
    //TypefaceProvider.registerDefaultIconSets();
    /*PushUpModel pushUpModel = new PushUpModel(14, "Mar 1, 2016");
    pushUpModel.save();
    PushUpModel.clearData();
    ;*/
    preferences = PreferenceManager.getDefaultSharedPreferences(this);
    Log.i("EFIZ",""+preferences.getBoolean("pushup_time", false));

    if (preferences.getBoolean("pushup_time", false)) {
      SharedPreferences prefs = this.getSharedPreferences("ALARM_COUNT", MODE_PRIVATE);
      SharedPreferences.Editor edit = prefs.edit();
      int alarms = prefs.getInt("numberofalarm", 1);

      // Initiate alarm so it doesn't run for every oncreate
      if (alarms < 2) {
        startAlarm();
        alarms++;
        edit.putInt("numberofalarm",alarms);
        edit.apply();
      }
    }

  }

  public void setTabIcons() {
    try {
      tabs.getTabAt(0).setIcon(tabIcons[0]);
      tabs.getTabAt(1).setIcon(tabIcons[2]);
      tabs.getTabAt(2).setIcon(tabIcons[1]);
      tabs.getTabAt(3).setIcon(tabIcons[3]);
      tabs.getTabAt(4).setIcon(tabIcons[4]);
    } catch (NullPointerException npe) {
      npe.getCause().printStackTrace();
    }
  }

  public void setUpPager(ViewPager viewPager) {
    SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
    adapter.addPageFragment(new ExerciseFragment(), "Exercise");
    adapter.addPageFragment(new SettingsFragment(), "Settings");
    adapter.addPageFragment(new InfoFragment(), "Info");
    adapter.addPageFragment(new StatisticsFragment(), "Statistics");
    adapter.addPageFragment(new CalendarFragment(), "Calendar");
    viewPager.setAdapter(adapter);
  }


  public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> pages = new ArrayList<>();
    private final List<String> titles = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      return pages.get(position);
    }

    @Override
    public int getCount() {
      return pages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return titles.get(position);
    }

    public void addPageFragment(Fragment newFragment, String fragmentTitle) {
      pages.add(newFragment);
      titles.add(fragmentTitle);
    }
  }

  private void startAlarm() {
    Intent intent = new Intent(getApplicationContext(), AlarmBroadcast.class);
    final PendingIntent pIntent = PendingIntent.getBroadcast(this, AlarmBroadcast.NOTIFY_ID,
        intent, PendingIntent.FLAG_UPDATE_CURRENT);

    // The time to fire
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MINUTE, 3);
    calendar.set(Calendar.HOUR, 3);
    calendar.set(Calendar.AM_PM,Calendar.PM);

    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60000*5, pIntent);
  }
}
