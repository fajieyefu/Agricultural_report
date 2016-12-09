package fajieyefu.com.agricultural_report.bean;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by qiancheng on 2016/11/16.
 */
public class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		ActivityCollector.addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
}
