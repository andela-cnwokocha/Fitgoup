package checkpoint.project.andela.pushfit.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Chidi on 3/8/16.
 */

@Table(name = "PushUp")
public class PushUpModel extends Model {

  @Column(name = "day_pushup")
  public long pushups;

  @Column(name = "current_day")
  public String currentDay;

  public PushUpModel () {
    super();
  }

  public PushUpModel(long pushup, String day) {
    super();
    this.pushups = pushup;
    this.currentDay = day;
  }

  public static List<PushUpModel> fetchPushups() {
    return new Select().all().from(PushUpModel.class).execute();
  }

  public static void clearData() {
    new Delete().from(PushUpModel.class).execute();
  }
}
