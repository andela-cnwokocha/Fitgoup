package com.example.andela.fitgoup.activities;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.example.andela.fitgoup.R;
import com.example.andela.fitgoup.fragments.CalendarFragment;
import com.example.andela.fitgoup.fragments.ExerciseFragment;
import com.example.andela.fitgoup.fragments.StatisticsFragment;
import com.example.andela.fitgoup.fragments.SettingsFragment;
import com.example.andela.fitgoup.fragments.InfoFragment;
import com.example.andela.fitgoup.model.PushUpModel;

import java.util.ArrayList;
import java.util.List;

public class HomeDashboard extends AppCompatActivity {
  private ViewPager mViewPager;
  private TabLayout tabs;
  private int[] tabIcons = {R.drawable.ic_fitness_center,
      R.drawable.ic_help_outline,
      R.drawable.ic_settings_white,
      R.drawable.ic_show_chart,
      R.drawable.ic_calendar};

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
    PushUpModel.clearData();
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

}
