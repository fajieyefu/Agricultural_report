package fajieyefu.com.agricultural_report.audit;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fajieyefu.com.agricultural_report.R;
import fajieyefu.com.agricultural_report.adapter.AddFilesAdapter;
import fajieyefu.com.agricultural_report.adapter.SpinnerAdapter;
import fajieyefu.com.agricultural_report.bean.BaseActivity;
import fajieyefu.com.agricultural_report.bean.CacheForInfo;
import fajieyefu.com.agricultural_report.bean.CommonData;
import fajieyefu.com.agricultural_report.bean.FilesInfo;
import fajieyefu.com.agricultural_report.bean.ImageFactory;
import fajieyefu.com.agricultural_report.bean.Processes;
import fajieyefu.com.agricultural_report.bean.ResponseBean;
import fajieyefu.com.agricultural_report.layout.TitleLayout;
import fajieyefu.com.agricultural_report.util.RespnseCallBack;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by qiancheng on 2016/12/2.
 */
public class IncomeApplyActivity extends BaseActivity {
	@BindView(R.id.title)
	TitleLayout title;
	@BindView(R.id.work_area)
	TextView workArea;
	@BindView(R.id.apply_content)
	EditText applyContent;
	@BindView(R.id.contract_code)
	EditText contractCode;
	@BindView(R.id.apply_amt)
	EditText applyAmt;
	@BindView(R.id.deposit_time)
	TextView depositTime;
	@BindView(R.id.deposit_bank)
	TextView depositBank;
	@BindView(R.id.save)
	Button save;
	@BindView(R.id.commit)
	Button commit;
	@BindView(R.id.parent)
	LinearLayout parent;
	private String username;
	private String password;
	private AddFilesAdapter addFilesAdapter;
	private List<FilesInfo> list;
	private ArrayList<String> mPhotoList = new ArrayList<>();
	private final static int REQUEST_IMAGE = 1;
	private List<Processes> processes = new ArrayList<>();
	private ListView listView;
	private Dialog dialog;
	private String bankName;
	private String bankCode;
	private PopupWindow popupWindow;
	private int year, month, day;
	private String code;
	private String TAG="IncomeApplyActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.income_apply_layout);
		ButterKnife.bind(this);
		initView();
		initData();
	}

	private void initView() {
		CacheForInfo cache = CacheForInfo.getInstance(this);
		username = cache.getUsername();
		password = cache.getPassword();
		Calendar c =Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		title.setTitleText("新增收入申报单");
	}

	private void initData() {
		Map<String, String> map = new HashMap<>();
		map.put("username", username);
		map.put("password", password);
		JSONObject jsonObject = new JSONObject(map);
		OkHttpUtils.postString()
				.url(CommonData.ADD_INCOME)
				.content(jsonObject.toString())
				.mediaType(MediaType.parse("application/json;charset=utf-8"))
				.build()
				.execute(new MyCallBack());
	}

	@OnClick({R.id.deposit_time, R.id.deposit_bank, R.id.save, R.id.commit})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.deposit_time:
				initDatePicker(depositTime);
				break;
			case R.id.deposit_bank:
				initApplyTypeDialog();
				break;
			case R.id.save:
				postToServer(CommonData.SAVE_INCOME);
				break;
			case R.id.commit:
				postToServer(CommonData.SUBMIT_INCOME);
				break;
		}
	}

	private void postToServer(String urlPath) {

		String content = applyContent.getText().toString();
		String contract_code = contractCode.getText().toString();
		String apply_amt = applyAmt.getText().toString();
		String deposit_time=depositTime.getText().toString();
		if (TextUtils.isEmpty(content)) {
			Toast.makeText(IncomeApplyActivity.this, "请输入收入内容", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(contract_code)) {
			Toast.makeText(IncomeApplyActivity.this, "请输入合同号", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(apply_amt)) {
			Toast.makeText(IncomeApplyActivity.this, "请输入收入金额", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(deposit_time)) {
			Toast.makeText(IncomeApplyActivity.this, "请选择存入日期", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(bankCode)) {
			Toast.makeText(IncomeApplyActivity.this, "请选择存款账户", Toast.LENGTH_SHORT).show();
			return;
		}
		Map<String,String> map = new HashMap<>();
		map.put("username",username);
		map.put("password",password);
		map.put("code",code);
		map.put("account",apply_amt);
		map.put("saveDate",deposit_time);
		map.put("accountNo",bankCode);
		map.put("accountName",bankName);
		map.put("contractCode",contract_code);
		map.put("content",content);
		JSONObject jsonObject = new JSONObject(map);
		OkHttpUtils.postString()
				.url(urlPath)
				.content(jsonObject.toString())
				.mediaType(MediaType.parse("application/json;charset=utf-8"))
				.build()
				.execute(new MyStringCallBack());

	}

	private void initDatePicker(final TextView editText) {
		final int year_temp = year;
		final int month_temp = month;
		final int day_temp = day;

		View datePickerLayout = LayoutInflater.from(this).inflate(R.layout.date_picker, null);

		DatePicker datePicker = (DatePicker) datePickerLayout.findViewById(R.id.date_picker);
		Button confirm = (Button) datePickerLayout.findViewById(R.id.confirm);
		Button cancel = (Button) datePickerLayout.findViewById(R.id.cancel);
		confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editText.setText(year + "-" + (month + 1) + "-" + day);
				if (popupWindow != null) {
					popupWindow.dismiss();
				}

			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (popupWindow != null) {
					popupWindow.dismiss();
				}
				year = year_temp;
				month = month_temp;
				day = day_temp;
			}
		});
		datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				IncomeApplyActivity.this.year = year;
				IncomeApplyActivity.this.month = monthOfYear;
				IncomeApplyActivity.this.day = dayOfMonth;

			}
		});
		popupWindow = new PopupWindow(datePickerLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.input_rect_white));
		//为popWindow添加动画效果
		popupWindow.setAnimationStyle(R.style.popWindow_animation);
		// 点击弹出泡泡窗口
		popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);

	}


	private class MyCallBack extends RespnseCallBack {
		@Override
		public void onError(Call call, Exception e, int id) {

		}

		@Override
		public void onResponse(ResponseBean response, int id) {
			if (response.getCode() == 0) {
				processes.addAll(response.getObj().getBanks());
				workArea.setText(response.getObj().getPname());
				code=response.getObj().getCode();
			}
		}
	}


	private void initPhotoList() {
		int size = list.size();
		if (size == 1) {
			return;
		}
		mPhotoList.clear();
		for (int i = 1; i < size; i++) {
			mPhotoList.add(list.get(i).getAdd_file_path());
		}
	}

	private void initApplyTypeDialog() {
		dialog = new Dialog(IncomeApplyActivity.this);
		View view = LayoutInflater.from(IncomeApplyActivity.this).inflate(R.layout.spinner_dialog, null);
		listView = (ListView) view.findViewById(R.id.spinner_list);
		SpinnerAdapter spinnerAdapter = new SpinnerAdapter(IncomeApplyActivity.this, processes);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				depositBank.setText(processes.get(position).getName());
				for (Processes processes_item : processes) {
					processes_item.setSelect_flag(0);
				}
				processes.get(position).setSelect_flag(1);
				bankName = processes.get(position).getName();
				bankCode = processes.get(position).getCode();
				dialog.dismiss();
			}
		});

		listView.setAdapter(spinnerAdapter);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		dialog.show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_IMAGE) {
			if (resultCode == RESULT_OK) {
				list.clear();
				list.add(new FilesInfo());
				// 获取返回的图片列表
				List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
				// 处理你自己的逻辑 ....
				if (path.size() != 0) {
					for (int i = 0; i < path.size(); i++) {
						FilesInfo fileInfo = new FilesInfo();
						fileInfo.setAdd_file_path(path.get(i));
						list.add(fileInfo);
					}

				}
				addFilesAdapter.notifyDataSetChanged();
			}
		}
	}

	private class MyStringCallBack extends RespnseCallBack {


		@Override
		public void onError(Call call, Exception e, int id) {
			Toast.makeText(IncomeApplyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onResponse(ResponseBean response, int id) {
			if (response.getCode() == 0) {
				Toast.makeText(IncomeApplyActivity.this, "单据处理成功", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(IncomeApplyActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
			}
		}
	}
}
