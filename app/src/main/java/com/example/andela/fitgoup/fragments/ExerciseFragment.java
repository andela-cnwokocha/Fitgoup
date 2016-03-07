package com.example.andela.fitgoup.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andela.fitgoup.R;

/**
 * Created by andela on 3/6/16.
 */
public class ExerciseFragment extends Fragment {

  public void FirstFragment() {}

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater layoutInflater, ViewGroup group, Bundle savedInstance) {
    return layoutInflater.inflate(R.layout.fragment_home_dashboard, group, false);
  }

}
