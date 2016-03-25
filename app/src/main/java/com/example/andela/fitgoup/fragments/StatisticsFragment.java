package com.example.andela.fitgoup.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.andela.fitgoup.R;
import com.example.andela.fitgoup.model.PushUpModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jaredrummler.materialspinner.MaterialSpinner;

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
    final MaterialSpinner spinnerDropdown = (MaterialSpinner) view.findViewById(R.id.mydropdown);
    spinnerDropdown.setItems("5 Days", "7 Days", "14 Days", "21 Days", "30 Days");
    lineChart = (LineChart) view.findViewById(R.id.linechart);
    pushuprecordMain = PushUpModel.fetchPushups();
    dateSelector(5);
    spinnerDropdown.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
      @Override
      public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        switch (position) {
          case 0:
            dateSelector(5);
            break;
          case 1:
            dateSelector(7);
            break;
          case 2:
            dateSelector(14);
            break;
          case 3:
            dateSelector(21);
            break;
          case 4:
            dateSelector(30);
            break;
        }
      }
    });
  }

  private void dateSelector(int sizeToPlot) {
    recordsSize = pushuprecordMain.size();
    LineDataSet dataset;
    if (recordsSize > 0) {
      ArrayList<Entry> pushups = new ArrayList<>();
      ArrayList<String> labels = new ArrayList<>();
      int start = recordsSize - sizeToPlot;

      if (start > 0) {
        List<PushUpModel> pushUpModels = pushuprecordMain.subList(start, recordsSize);
        for (int i = 0; i < pushUpModels.size(); i++) {
          pushups.add(new Entry((float) pushUpModels.get(i).pushups, i));
          labels.add(pushUpModels.get(i).currentDay);
        }
        dataset = new LineDataSet(pushups, "Push up chart");
      } else {
        for (PushUpModel model:pushuprecordMain) {
          pushups.add(new Entry((float) model.pushups, ((int) (long) model.getId()) - 1));
          labels.add(model.currentDay);
        }
        dataset = new LineDataSet(pushups, "Push up chart");
      }
      dataset.setAxisDependency(YAxis.AxisDependency.LEFT);
      dataset.setDrawFilled(false);
      dataset.setDrawCubic(true);
      ArrayList<ILineDataSet> iLineDataSets = new ArrayList<ILineDataSet>();
      iLineDataSets.add(dataset);

      LineData data = new LineData(labels, iLineDataSets);

      lineChart.setData(data);
      lineChart.setDescription("Daily push up count");
      lineChart.animateXY(2000, 2000);
      lineChart.setDragEnabled(true);
      lineChart.setScaleEnabled(true);
      lineChart.setTouchEnabled(true);
      lineChart.setPinchZoom(false);
      lineChart.invalidate();
    } else {
      LinearLayout layout = (LinearLayout) viewing.findViewById(R.id.noNoteLayout);
      layout.setVisibility(View.VISIBLE);
    }
  }
}
