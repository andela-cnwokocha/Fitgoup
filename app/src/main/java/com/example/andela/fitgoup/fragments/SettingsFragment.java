package com.example.andela.fitgoup.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.SwitchPreferenceCompat;

import com.example.andela.fitgoup.R;
import com.example.andela.fitgoup.utils.TimePickerDialogFragmentCompat;
import com.example.andela.fitgoup.utils.TimePreferencePicker;

import java.util.Date;

import me.philio.preferencecompatextended.PreferenceFragmentCompat;

/**
 * Created by andela on 3/6/16.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

  private SwitchPreferenceCompat countdown;
  private SwitchPreferenceCompat timer;
  private SharedPreferences.OnSharedPreferenceChangeListener listener;
  private SharedPreferences sharedPreferences;
  private SwitchPreferenceCompat scheduleTime;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
  }

  @Override
  public void onCreatePreferences(Bundle bundle, String s) {
    addPreferencesFromResource(R.xml.pref_setting);
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

    listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
      @Override
      public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        timer = (SwitchPreferenceCompat) findPreference("timer_switch");
        countdown = (SwitchPreferenceCompat) findPreference("pushup_switch");
        scheduleTime = (SwitchPreferenceCompat) findPreference("pushup_time");
        if((key.equals("timer_switch")) && timer.isChecked()) {
          countdown.setChecked(false);
          timer.setSummary(String.valueOf(sharedPreferences.getBoolean("timer_switch", false)));
          countdown.setSummary(String.valueOf(sharedPreferences.getBoolean("pushup_switch", false)));
        } else if (key.equals("pushup_switch") && countdown.isChecked()) {
          timer.setChecked(false);
          countdown.setSummary(String.valueOf(sharedPreferences.getBoolean("pushup_switch", false)));
          timer.setSummary(String.valueOf(sharedPreferences.getBoolean("timer_switch", false)));
        } else if(key.equals("pushup_day") && scheduleTime.isChecked()) {
          int val = sharedPreferences.getInt("pushup_day", 3);
          findPreference("pushup_day").setTitle(String.format("Remind me every %d day(s)",val));
        } else if (key.equals("pushup_hour") && scheduleTime.isChecked()) {
          sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
          findPreference("pushup_hour").setTitle(sharedPreferences.getString("pushup_hour", "12:00"));
        }
      }
    };
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
  }

  @Override
  public void onResume() {
    super.onResume();
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
    findPreference("pushup_day").setTitle(String.format("Remind me every %d day(s)",
        sharedPreferences.getInt("pushup_day", 3)));
    findPreference("pushup_hour").setTitle(sharedPreferences.getString("pushup_hour", "12:00"));
  }

  @Override
  public void onPause() {
    super.onPause();
    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);
    findPreference("pushup_day").setTitle(String.format("Remind me every %d day(s)",
        sharedPreferences.getInt("pushup_day", 3)));
    findPreference("pushup_hour").setTitle(sharedPreferences.getString("pushup_hour", "12:00"));
  }

  @Override
  public void onDisplayPreferenceDialog(Preference preference)
  {
    DialogFragment dialogFragment = null;
    if (preference instanceof TimePreferencePicker)
    {
      dialogFragment = new TimePickerDialogFragmentCompat();
      Bundle bundle = new Bundle(1);
      bundle.putString("key", preference.getKey());
      dialogFragment.setArguments(bundle);
    }

    if (dialogFragment != null)
    {
      dialogFragment.setTargetFragment(this, 0);
      dialogFragment.show(this.getFragmentManager(), "android.support.v7.preference.PreferenceFragment.DIALOG");
    }
    else
    {
      super.onDisplayPreferenceDialog(preference);
    }
  }

}
