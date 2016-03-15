package com.example.andela.fitgoup.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.andela.fitgoup.R;
import com.example.andela.fitgoup.model.PushUpModel;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {


  public StatisticsFragment() {

  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_fourth, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstance) {
    ValueLineChart mCubicValueLineChart = (ValueLineChart) view.findViewById(R.id.linechart);

    ValueLineSeries series = new ValueLineSeries();
    series.setColor(0xFF56B7F1);

    List<PushUpModel> pushuprecordMain = PushUpModel.fetchPushups();
    List<PushUpModel> pushuprecords = new ArrayList<>();
    ArrayList<String> days = new ArrayList<>();
    if(pushuprecordMain.size() > 0) {
      //Select sections -> pushuprecords = pushuprecords.subList(0,5);

      // Get all recorded days
      for (PushUpModel record: pushuprecordMain) {
        days.add(record.currentDay);
      }
      Log.i("puz", ""+pushuprecordMain.size());
      for (PushUpModel pushUpModel: pushuprecordMain) {
        series.addPoint(new ValueLinePoint(pushUpModel.currentDay, (float)pushUpModel.pushups));
      }
    } else {
      LinearLayout layout = (LinearLayout) view.findViewById(R.id.noNoteLayout);
      layout.setVisibility(View.VISIBLE);
    }
    mCubicValueLineChart.addSeries(series);
    mCubicValueLineChart.startAnimation();
  }

}
