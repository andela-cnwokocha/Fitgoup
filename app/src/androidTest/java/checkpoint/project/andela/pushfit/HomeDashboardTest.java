package checkpoint.project.andela.pushfit;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
/**
 * Created by andela on 23/03/2016.
 */
public class HomeDashboardTest {

  /*@Rule
  public ActivityTestRule<HomeDashboard> trashList = new ActivityTestRule<>(HomeDashboard.class);

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
    onView(withText("Settings"))
        .check(matches(isDisplayed()));
    onView(withText("Settings"))
        .perform(click());
    onView(withText("Push-ups Countdown"))
        .perform(click());
    onView(withText("Exercise"))
        .perform(click());
    onView(withText("0"))
        .check(matches(isDisplayed()));
    onView(withText("Statistics"))
        .check(matches(isDisplayed()));
    onView(withText("Statistics"))
        .perform(click());
  }*/
}
