package fajieyefu.com.agricultural_report.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fajieyefu.com.agricultural_report.R;
import fajieyefu.com.agricultural_report.bean.BaseActivity;
import fajieyefu.com.agricultural_report.bean.CacheForInfo;
import fajieyefu.com.agricultural_report.service.WebService;
import fajieyefu.com.agricultural_report.util.Md5Encoding;

/**
 * Created by qiancheng on 2016/11/22.
 */
public class Login_Activity extends BaseActivity implements View.OnClickListener {
	private EditText userName;
	private EditText psw;
	private String user_text, psw_text;
	private Button login;
	private SharedPreferences pref_login;
	private SharedPreferences.Editor editor_login;
	private CheckBox checkBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		findById();


	}

	private void findById() {
		login = (Button) findViewById(R.id.login);
		userName = (EditText) findViewById(R.id.login_user);
		psw = (EditText) findViewById(R.id.login_psw);
		checkBox = (CheckBox) findViewById(R.id.checkbox);
		pref_login = this.getSharedPreferences("login_info", MODE_PRIVATE);
		editor_login = pref_login.edit();
		login.setOnClickListener(this);
		boolean isRemember = pref_login.getBoolean("remember_password", false);
		if (isRemember) {
			String account = pref_login.getString("username", "");
			String pass = pref_login.getString("password", "");
			userName.setText(account);
			psw.setText(pass);
			checkBox.setChecked(true);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.login:
				user_text = userName.getText().toString();
				psw_text = psw.getText().toString();
				if (user_text.equals("") || psw_text.equals("")) {
					Toast.makeText(Login_Activity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
				} else {
					login();
				}

				break;
		}
	}

	private void login() {


		new AsyncTask<Void, Void, Integer>() {
			ProgressDialog dialog;
			String msg;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				dialog = new ProgressDialog(Login_Activity.this);
				dialog.setMessage("正在登录");
				dialog.show();
			}

			@Override
			protected Integer doInBackground(Void... params) {
				int code;
				Map<String, String> map = new HashMap<>();
				String psw_Md5 = Md5Encoding.md5(psw_text);
				map.put("username", user_text);
				map.put("password", psw_Md5);
				String info = WebService.login(map);
				try {
					JSONObject jsonObject = new JSONObject(info);
					code = jsonObject.optInt("code");
					msg = jsonObject.optString("msg");
					if (code == 0) {
						jsonObject = jsonObject.optJSONObject("obj");
						String orgName = jsonObject.optString("orgName");
						String orgCode = jsonObject.optString("code");
						String realname = jsonObject.optString("realname");
						String password = jsonObject.optString("password");
						String birthday = jsonObject.optString("brithday");
						String hiredate = jsonObject.optString("hiredate");
						String email = jsonObject.optString("email");
						String phone = jsonObject.optString("phone");
						String idcard = jsonObject.optString("idcard");
						String address = jsonObject.optString("address");
						CacheForInfo cache = CacheForInfo.getInstance(Login_Activity.this);
						cache.setOrgName(orgName);
						cache.setOrgCode(orgCode);
						cache.setRealname(realname);
						cache.setPassword(password);
						cache.setBirthday(birthday);
						cache.setBirthday(hiredate);
						cache.setBirthday(email);
						cache.setBirthday(phone);
						cache.setBirthday(idcard);
						cache.setBirthday(address);
						cache.setUsername(user_text);

					} else {
						code = 1;
					}
				} catch (JSONException e) {
					code = 1;
					msg = e.getMessage();
				}
				return code;
			}

			@Override
			protected void onPostExecute(Integer integer) {
				if (dialog != null) {
					dialog.dismiss();
				}
				switch (integer) {
					case 0:
						if (checkBox.isChecked()) {//檢查复选框是否选中
							editor_login.putBoolean("remember_password", true);
							editor_login.putString("username", user_text);
							editor_login.putString("password", psw_text);
						} else {
							editor_login.clear();
						}
						editor_login.apply();
						Intent intent = new Intent(Login_Activity.this, MainActivity.class);
						startActivity(intent);
						finish();
						break;
					case 1:
						Toast.makeText(Login_Activity.this, msg, Toast.LENGTH_SHORT).show();
						break;
				}
			}
		}.execute();
	}

}
