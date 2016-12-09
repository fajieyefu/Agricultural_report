package fajieyefu.com.agricultural_report.bean;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by qiancheng on 2016/10/11.
 */
public class CacheForInfo {
	private String username;
	private String name;
	private String orgName;
	private String orgCode;
	private String realname;
	private String password;
	private String birthday;
	private String hiredate;
	private String email;
	private String phone;
	private String idcard;
	private String address;
	private static Context mContext;

	private String psw_md5;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private static CacheForInfo cacheForInfo = null;

	public static CacheForInfo getInstance(Context context) {
		mContext = context;
		if (cacheForInfo == null) {
			cacheForInfo = new CacheForInfo();
		}
		return cacheForInfo;
	}


	private CacheForInfo() {
		pref = mContext.getSharedPreferences("cache", Context.MODE_PRIVATE | Context.MODE_APPEND);
		editor = pref.edit();
		username = pref.getString("username", "");
		phone = pref.getString("phone", "");
		name = pref.getString("name", "");
		psw_md5 = pref.getString("psw_md5", "");
		orgName = pref.getString("orgName", "");
		orgCode = pref.getString("orgCode", "");
		realname = pref.getString("realname", "");
		password = pref.getString("password", "");
		birthday = pref.getString("birthday", "");
		hiredate = pref.getString("hiredate", "");
		email = pref.getString("email", "");
		idcard = pref.getString("idcard", "");
		address = pref.getString("address", "");

	}


	public void setName(String name) {
		editor.putString("name", name);
		editor.apply();
	}

	public void setUsername(String username) {
		editor.putString("username", username);
		editor.apply();
	}

	public void setPhone(String phone) {
		editor.putString("phone", phone);
		editor.apply();
	}

	public String getName() {
		return name;
	}

	public String getUsername() {
		return username;
	}

	public String getPhone() {
		return phone;
	}

	public String getPsw_md5() {
		return psw_md5;
	}

	public void setPsw_md5(String psw_md5) {
		editor.putString("psw_md5", psw_md5);
		editor.apply();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		editor.putString("address", address);
		editor.apply();
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		editor.putString("birthday", birthday);
		editor.apply();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		editor.putString("email", email);
		editor.apply();

	}

	public String getHiredate() {
		return hiredate;
	}

	public void setHiredate(String hiredate) {
		editor.putString("hiredate", hiredate);
		editor.apply();
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		editor.putString("idcard", idcard);
		editor.apply();
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		editor.putString("orgCode", orgCode);
		editor.apply();
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		editor.putString("orgName", orgName);
		editor.apply();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		editor.putString("password", password);
		editor.apply();
	}


	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		editor.putString("realname", realname);
		editor.apply();
	}
}
