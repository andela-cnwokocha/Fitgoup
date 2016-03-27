package checkpoint.project.andela.pushfit.activities;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import checkpoint.project.andela.pushfit.R;
import checkpoint.project.andela.pushfit.fragments.SettingsFragment;

public class SettingActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting);

    Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
    setSupportActionBar(toolbar);
    setTitle("Settings");
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
  public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId() == android.R.id.home){
          finish();
      }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    finish();
  }

}
