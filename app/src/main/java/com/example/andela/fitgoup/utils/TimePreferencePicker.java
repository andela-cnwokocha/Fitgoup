package com.example.andela.fitgoup.utils;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.preference.DialogPreference;
import android.util.AttributeSet;

public class TimePreferencePicker extends DialogPreference {
  public int hour = 0;
  public int minute = 0;

  private static int parseHour(String value) {
    try {
      String[] time = value.split(":");
      return (Integer.parseInt(time[0]));
    } catch (Exception e) {
      return 0;
    }
  }

  private static int parseMinute(String value) {
    try {
      String[] time = value.split(":");
      return (Integer.parseInt(time[1]));
    } catch (Exception e) {
      return 0;
    }
  }

  public static String timeToString(int hour, int minute) {
    return String.format("%02d", hour) + ":" + String.format("%02d", minute);
  }

  public TimePreferencePicker(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected Object onGetDefaultValue(TypedArray typedArray, int index) {
    return typedArray.getString(index);
  }

  @Override
  protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
    String value;
    if (restoreValue) {
      if (defaultValue == null) value = getPersistedString("00:00");
      else value = getPersistedString(defaultValue.toString());
    } else {
      value = defaultValue.toString();
    }
    hour = parseHour(value);
    minute = parseMinute(value);
  }

  public void persistStringValue(String value) {
    persistString(value);
  }
}