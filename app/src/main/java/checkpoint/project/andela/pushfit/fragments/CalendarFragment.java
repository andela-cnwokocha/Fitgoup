package checkpoint.project.andela.pushfit.fragments;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andela.fitgoup.R;
import checkpoint.project.andela.pushfit.model.PushUpModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {
  private List<PushUpModel> pushUpModels;
  private Collection<CalendarDay> calendarDays = new Collection<CalendarDay>() {
    @Override
    public boolean add(CalendarDay object) {
      return false;
    }

    @Override
    public boolean addAll(Collection<? extends CalendarDay> collection) {
      return false;
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean contains(Object object) {
      return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
      return false;
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @NonNull
    @Override
    public Iterator<CalendarDay> iterator() {
      return null;
    }

    @Override
    public boolean remove(Object object) {
      return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
      return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
      return false;
    }

    @Override
    public int size() {
      return 0;
    }

    @NonNull
    @Override
    public Object[] toArray() {
      return new Object[0];
    }

    @NonNull
    @Override
    public <T> T[] toArray(T[] array) {
      return null;
    }
  };

  public CalendarFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_calendar, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstance) {
    getActivity().setTitle("PushFit");
    MaterialCalendarView calendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
    calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
    pushUpModels = PushUpModel.fetchPushups();
    List<Date> days = new ArrayList<Date>();
    for (PushUpModel day:pushUpModels) {
      days.add(dateFormatter(day.currentDay));
    }

    List<CalendarDay> listOfcalendarDays = new ArrayList<CalendarDay>();
    Calendar calendar = Calendar.getInstance();

    for (Date date:days) {
      calendar.setTime(date);
      CalendarDay calendarDay = CalendarDay.from(calendar);
      listOfcalendarDays.add(calendarDay);
    }

    calendarDays = listOfcalendarDays;
    calendarView.addDecorators(new PushUpDayDecor(calendarDays));
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

  private class PushUpDayDecor implements DayViewDecorator {
    private final int color = Color.parseColor("#FFC107");
    private final HashSet<CalendarDay> dates;
    private final Drawable pushup_drawable;

    public PushUpDayDecor(Collection<CalendarDay> dates) {
      this.dates = new HashSet<>(dates);
      pushup_drawable = new ColorDrawable(color);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
      return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
      view.setBackgroundDrawable(pushup_drawable);
      view.addSpan(new DotSpan(5, color));
    }
  }
}
