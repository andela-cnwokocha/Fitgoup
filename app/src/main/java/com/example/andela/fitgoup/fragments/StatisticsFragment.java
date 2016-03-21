package com.example.andela.fitgoup.fragments;


import android.os.Build;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.BootstrapDropDown;
import com.example.andela.fitgoup.R;
import com.example.andela.fitgoup.activities.HomeDashboard;
import com.example.andela.fitgoup.model.PushUpModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {
  private List<PushUpModel> pushuprecordMain;
  private View viewing;
  private int recordsSize;
  private LineChart lineChart;


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
    lineChart = (LineChart) view.findViewById(R.id.linechart);
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
    if(recordsSize > 0) {
      final List<PushUpModel> pushing;
      int start = recordsSize - sizetoplot;
      if (start >= 1) {
        pushing = pushuprecordMain.subList(start, recordsSize);
      } else {
        pushing = pushuprecordMain;
      }
      ArrayList<Entry> pushUps = new ArrayList<>();
      ArrayList<String> labels = new ArrayList<>();
      for (PushUpModel pushUpModel: pushing) {
        pushUps.add(new Entry((float) pushUpModel.pushups, ((int) (long) pushUpModel.getId()) - 1));
        labels.add(pushUpModel.currentDay);
      }
      LineDataSet dataSet = new LineDataSet(pushUps, "Push ups chart");
      LineData data = new LineData(labels, dataSet);
      lineChart.setData(data);
      lineChart.notifyDataSetChanged();
      dataSet.setDrawFilled(true);
      dataSet.setDrawCubic(true);
      lineChart.setDescription("Chart for the number of pushups done so far");
      lineChart.invalidate();
      spinner.setText(String.format("Last %d Days", sizetoplot));
    } else {
      LinearLayout layout = (LinearLayout) viewing.findViewById(R.id.noNoteLayout);
      layout.setVisibility(View.VISIBLE);
    }
  }
}
