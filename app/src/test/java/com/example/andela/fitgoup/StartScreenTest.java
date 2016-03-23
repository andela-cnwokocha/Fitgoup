package com.example.andela.fitgoup;

import android.os.Build;
import android.support.v4.view.ViewPager;

import com.example.andela.fitgoup.activities.HomeDashboard;
import com.example.andela.fitgoup.fragments.CalendarFragment;
import com.example.andela.fitgoup.fragments.ExerciseFragment;
import com.example.andela.fitgoup.fragments.SettingsFragment;
import com.example.andela.fitgoup.fragments.StatisticsFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

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
  private CalendarFragment calendarFragment;
  private SettingsFragment settingsFragment;
  private StatisticsFragment statisticsFragment;
  private ExerciseFragment exerciseFragment;

  @Before
  public void setUp() {
    homeDashboard = Robolectric.setupActivity(HomeDashboard.class);
    viewPager = (ViewPager) homeDashboard.getWindow().findViewById(R.id.container);
  }

  /**
   *  The fllowing are things to test with robolectric
   *  1. Is my title correctly displayed
   *  2. Do I have the correct number of tabs - 4
   *  3. Test that the tests has the correct title names
   *  4. Test that the icons are correct
   *
   *  On the first fragment (exercise),
   *  - test that the textview is correct when it is created newly,
   *  - test that the button has the right text
   *  - test what happens when the button is clicked
   *  - test that darwable items are not null, and have the right text
   *
   *  On the second fragment (Calendar)
   *  Test that the calendar is in view
   *
   *  On the third fragment
   *   - test that no data is shown when it is created
   *   - Supply data to the database
   *   - test that the no data text isn't visible again
   *   - test that the no data text is the right text
   *
   *   On the settings fragment
   *   - test that there are the right text
   *   - change options
   * */

  @Test
  public void validateTitle() {
    assertTrue(homeDashboard.getTitle().toString().equals("PushFit"));
  }

  @Test
  public void validNumberOfTabs() {
    assertNotNull(viewPager);
  }

  /*@Test
  public void testFirstFragment() {
    viewPager.setCurrentItem(0);

    Fragment exerciseFragment =
        homeDashboard.getSupportFragmentManager().findFragmentById(R.id.exercise_fragment);

    assertNotNull(exerciseFragment);
    *//*FragmentManager fragmentManager = homeDashboard.getSupportFragmentManager();
    fragmentManager.beginTransaction().add(exerciseFragment, null).commit();
    homeDashboard.getSupportFragmentManager().executePendingTransactions();*//*

    //assertNotNull(exerciseFragment);
  }*/


}
