package com.example.andela.fitgoup.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.andela.fitgoup.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class AppIntroActivity extends AppIntro {

  @Override
  public void init(Bundle savedInstanceState) {
    addSlide(AppIntroFragment.newInstance("PushFit", getString(R.string.pushfit_slide1_description),
        R.drawable.ic_calendar, Color.parseColor("#607D8B")));

    addSlide(AppIntroFragment.newInstance("How to use", getString(R.string.appintro_slide2_howtouse),
        R.drawable.ic_calendar, Color.parseColor("#607D8B")));

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
    Intent homedashboard = new Intent(this, HomeDashboard.class);
    startActivity(homedashboard);
  }

  /*@Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_app_intro);
  }*/
}
