package com.example.andela.fitgoup.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.andela.fitgoup.R;
import com.example.andela.fitgoup.fragments.CalendarFragment;
import com.example.andela.fitgoup.fragments.ExerciseFragment;
import com.example.andela.fitgoup.fragments.StatisticsFragment;

public class HomeDrawer extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {
  private FragmentTransaction ft;
  private SharedPreferences preferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        SharedPreferences getPrefs = PreferenceManager
            .getDefaultSharedPreferences(getBaseContext());
        boolean isFirstStart = getPrefs.getBoolean("firstStart", true);
        if (isFirstStart) {
          Intent i = new Intent(HomeDrawer.this, AppIntroActivity.class);
          startActivity(i);
          SharedPreferences.Editor e = getPrefs.edit();
          e.putBoolean("firstStart", false);
          e.apply();
        }
      }
    });
    t.start();

    preferences = PreferenceManager.getDefaultSharedPreferences(this);
    setContentView(R.layout.activity_home_drawer);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.dfragment_exercise, new ExerciseFragment()).commit();
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      finish();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.home_drawer, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      startSetting();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case R.id.push_up:
        setFragment(new ExerciseFragment());
        break;
      case R.id.stats:
        setFragment(new StatisticsFragment());
        break;
      case R.id.calendar:
        setFragment(new CalendarFragment());
        break;
      case R.id.settings:
        startSetting();
        break;
    }
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  private void setFragment(Fragment fragment) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(R.id.dfragment_exercise, fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }

  private void startSetting() {
    Intent settingIntent = new Intent(this, SettingActivity.class);
    startActivityForResult(settingIntent, 0);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == 0) {
      ExerciseFragment fragment = (ExerciseFragment) getSupportFragmentManager()
              .getFragments().get(0);
      fragment.updateComponents();
      fragment.setAlarmService();
    }
  }
}
