package com.example.andela.fitgoup.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andela.fitgoup.R;
import com.example.andela.fitgoup.model.PushUpModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {
  private List<PushUpModel> pushUpModels;

  public CalendarFragment() {
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_calendar, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstance) {
    MaterialCalendarView calendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
    //calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
    pushUpModels = PushUpModel.fetchPushups();
    List<Date> days = new ArrayList<Date>();
    for (PushUpModel day:pushUpModels) {
      days.add(dateFormatter(day.currentDay));
    }

    List<CalendarDay> list = new ArrayList<CalendarDay>();
    Calendar calendar = Calendar.getInstance();



  }

  private Date dateFormatter(String date) {
    SimpleDateFormat day = new SimpleDateFormat ("MMM dd, yyyy", Locale.ENGLISH);
    Date dated = new Date();
    try {
      dated =  day.parse(date);
    } catch (ParseException pe) {
    }
    return dated;
  }
}
