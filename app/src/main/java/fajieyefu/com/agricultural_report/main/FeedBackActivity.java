package fajieyefu.com.agricultural_report.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fajieyefu.com.agricultural_report.R;
import fajieyefu.com.agricultural_report.bean.BaseActivity;
import fajieyefu.com.agricultural_report.bean.BeanUtil;
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
public class FeedBackActivity extends BaseActivity {
	@BindView(R.id.content)
	EditText content;
	@BindView(R.id.phone)
	EditText phone;
	@BindView(R.id.submit)
	Button submit;
	@BindView(R.id.title)
	TitleLayout title;
	private String contentText;
	private String phoneNum;
	private String username;
	private String password;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback_layout);
		ButterKnife.bind(this);
		initView();

	}

	private void initView() {
		title.setTitleText("意见反馈");
		CacheForInfo cache = CacheForInfo.getInstance(this);
		username=cache.getUsername();
		password=cache.getPassword();

	}

	@OnClick(R.id.submit)
	public void onClick() {
		contentText = content.getText().toString();
		phoneNum = phone.getText().toString();
		if (TextUtils.isEmpty(contentText)) {
			Toast.makeText(FeedBackActivity.this, "请输入您的反馈意见", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(phoneNum) || !BeanUtil.isMobileNO(phoneNum)) {
			Toast.makeText(FeedBackActivity.this, "请输入合法的手机号码", Toast.LENGTH_SHORT).show();
		}
		postToServer();

	}

	private void postToServer() {
		Map<String, String> map = new HashMap<>();
		map.put("username",username);
		map.put("password",password);
		JSONObject jsonObject = new JSONObject(map);
		OkHttpUtils.postString()
				.url(CommonData.FEEDBACK)
				.content(jsonObject.toString())
				.mediaType(MediaType.parse("application/json;charset=utf-8"))
				.build()
				.execute(new MyCallBack());
	}


	private class MyCallBack extends RespnseCallBack {
		@Override
		public void onError(Call call, Exception e, int id) {
			Toast.makeText(FeedBackActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onResponse(ResponseBean response, int id) {
			if (response.getCode()==0){
				Toast.makeText(FeedBackActivity.this, "意见反馈成功，非常感谢！", Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(FeedBackActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
			}

		}
	}
}
