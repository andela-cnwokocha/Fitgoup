package com.example.andela.fitgoup.fragments;


import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andela.fitgoup.R;
import com.example.andela.fitgoup.activities.HomeDashboard;
import com.example.andela.fitgoup.model.PushUpModel;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by andela on 3/6/16.
 */
public class ExerciseFragment extends Fragment implements SensorEventListener {
  private TextView timerview;
  private TextView startbutton;
  private CountDownTimer countDownTimer;
  private LinearLayout recordLayout;
  private EditText pushups;
  private Button saveButton;
  private long timerCount;
  private boolean timeroption;
  private long countDownCount;
  private long counter = 0;
  private boolean countdownoption;
  private SharedPreferences preferences;
  private SensorManager mSensorManager;
  private Sensor mSensor;

  public ExerciseFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater layoutInflater, ViewGroup group, Bundle savedInstance) {
    return layoutInflater.inflate(R.layout.fragment_home_dashboard, group, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstance) {
    startbutton = (TextView) view.findViewById(R.id.fragment_exercise);
    recordLayout = (LinearLayout) view.findViewById(R.id.record);
    pushups = (EditText) view.findViewById(R.id.pushup_num);
    saveButton = (Button) view.findViewById(R.id.save_button);
    timerview = (TextView) view.findViewById(R.id.timer_field);
    mSensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    setCountOptions();
    saveButton();
    startbutton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        setCountOptions();
        if (startbutton.getText().equals("Start")) {
          if (timeroption) {
            initTimeCounter();
            countDownTimer.start();
            startbutton.setText(R.string.stop_timer);
          } else if (countdownoption) {
            mSensorManager.registerListener(ExerciseFragment.this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
            if (countDownCount == counter) {
              timerview.setText(String.format("You recorded %s Push-ups!", counter));
            }
          }
        } else {
          if (countDownTimer != null) {
            countDownTimer.cancel();
          }
          mSensorManager.unregisterListener(ExerciseFragment.this);
          timerview.setText(R.string.init_timer);
          startbutton.setText(R.string.start_timer);
          recordLayout.setVisibility(View.INVISIBLE);
        }
      }
    });
  }

  private void initTimeCounter() {
    countDownTimer = new CountDownTimer(timerCount * 60000, 1000) {
      public void onTick(long millisUntilFinished) {
        setCounterView(millisUntilFinished/1000);
      }
      public void onFinish() {
        timerview.setText("done!");
        startbutton.setText(R.string.restart_button);
        pushups.setVisibility(View.VISIBLE);
        saveButton.setText("Save Record");
        saveButton.setClickable(true);
        recordLayout.setVisibility(View.VISIBLE);
        try {
          Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
          Ringtone doneSound = RingtoneManager.getRingtone(getActivity().getApplicationContext(), notification);
          doneSound.play();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };
  }

  private void setCounterView(long secsval) {
    timerview.setText(String.format("%02d:%02d:%02d", (secsval / 3600),
        (secsval % 3600) / 60, (secsval % 60)));
  }

  private void setCountOptions() {
    preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    timeroption = preferences.getBoolean("timer_switch", false);
    countdownoption = preferences.getBoolean("pushup_switch", false);
    timerCount = Integer.parseInt(preferences.getString("timer_count", "5"));
    countDownCount = Integer.parseInt(preferences.getString("pushup_count", "10"));

  }

  private void saveButton() {
    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int values = pushups.getText().toString().trim().length();
        if ((values > 0) && startbutton.getText().equals("Restart")) {
          savePushups(processedpushup());
          pushups.setVisibility(View.GONE);
          saveButton.setText("Saved");
          saveButton.setClickable(false);
        } else {
          pushups.setError("No Pushups entered");
        }
      }
    });
  }

  private void savePushups(long pushup) {
    List<PushUpModel> push = PushUpModel.fetchPushups();
    boolean dateExists = false;
    if (push.size() > 0) {
      for (PushUpModel pushUpModel : push) {
        if (pushUpModel.currentDay.equals(getLogTime())) {
          dateExists = true;
          PushUpModel savedPushup = PushUpModel.load(PushUpModel.class, pushUpModel.getId());
          savedPushup.pushups+=pushup;
          savedPushup.save();
        }
      }
      for (PushUpModel pis:push) {
        Log.i("pis", " "+pis.pushups+" "+pis.currentDay);
      }
      if (!dateExists) {
        PushUpModel pushUpModel = new PushUpModel(pushup, getLogTime());
        pushUpModel.save();
      }
    } else {
      PushUpModel pushUpModel = new PushUpModel(pushup, getLogTime());
      pushUpModel.save();
    }
  }

  private long processedpushup() {
    return Integer.parseInt(pushups.getText().toString().trim());
  }


  @Override
  public void onSensorChanged(SensorEvent event) {
    startbutton.setText(R.string.stop_timer);
    if (event.values[0] == 0) {
      timerview.setText(String.format("%s", counter += 1));
      if (counter == countDownCount) {
        mSensorManager.unregisterListener(this);
        startbutton.setText(R.string.restart_button);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone doneSound = RingtoneManager.getRingtone(getActivity().getApplicationContext(), notification);
        doneSound.play();
        savePushups(counter);
        timerview.setText(String.format("You recorded %s Push-ups!", counter));
        counter = 0;
      }
    }
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {

  }

  @Override
  public void onResume() {
    super.onResume();
    mSensorManager.unregisterListener(this);
  }

  @Override
  public void onPause() {
    super.onPause();
    mSensorManager.unregisterListener(this);
  }

  private String getLogTime() {
    Date currentTime = new Date();
    Locale myLocale = new Locale("en");
    return DateFormat.getDateInstance(DateFormat.DEFAULT, myLocale).format(currentTime);
  }

}

