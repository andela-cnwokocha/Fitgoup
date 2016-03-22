package com.example.andela.fitgoup.activities;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.example.andela.fitgoup.R;
import com.example.andela.fitgoup.fragments.CalendarFragment;
import com.example.andela.fitgoup.fragments.ExerciseFragment;
import com.example.andela.fitgoup.fragments.InfoFragment;
import com.example.andela.fitgoup.fragments.SettingsFragment;
import com.example.andela.fitgoup.fragments.StatisticsFragment;
import com.example.andela.fitgoup.model.PushUpModel;
import com.example.andela.fitgoup.notification.AlarmBroadcast;

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
  private SharedPreferences preferences;

  public HomeDashboard() {}

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    TypefaceProvider.registerDefaultIconSets();
    setContentView(R.layout.activity_home_dashboard);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setTitle("PushFit");
    setSupportActionBar(toolbar);
    mViewPager = (ViewPager) findViewById(R.id.container);
    setUpPager(mViewPager);
    tabs = (TabLayout) findViewById(R.id.tabs);
    tabs.setupWithViewPager(mViewPager);

    setTabIcons();

    /*PushUpModel.clearData();*/

    /*PushUpModel data9 = new PushUpModel(32, "Mar 8, 2016");
    data9.save();
    PushUpModel data1 = new PushUpModel(16, "Mar 9, 2016");
    data1.save();
    PushUpModel data2 = new PushUpModel(20, "Mar 10, 2016");
    data2.save();
    PushUpModel data3 = new PushUpModel(10, "Mar 11, 2016");
    data3.save();
    PushUpModel data4 = new PushUpModel(25, "Mar 12, 2016");
    data4.save();
    PushUpModel data5 = new PushUpModel(30, "Mar 13, 2016");
    data5.save();
    PushUpModel data6 = new PushUpModel(20, "Mar 15, 2016");
    data6.save();
    PushUpModel data7 = new PushUpModel(27, "Mar 16, 2016");
    data7.save();
    PushUpModel data8 = new PushUpModel(13, "Mar 17, 2016");
    data8.save();*/
    preferences = PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferences prefs = this.getSharedPreferences("alarm_time", MODE_PRIVATE);
    if (preferences.getBoolean("pushup_time", true)) {
      SharedPreferences.Editor edit = prefs.edit();
      int alarms = prefs.getInt("numberofalarm", 1);
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
      tabs.getTabAt(1).setIcon(tabIcons[1]);
      tabs.getTabAt(2).setIcon(tabIcons[2]);
      tabs.getTabAt(3).setIcon(tabIcons[3]);
      tabs.getTabAt(4).setIcon(tabIcons[4]);
    } catch (NullPointerException npe) {
      npe.getCause().printStackTrace();
    }
  }

  public void setUpPager(ViewPager viewPager) {
    SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
    adapter.addPageFragment(new ExerciseFragment(), "Exercise");
    adapter.addPageFragment(new InfoFragment(), "Info");
    adapter.addPageFragment(new SettingsFragment(), "Settings");
    adapter.addPageFragment(new StatisticsFragment(), "Statistics");
    adapter.addPageFragment(new CalendarFragment(), "Calendar");
    viewPager.setAdapter(adapter);
    viewPager.setOffscreenPageLimit(1);
    viewPager.getAdapter().notifyDataSetChanged();
  }

  public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
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

    @Override
    public int getItemPosition(Object object) {
      return POSITION_NONE;
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
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MINUTE, getMinute());
    calendar.set(Calendar.HOUR_OF_DAY, getHour());

    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
        AlarmManager.INTERVAL_DAY*preferences.getInt("pushup_day", 1), pIntent);
  }

  private int getMinute() {
    String val = preferences.getString("pushup_hour", "12:00");
    return Integer.parseInt(val.split(":")[1]);
  }

  private int getHour() {
    String val = preferences.getString("pushup_hour", "12:00");
    return Integer.parseInt(val.split(":")[0]);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    Calendar time = Calendar.getInstance();
    int currentHour = time.get(Calendar.HOUR_OF_DAY);
    int currentMin = time.get(Calendar.MINUTE);
    if ((currentHour < getHour()) && (currentMin < getMinute())) {
      SharedPreferences prefs = this.getSharedPreferences("alarm_time", MODE_PRIVATE);
      SharedPreferences.Editor edit = prefs.edit();
      edit.putInt("numberofalarm",1);
      edit.apply();
    }
  }
}
