package com.example.andela.fitgoup.fragments;


import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andela.fitgoup.R;
import com.example.andela.fitgoup.model.PushUpModel;

import java.util.List;

/**
 * Created by andela on 3/6/16.
 */
public class ExerciseFragment extends Fragment {
  private TextView timerview;
  private TextView startbutton;
  private CountDownTimer countDownTimer;
  private LinearLayout recordLayout;
  private EditText pushups;
  private Button saveButton;

  public ExerciseFragment() {}

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
    init();
    timerview = (TextView) view.findViewById(R.id.timer_field);
    timerview.setText(R.string.init_timer);
    startbutton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (startbutton.getText().equals("Start")) {
          countDownTimer.start();
          startbutton.setText(R.string.stop_timer);
        } else {
          countDownTimer.cancel();
          timerview.setText(R.string.init_timer);
          startbutton.setText(R.string.start_timer);
          recordLayout.setVisibility(View.INVISIBLE);
        }

      }
    });
    saveButton();
  }

  private void init() {
    countDownTimer = new CountDownTimer(60 * 1 * 500, 500) {
      public void onTick(long millisUntilFinished) {
        long seconds = millisUntilFinished/2000;
        timerview.setText(String.format("%02d:%02d:%02d", (seconds / 3600),
            (seconds % 3600) / 60, (seconds % 60)));
      }
      public void onFinish() {
        timerview.setText("done!");
        startbutton.setText(R.string.restart_button);
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

  private int processedpushup() {
    return Integer.parseInt(pushups.getText().toString().trim());
  }

  private void savePushups() {
    PushUpModel pushUpModel = new PushUpModel();
    pushUpModel.pushups = processedpushup();
    pushUpModel.save();
  }

  private void saveButton() {
    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int values = pushups.getText().toString().trim().length();
        if (values > 0) {
          savePushups();
          pushups.setEnabled(false);
          saveButton.setClickable(false);
          //testing size
          List<PushUpModel> pushfits = PushUpModel.fetchPushups();
          Log.i("pushfitsSize", String.valueOf(pushfits.size()));
        } else {
          pushups.setError("No Pushups entered");
        }
      }
    });
  }

}
