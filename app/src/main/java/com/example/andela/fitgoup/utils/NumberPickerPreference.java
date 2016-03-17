/*package com.example.andela.fitgoup.utils;


import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

public class NumberPickerPreference extends DialogPreference {
  private int lastHour=0;
  private int lastMinute=0;
  private TimePicker picker=null;

  public static int getHour(String time) {
    String[] pieces=time.split(":");

    return(Integer.parseInt(pieces[0]));
  }

  public static int getMinute(String time) {
    String[] pieces=time.split(":");

    return(Integer.parseInt(pieces[1]));
  }

  public NumberPickerPreference(Context ctxt, AttributeSet attrs) {
    super(ctxt, attrs);

    setPositiveButtonText("Set");
    setNegativeButtonText("Cancel");
  }

  @Override
  protected View onCreateDialogView() {
    picker=new TimePicker(getContext());

    return(picker);
  }

  @Override
  protected void onBindDialogView(View v) {
    super.onBindDialogView(v);

    picker.setCurrentHour(lastHour);
    picker.setCurrentMinute(lastMinute);
  }

  @Override
  protected void onDialogClosed(boolean positiveResult) {
    super.onDialogClosed(positiveResult);

    if (positiveResult) {
      lastHour=picker.getCurrentHour();
      lastMinute=picker.getCurrentMinute();

      String time=String.valueOf(lastHour)+":"+String.valueOf(lastMinute);

      if (callChangeListener(time)) {
        persistString(time);
      }
    }
  }

  @Override
  protected Object onGetDefaultValue(TypedArray a, int index) {
    return(a.getString(index));
  }

  @Override
  protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
    String time=null;

    if (restoreValue) {
      if (defaultValue==null) {
        time=getPersistedString("00:00");
      }
      else {
        time=getPersistedString(defaultValue.toString());
      }
    }
    else {
      time=defaultValue.toString();
    }

    lastHour=getHour(time);
    lastMinute=getMinute(time);
  }
}

<com.example.andela.fitgoup.utils.NumberPickerPreference
        android:key="timer_count"
        android:dependency="timer_switch"
        android:title="Minutes"/>

        <!--<EditTextPreference
            android:key="timer_count"
            android:dependency="timer_switch"
            android:layout="?android:attr/preferenceLayoutChild"
            android:title="Minutes"
            android:summary="Rate number of minutes to pushup"
            android:defaultValue="5"
            android:dialogTitle="Enter the number of minutes you will do pushups" />-->
*/


package com.example.andela.fitgoup.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.DialogPreference;

//import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

import com.example.andela.fitgoup.R;



public class NumberPickerPreference extends DialogPreference implements  NumberPicker.OnValueChangeListener {
  private static final String NUMBER_PICKER_DEFAULT = "10";
  private NumberPicker numberPicker;
  private SharedPreferences sharedPreferences;
  private String savedValue;

  public NumberPickerPreference(Context context, AttributeSet attrs) {
    super(context, attrs);
    setDialogLayoutResource(R.layout.number_preference);
    //setOnPreferenceChangeListener(this);

  }

  @Override
  protected void onDialogClosed(boolean positiveResult) {
    super.onDialogClosed(positiveResult);
    int pno = numberPicker.getValue();
    if (positiveResult) {
      String push_number = String.valueOf(pno);
      if (callChangeListener(push_number)) {
        saveString("push_count", push_number);
      }
    }
    setSummary(pno + " Push Ups");
  }

  @Override
  protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
    if (restorePersistedValue) {
      setSummary(loadString() + " Push Ups");
    } else {
      setSummary(defaultValue.toString() + " Push Ups");
      persistString(defaultValue.toString());
    }
  }

  /*@Override
  public boolean onPreferenceChange(Preference preference, Object newValue) {
    setSummary(newValue.toString() + " Push Ups");
    saveString("push_count", newValue.toString());
    return false;
  }*/

  @Override
  public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

  }

  public void setTitle(CharSequence title) {
    super.setTitle(title);
  }

  protected Object onGetDefaultValue(TypedArray array, int index) {
    return array.getString(index);
  }

  protected View onCreateDialogView() {
    View view = super.onCreateDialogView();
    numberPicker = (NumberPicker) view.findViewById(R.id.number_picker);
    numberPicker.setMaxValue(300);
    numberPicker.setMinValue(5);
    numberPicker.setValue(Integer.parseInt(loadString()));
    return view;
  }

  protected void onBindDialogView(View view) {
    super.onBindDialogView(view);
  }

  public void saveString(String key, String value) {
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(key, value);
    editor.apply();
  }

  public String loadString() {
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
    savedValue = sharedPreferences.getString("push_count", NUMBER_PICKER_DEFAULT);
    return savedValue;
  }
}
