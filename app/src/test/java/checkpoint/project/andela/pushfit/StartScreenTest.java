package checkpoint.project.andela.pushfit;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.TextView;


import checkpoint.project.andela.pushfit.activities.HomeDrawer;
import checkpoint.project.andela.pushfit.activities.SettingActivity;
import checkpoint.project.andela.pushfit.fragments.CalendarFragment;
import checkpoint.project.andela.pushfit.fragments.ExerciseFragment;
import checkpoint.project.andela.pushfit.fragments.StatisticsFragment;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.Shadows;;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by andela on 23/03/2016.
 */


@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class StartScreenTest {
  private HomeDrawer homeDashboard;
  private ShadowActivity homwdashboardShadow;


  @Before
  public void setUp() {
    homeDashboard = Robolectric.setupActivity(HomeDrawer.class);
    homwdashboardShadow = Shadows.shadowOf(homeDashboard);
  }

  @Test
  public void validateTitle() {
    assertTrue(homeDashboard.getTitle().toString().equals("PushFit"));
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
    Assert.assertThat(exerciseFragment.getActivity(), CoreMatchers.instanceOf(HomeDrawer.class));

    TextView timerview = (TextView) homeDashboard.findViewById(R.id.timer_field);
    assertTrue(timerview.getText().equals("00:05:00"));
    TextView buttonText = (TextView) homeDashboard.findViewById(R.id.fragment_exercise);
    assertTrue(buttonText.getText().equals("Start"));
  }

  @Test
  public void calendarFragmentTest() {
    CalendarFragment calendarFragment = new CalendarFragment();
    SupportFragmentTestUtil.startFragment(calendarFragment);
    assertNotNull(calendarFragment);
  }

  @Test
  public void statisticsFragmentTest() {
    StatisticsFragment statisticsFragment = new StatisticsFragment();
    SupportFragmentTestUtil.startFragment(statisticsFragment);
    assertNotNull(statisticsFragment);
  }

  @Test
  public void testOptionsMenuActions() {
    MenuItem menuItem = new RoboMenuItem(R.id.action_settings);
    assertNotNull(menuItem);
    homeDashboard.onOptionsItemSelected(menuItem);
   /* ShadowActivity.IntentForResult expectedIntent = new ShadowActivity.IntentForResult(homeDashboard, SettingActivity
        .class);*/
    //assertTrue(homwdashboardShadow.getNextStartedActivityForResult().equals(expectedIntent));
  }


}
