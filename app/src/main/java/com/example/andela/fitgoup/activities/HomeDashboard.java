package com.example.andela.fitgoup.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.andela.fitgoup.R;
import com.example.andela.fitgoup.fragments.FirstFragment;
import com.example.andela.fitgoup.fragments.FourthFragment;
import com.example.andela.fitgoup.fragments.SecondFragment;
import com.example.andela.fitgoup.fragments.ThirdFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeDashboard extends AppCompatActivity {
  private ViewPager mViewPager;


  public HomeDashboard() {}

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_dashboard);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setTitle("Fitgoup");
    setSupportActionBar(toolbar);
    mViewPager = (ViewPager) findViewById(R.id.container);
    setUpPager(mViewPager);
    TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
    tabs.setupWithViewPager(mViewPager);
  }

  public void setUpPager(ViewPager viewPager) {
    SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
    adapter.addPageFragment(new FirstFragment(), "Exercise");
    adapter.addPageFragment(new SecondFragment(), "Settings");
    adapter.addPageFragment(new ThirdFragment(), "Calendar");
    adapter.addPageFragment(new FourthFragment(), "Statistics");
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
