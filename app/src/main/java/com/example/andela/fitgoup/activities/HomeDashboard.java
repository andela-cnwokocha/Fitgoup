package com.example.andela.fitgoup.activities;


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
import com.example.andela.fitgoup.fragments.SettingsFragment;
import com.example.andela.fitgoup.fragments.StatisticsFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeDashboard extends AppCompatActivity {
  private ViewPager mHomeViewPager;
  private TabLayout tabs;
  private int[] tabIcons = {R.drawable.ic_fitness_center,
      R.drawable.ic_calendar,
      R.drawable.ic_show_chart,
      R.drawable.ic_settings_white};
  private SharedPreferences preferences;

  public HomeDashboard() {}

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        SharedPreferences getPrefs = PreferenceManager
            .getDefaultSharedPreferences(getBaseContext());
        boolean isFirstStart = getPrefs.getBoolean("firstStart", true);
        if (isFirstStart) {
          Intent i = new Intent(HomeDashboard.this, AppIntroActivity.class);
          startActivity(i);
          SharedPreferences.Editor e = getPrefs.edit();
          e.putBoolean("firstStart", false);
          e.apply();
        }
      }
    });
    t.start();

    TypefaceProvider.registerDefaultIconSets();
    setContentView(R.layout.activity_home_dashboard);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setTitle("PushFit");
    setSupportActionBar(toolbar);
    mHomeViewPager = (ViewPager) findViewById(R.id.container);
    setUpPager(mHomeViewPager);
    tabs = (TabLayout) findViewById(R.id.tabs);
    tabs.setupWithViewPager(mHomeViewPager);
    setTabIcons();

    preferences = PreferenceManager.getDefaultSharedPreferences(this);
  }

  public void setTabIcons() {
    try {
      tabs.getTabAt(0).setIcon(tabIcons[0]);
      tabs.getTabAt(1).setIcon(tabIcons[1]);
      tabs.getTabAt(2).setIcon(tabIcons[2]);
      tabs.getTabAt(3).setIcon(tabIcons[3]);

    } catch (NullPointerException npe) {
      npe.getCause().printStackTrace();
    }
  }

  public void setUpPager(ViewPager viewPager) {
    SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
    adapter.addPageFragment(new ExerciseFragment(), "Exercise");
    adapter.addPageFragment(new CalendarFragment(), "Calendar");
    adapter.addPageFragment(new StatisticsFragment(), "Statistics");
    adapter.addPageFragment(new SettingsFragment(), "Settings");
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
}
