package com.example.andela.fitgoup.model;

import android.database.Cursor;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by Chidi on 3/8/16.
 */

@Table(name = "PushUp")
public class PushUpModel extends Model {

  @Column(name = "day_pushup")
  public int pushups;

  public PushUpModel () {
    super();
  }

  public PushUpModel(int pushup) {
    super();
    this.pushups = pushup;
  }

  public static List<PushUpModel> fetchPushups() {
    return new Select().all().from(PushUpModel.class).execute();
  }
}
