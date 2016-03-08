package com.example.andela.fitgoup.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andela.fitgoup.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by andela on 3/6/16.
 */
public class ExerciseFragment extends Fragment {
  private TextView timerview;
  private TextView startbutton;
  private CountDownTimer countDownTimer;
  private LinearLayout recordLayout;

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
    init();
    timerview = (TextView) view.findViewById(R.id.timer_field);
    timerview.setText("00:00:00");
    startbutton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (startbutton.getText().equals("Start")) {
          countDownTimer.start();
          startbutton.setText("Stop");
          recordLayout.setVisibility(View.VISIBLE);
        } else {
          countDownTimer.cancel();
          timerview.setText("00:00:00");
          startbutton.setText("Start");
          recordLayout.setVisibility(View.INVISIBLE);
        }

      }
    });
  }

  private void init() {
    countDownTimer = new CountDownTimer(60 * 5 * 1000, 1000) {
      public void onTick(long millisUntilFinished) {
        long seconds = millisUntilFinished/1000;
        timerview.setText(String.format("%02d:%02d:%02d", (seconds / 3600),
            (seconds % 3600) / 60, (seconds % 60)));
      }
      public void onFinish() {
        timerview.setText("done!");
        startbutton.setText("Start");
        recordLayout.setVisibility(View.VISIBLE);
      }
    };
  }

}
