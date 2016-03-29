package checkpoint.project.andela.pushfit;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import checkpoint.project.andela.pushfit.activities.HomeDrawer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
/**
 * Created by andela on 23/03/2016.
 */
public class HomeDashboardTest {

  @Rule
  public ActivityTestRule<HomeDrawer> trashList = new ActivityTestRule<>(HomeDrawer.class);

  @Test
  public void testAppIntroSlide() {
    onView(withText("PushFit"))
        .check(matches(isDisplayed()));
    onView(withText("SKIP"))
        .perform(click());

    onView(withText("00:05:00"))
        .check(matches(isDisplayed()));
    onView(withText("Start"))
        .check(matches(isDisplayed()));
    onView(withText("Start"))
        .perform(click());
    onView(withText("Stop"))
        .check(matches(isDisplayed()));
    onView(withText("Stop"))
        .perform(click());

    onView(withContentDescription("Open navigation drawer"))
        .perform(click());
    onView(withText("Settings"))
        .check(matches(isDisplayed()));
    onView(withText("Reports"))
        .check(matches(isDisplayed()));
    onView(withText("Calendar"))
        .check(matches(isDisplayed()));
    onView(withText("Push up"))
        .check(matches(isDisplayed()));

    onView(withText("Reports"))
        .perform(click());
    onView(withText("No Push up data yet"))
        .check(matches(isDisplayed()));
  }

}
