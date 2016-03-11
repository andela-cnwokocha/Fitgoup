package com.example.andela.fitgoup.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceManager;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.text.InputType;
import android.util.Log;

import com.example.andela.fitgoup.R;

/**
 * Created by andela on 3/6/16.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

  private SwitchPreferenceCompat countdown;
  private SwitchPreferenceCompat timer;
  private SharedPreferences.OnSharedPreferenceChangeListener listener;
  private SharedPreferences sharedPreferences;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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
        if((key.equals("timer_switch") || key.equals("timer_count")) && timer.isChecked()) {
          countdown.setChecked(false);
          timer.setSummary(String.valueOf(sharedPreferences.getBoolean("timer_switch", false)));
          countdown.setSummary(String.valueOf(sharedPreferences.getBoolean("pushup_switch", false)));
          findPreference("timer_count").setSummary(sharedPreferences.getString("timer_count", "5"));

        } else if (key.equals("pushup_switch") && countdown.isChecked()) {
          timer.setChecked(false);
          Log.i("switches", "" + timer.isChecked());
          timer.setSummary(String.valueOf(sharedPreferences.getBoolean("timer_switch", false)));
          countdown.setSummary(String.valueOf(sharedPreferences.getBoolean("pushup_switch", false)));
          findPreference("pushup_count").setSummary(String.valueOf(sharedPreferences.getString("pushup_count", "10")));
        }
      }
    };
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
  }

  @Override
  public void onResume() {
    super.onResume();
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
  }

  @Override
  public void onPause() {
    super.onPause();
    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);
  }
}
