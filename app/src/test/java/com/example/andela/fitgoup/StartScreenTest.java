package com.example.andela.fitgoup;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import checkpoint.project.andela.pushfit.fragments.CalendarFragment;
import checkpoint.project.andela.pushfit.fragments.ExerciseFragment;
import checkpoint.project.andela.pushfit.fragments.SettingsFragment;
import checkpoint.project.andela.pushfit.fragments.StatisticsFragment;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by andela on 23/03/2016.
 */


@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class StartScreenTest {
  private HomeDashboard homeDashboard;
  private ViewPager viewPager;
  private TabLayout tabLayout;
  private CalendarFragment calendarFragment;
  private SettingsFragment settingsFragment;
  private StatisticsFragment statisticsFragment;
  private ExerciseFragment exerciseFragment;

  @Before
  public void setUp() {
    homeDashboard = Robolectric.setupActivity(HomeDashboard.class);
    viewPager = (ViewPager) homeDashboard.getWindow().findViewById(R.id.container);
    tabLayout = (TabLayout) homeDashboard.getWindow().findViewById(R.id.tabs);
  }

  @Test
  public void validateTitle() {
    assertTrue(homeDashboard.getTitle().toString().equals("PushFit"));
  }

  @Test
  public void testPagerProperties() {
    assertNotNull(viewPager);
    assertNotNull(tabLayout);

    assertTrue(tabLayout.getTabCount() == 4);

  }

  @Test
  public void exerciseFragmentTest() {
    ExerciseFragment exerciseFragment = new ExerciseFragment();
    FragmentManager fragmentManager = homeDashboard.getSupportFragmentManager();
    fragmentManager.beginTransaction().add(exerciseFragment, "EXERCISE").commit();
    homeDashboard.getSupportFragmentManager().executePendingTransactions();

    Assert.assertThat(exerciseFragment, CoreMatchers.not(CoreMatchers.nullValue()));
    Assert.assertThat(exerciseFragment.getView(), CoreMatchers.not(CoreMatchers.nullValue()));
    Assert.assertThat(exerciseFragment.getActivity(), CoreMatchers.not(CoreMatchers.nullValue()));
    Assert.assertThat(exerciseFragment.getActivity(), CoreMatchers.instanceOf(FragmentActivity.class));
    Assert.assertThat(exerciseFragment.getActivity(), CoreMatchers.instanceOf(HomeDashboard.class));

    TextView timerview = (TextView) homeDashboard.findViewById(R.id.timer_field);
    assertTrue(timerview.getText().equals("00:05:00"));
    TextView buttonText = (TextView) homeDashboard.findViewById(R.id.fragment_exercise);
    assertTrue(buttonText.getText().equals("Start"));

    assertTrue(tabLayout.getTabAt(0).getText().equals("Exercise"));
  }

  @Test
  public void calendarFragmentTest() {
    CalendarFragment calendarFragment = new CalendarFragment();
    SupportFragmentTestUtil.startFragment(calendarFragment);
    assertNotNull(calendarFragment);

  }

  @Test
  public void settingsFragmentTest() {
    SettingsFragment settingsFragment = new SettingsFragment();
    FragmentManager fragmentManager = homeDashboard.getSupportFragmentManager();
    fragmentManager.beginTransaction().add(settingsFragment, "Settings").commit();
    homeDashboard.getSupportFragmentManager().executePendingTransactions();

    Assert.assertThat(settingsFragment, CoreMatchers.not(CoreMatchers.nullValue()));
    Assert.assertThat(settingsFragment.getView(), CoreMatchers.not(CoreMatchers.nullValue()));
    Assert.assertThat(settingsFragment.getActivity(), CoreMatchers.not(CoreMatchers.nullValue()));
    Assert.assertThat(settingsFragment.getActivity(), CoreMatchers.instanceOf(FragmentActivity.class));
    Assert.assertThat(settingsFragment.getActivity(), CoreMatchers.instanceOf(HomeDashboard.class));

    assertTrue(tabLayout.getTabAt(3).getText().equals("Settings"));
  }

  @Test
  public void statisticsFragmentTest() {
    StatisticsFragment statisticsFragment = new StatisticsFragment();
    SupportFragmentTestUtil.startFragment(statisticsFragment);
    assertNotNull(statisticsFragment);
  }

}
