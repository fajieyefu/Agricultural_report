package fajieyefu.com.agricultural_report.main;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fajieyefu.com.agricultural_report.R;
import fajieyefu.com.agricultural_report.bean.BaseActivity;
import fajieyefu.com.agricultural_report.bean.CacheForInfo;
import fajieyefu.com.agricultural_report.bean.CommonData;
import fajieyefu.com.agricultural_report.bean.ResponseBean;
import fajieyefu.com.agricultural_report.layout.TitleLayout;
import fajieyefu.com.agricultural_report.util.RespnseCallBack;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by qiancheng on 2016/12/5.
 */
public class MyInfoActivity extends BaseActivity {
	@BindView(R.id.title)
	TitleLayout title;
	@BindView(R.id.true_name)
	TextView trueName;
	@BindView(R.id.birthday)
	TextView birthday;
	@BindView(R.id.entry_time)
	TextView entryTime;
	@BindView(R.id.email)
	TextView email;
	@BindView(R.id.phone)
	TextView phone;
	@BindView(R.id.identify)
	TextView identify;
	@BindView(R.id.address)
	TextView address;
	@BindView(R.id.submit)
	Button submit;
	private String username;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfo);
		ButterKnife.bind(this);
		initData();

	}

	private void initData() {
		CacheForInfo cache = CacheForInfo.getInstance(this);
		username=cache.getUsername();
		password=cache.getPassword();
		Map<String,String> map = new HashMap<>();
		map.put("username",username);
		map.put("password",password);
		JSONObject jsonObject = new JSONObject(map);
		OkHttpUtils.postString()
				.url(CommonData.MY_INFO)
				.content(jsonObject.toString())
				.mediaType(MediaType.parse("application/json;charset=utf-8"))
				.build()
				.execute(new MyCallBack());
	}

	@OnClick(R.id.submit)
	public void onClick() {
	}

	private class MyCallBack extends RespnseCallBack {
		@Override
		public void onError(Call call, Exception e, int id) {

		}

		@Override
		public void onResponse(ResponseBean response, int id) {
			if (response.getCode()==0){

			}
		}
	}
}
