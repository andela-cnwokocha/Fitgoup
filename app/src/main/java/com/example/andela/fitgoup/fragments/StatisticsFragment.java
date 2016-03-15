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
    PushUpModel pushes1 = new PushUpModel(pushuprecordMain.get(0).pushups, pushuprecordMain.get(0).currentDay);
    PushUpModel pushes2 = new PushUpModel(14, "Mar 16, 2016");
    PushUpModel pushes3 = new PushUpModel(12, "Mar 17, 2016");
    PushUpModel pushes4 = new PushUpModel(18, "Mar 18, 2016");
    PushUpModel pushes5 = new PushUpModel(40, "Mar 19, 2016");
    PushUpModel pushes6 = new PushUpModel(14, "Mar 20, 2016");
    PushUpModel pushes7 = new PushUpModel(16, "Mar 21, 2016");
    pushuprecords.add(pushes1);
    pushuprecords.add(pushes2);
    pushuprecords.add(pushes3);
    pushuprecords.add(pushes4);
    pushuprecords.add(pushes5);
    pushuprecords.add(pushes6);
    pushuprecords.add(pushes7);
    if(pushuprecords.size() > 0) {
      for (PushUpModel pushUpModel: pushuprecords) {
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
