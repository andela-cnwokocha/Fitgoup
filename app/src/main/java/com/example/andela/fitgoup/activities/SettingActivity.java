package com.example.andela.fitgoup.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.andela.fitgoup.R;
import com.example.andela.fitgoup.fragments.SettingsFragment;

public class SettingActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting);

    Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.left);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });

    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.preference_area, new SettingsFragment()).commit();
  }

  @Override
  public void onBackPressed() {
    Intent homeIntent = new Intent(this, HomeDrawer.class);
    startActivity(homeIntent);
  }


}
