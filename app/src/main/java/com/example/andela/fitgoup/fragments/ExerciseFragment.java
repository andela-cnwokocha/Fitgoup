package com.example.andela.fitgoup.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.andela.fitgoup.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by andela on 3/6/16.
 */
public class ExerciseFragment extends Fragment {

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
    TextView dateTextview = (TextView) view.findViewById(R.id.fragment_exercise);
    GregorianCalendar currentCalendar = new GregorianCalendar();
  }

}
