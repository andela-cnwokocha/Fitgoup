package com.example.andela.fitgoup.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.BootstrapDropDown;
import com.example.andela.fitgoup.R;
import com.example.andela.fitgoup.model.PushUpModel;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {
  private List<PushUpModel> pushuprecordMain;
  private List<PushUpModel> pushuprecordsSubset = new ArrayList<>();
  private ValueLineSeries series;
  private View viewing;
  private int recordsSize;


  public StatisticsFragment() {
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_fourth, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstance) {
    viewing = view;
    final BootstrapDropDown spinnerDropdown = (BootstrapDropDown) view.findViewById(R.id.mydropdown);
    pushuprecordMain = PushUpModel.fetchPushups();
    dateSelector(5, spinnerDropdown);
    spinnerDropdown.setOnDropDownItemClickListener(new BootstrapDropDown.OnDropDownItemClickListener() {
      @Override
      public void onItemClick(ViewGroup parent, View v, int id) {
        switch (id) {
          case 0:
            dateSelector(5, spinnerDropdown);
            break;
          case 1:
            dateSelector(7, spinnerDropdown);
            break;
          case 2:
            dateSelector(14, spinnerDropdown);
            break;
          case 3:
            dateSelector(21, spinnerDropdown);
            break;
          case 4:
            dateSelector(30, spinnerDropdown);
            break;
        }
      }
    });
  }

  private void dateSelector(int sizetoplot, BootstrapDropDown spinner) {
    recordsSize = pushuprecordMain.size();
    if (recordsSize > 0) {
      int recordsSize = pushuprecordMain.size();
      final ValueLineChart mCubicValueLineChart = (ValueLineChart) viewing.findViewById(R.id.linechart);
      series = new ValueLineSeries();
      series.setColor(0xFF56B7F1);
      int start = recordsSize - sizetoplot;
      if (start >= 1) {
        pushuprecordsSubset = pushuprecordMain.subList(start, recordsSize);
      } else {
        pushuprecordsSubset = pushuprecordMain;
      }
      for (PushUpModel pushUpModel: pushuprecordsSubset) {
        series.addPoint(new ValueLinePoint(pushUpModel.currentDay, (float)pushUpModel.pushups));
      }
      spinner.setText(String.format("Last %d Days", sizetoplot));
      mCubicValueLineChart.addSeries(series);
      mCubicValueLineChart.startAnimation();
    } else {
      LinearLayout layout = (LinearLayout) viewing.findViewById(R.id.noNoteLayout);
      layout.setVisibility(View.VISIBLE);
    }
  }

}
