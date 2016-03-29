package checkpoint.project.andela.pushfit;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.TextView;


import checkpoint.project.andela.pushfit.activities.HomeDrawer;
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
  private ShadowActivity homedashboardShadow;
  private DrawerLayout drawerLayout;


  @Before
  public void setUp() {
    homeDashboard = Robolectric.setupActivity(HomeDrawer.class);
    homedashboardShadow = Shadows.shadowOf(homeDashboard);
    drawerLayout = (DrawerLayout) homeDashboard.findViewById(R.id.drawer_layout);
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

    testFragment(exerciseFragment);

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
  }

  @Test
  public void testSelectNavItemToChangeFragment() {
    assertNotNull(drawerLayout);
    MenuItem menuItem = new RoboMenuItem(R.id.stats);
    assertNotNull(menuItem);

    homeDashboard.onNavigationItemSelected(menuItem);
    assertTrue(homeDashboard.getTitle().toString().equals("Reports"));
  }

  private void testFragment(Fragment fragment) {
    Assert.assertThat(fragment, CoreMatchers.not(CoreMatchers.nullValue()));
    Assert.assertThat(fragment.getView(), CoreMatchers.not(CoreMatchers.nullValue()));
    Assert.assertThat(fragment.getActivity(), CoreMatchers.not(CoreMatchers.nullValue()));
    Assert.assertThat(fragment.getActivity(), CoreMatchers.instanceOf(FragmentActivity.class));
    Assert.assertThat(fragment.getActivity(), CoreMatchers.instanceOf(HomeDrawer.class));
  }

}
