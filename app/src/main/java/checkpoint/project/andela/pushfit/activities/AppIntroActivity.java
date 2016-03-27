package checkpoint.project.andela.pushfit.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import checkpoint.project.andela.pushfit.R;
import checkpoint.project.andela.pushfit.utils.FragmentSlide;
import com.github.paolorotolo.appintro.AppIntro;

public class AppIntroActivity extends AppIntro {

  @Override
  public void init(Bundle savedInstanceState) {
    addSlide(FragmentSlide.newInstance(R.layout.intro_slide));
    addSlide(FragmentSlide.newInstance(R.layout.intro2_slide));

    setBarColor(Color.parseColor("#000000"));
    setSeparatorColor(Color.parseColor("#B6B6B6"));
    showSkipButton(true);
    showStatusBar(true);
    setProgressButtonEnabled(true);

    setSlideOverAnimation();
  }

  @Override
  public void onSkipPressed() {
    Toast.makeText(getApplicationContext(), getString(R.string.skip_tutorial), Toast.LENGTH_LONG).show();
    loadActivity();
  }

  @Override
  public void onNextPressed() {

  }

  @Override
  public void onDonePressed() {
    loadActivity();
  }

  @Override
  public void onSlideChanged() {

  }

  private void loadActivity() {
    Intent homedashboard = new Intent(this, HomeDrawer.class);
    startActivity(homedashboard);
  }
}
