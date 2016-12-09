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
 * Created by qiancheng on 2016/11/30.
 */
public class ContractApplyActivity extends BaseActivity {

	@BindView(R.id.title)
	TitleLayout title;
	@BindView(R.id.work_area)
	TextView workArea;
	@BindView(R.id.apply_content)
	EditText applyContent;
	@BindView(R.id.apply_type)
	TextView applyType;
	@BindView(R.id.contract_type)
	TextView contractType;
	@BindView(R.id.contract_name)
	EditText contractName;
	@BindView(R.id.apply_amt)
	EditText applyAmt;
	@BindView(R.id.start_time)
	TextView startTime;
	@BindView(R.id.end_time)
	TextView endTime;
	@BindView(R.id.contract_A)
	EditText contractA;
	@BindView(R.id.files_rv)
	RecyclerView filesRv;
	@BindView(R.id.save)
	Button save;
	@BindView(R.id.commit)
	Button commit;
	@BindView(R.id.parent)
	LinearLayout parent;
	@BindView(R.id.contract_B)
	EditText contractB;
	private AddFilesAdapter addFilesAdapter;
	private List<FilesInfo> list;
	private ArrayList<String> mPhotoList = new ArrayList<>();
	private final static int REQUEST_IMAGE = 1;
	private List<Processes> processes = new ArrayList<>();
	private List<Processes> businessTypes = new ArrayList<>();
	private ListView listView;
	private Dialog dialog;
	private static final String TAG = "ContractApplyActivity";
	private String urlPath;
	private String username;
	private String password;
	private float account;
	private String businessCode;
	private String businessName;
	private String code;
	private String contractcode;
	private String requestcode;
	private String processCode;
	private String processName;
	private String content;
	private String processType;
	private String contract_type;
	private int year;
	private int month;
	private int day;
	private PopupWindow popupWindow;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contract_apply_layout);
		ButterKnife.bind(this);
		initData();
		initFiles();

	}

	private void initData() {
		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		title.setTitleText("新增合同预审单");
		CacheForInfo cache = CacheForInfo.getInstance(this);
		username = cache.getUsername();
		password = cache.getPassword();
		Map<String, String> map = new HashMap<>();
		map.put("username", username);
		map.put("password", password);
		JSONObject json = new JSONObject(map);
		OkHttpUtils.postString()
				.url(CommonData.ADD_CONTRACT)
				.content(json.toString())
				.mediaType(MediaType.parse("application/json;charset=utf-8"))
				.build()
				.execute(new MyCallBack());
	}

	private void initFiles() {
		list = new ArrayList<>();
		FilesInfo filesInfo = new FilesInfo();
		list.add(filesInfo);
		addFilesAdapter = new AddFilesAdapter(list, this);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		//默认为vertical，可以不设置
		layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		filesRv.setLayoutManager(layoutManager);
		filesRv.setAdapter(addFilesAdapter);
		addFilesAdapter.setClickListener(new AddFilesAdapter.OnItemClickListener() {
											 @Override
											 public void onClick(View view, int position) {
												 switch (position) {
													 case 0:
														 initPhotoList();
														 MultiImageSelector.create(ContractApplyActivity.this)
																 .showCamera(true)
																 .count(10)
																 .multi()//设置为多选模式，.single为单选模式
																 .origin(mPhotoList).start(ContractApplyActivity.this, REQUEST_IMAGE);
														 break;

												 }
											 }
										 }

		);

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


	private void postToServer() {

		String account_String = applyAmt.getText().toString();
		content = applyContent.getText().toString();
		String start_time = startTime.getText().toString();
		String end_time = startTime.getText().toString();
		String contract_A=contractA.getText().toString();
		String contract_B=contractB.getText().toString();
		String contract_name=contractName.getText().toString();
		if (TextUtils.isEmpty(account_String)) {
			Toast.makeText(ContractApplyActivity.this, "请输入金额", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(content)) {
			Toast.makeText(ContractApplyActivity.this, "请输入申请内容", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(contract_type)) {
			Toast.makeText(ContractApplyActivity.this, "请选择合同类型", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(processCode)) {
			Toast.makeText(ContractApplyActivity.this, "请选择申请类型", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(contract_name)) {
			Toast.makeText(ContractApplyActivity.this, "请输入合同名称", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(start_time)) {
			Toast.makeText(ContractApplyActivity.this, "请选择合同开始时间", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(end_time)) {
			Toast.makeText(ContractApplyActivity.this, "请选择合同结束时间", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(contract_A) ){
			Toast.makeText(ContractApplyActivity.this, "请输入合同甲方", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(contract_B)) {
			Toast.makeText(ContractApplyActivity.this, "请输入合同乙方", Toast.LENGTH_SHORT).show();
			return;
		}
		Map<String, File> map = new HashMap<>();
		Bitmap bitmap = null;
		ImageFactory imageFactory = new ImageFactory();
		if (list.size() > 1) {
			for (int i = 1; i < list.size(); i++) {
				File file = new File(CommonData.PIC_TEMP + i + ".jpg");
				bitmap = imageFactory.getBitmap(list.get(i).getAdd_file_path());
				ImageFactory.compressBmpToFile(bitmap, file);
				map.put(i + ".jpg", file);
			}
		}
		Log.i(TAG, map.size() + "");
		account = Float.parseFloat(account_String);
		OkHttpUtils.post()
				.url(urlPath)
				.files("file", map)
				.addParams("username", username)
				.addParams("password", password)
				.addParams("code", code)
				.addParams("processCode", processCode)
				.addParams("processName", processName)
				.addParams("processType", processType)
				.addParams("type",contract_type)
				.addParams("name",contract_name)
				.addParams("amt",account_String)
				.addParams("startDate",start_time)
				.addParams("endDate",end_time)
				.addParams("contractA",contract_A)
				.addParams("contractB",contract_B)
				.addParams("content",content)
				.build()
				.execute(new MyStringCallBack());

	}

	private void initApplyTypeDialog() {
		dialog = new Dialog(ContractApplyActivity.this);
		View view = LayoutInflater.from(ContractApplyActivity.this).inflate(R.layout.spinner_dialog, null);
		listView = (ListView) view.findViewById(R.id.spinner_list);
		SpinnerAdapter spinnerAdapter = new SpinnerAdapter(ContractApplyActivity.this, processes);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				applyType.setText(processes.get(position).getName());
				for (Processes processes_item : processes) {
					processes_item.setSelect_flag(0);
				}
				processes.get(position).setSelect_flag(1);
				processName = processes.get(position).getName();
				processCode = processes.get(position).getCode();
				processType = processes.get(position).getType();
				dialog.dismiss();
			}
		});

		listView.setAdapter(spinnerAdapter);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		dialog.show();

	}

	private void initServiceTypeDialog() {
		dialog = new Dialog(ContractApplyActivity.this);
		View view = LayoutInflater.from(ContractApplyActivity.this).inflate(R.layout.spinner_dialog, null);
		listView = (ListView) view.findViewById(R.id.spinner_list);
		SpinnerAdapter spinnerAdapter = new SpinnerAdapter(ContractApplyActivity.this, businessTypes);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				contractType.setText(businessTypes.get(position).getType());
				for (Processes processes_item : businessTypes) {
					processes_item.setSelect_flag(0);
				}
				businessTypes.get(position).setSelect_flag(1);
				contract_type = businessTypes.get(position).getType();
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

	@OnClick({R.id.apply_type, R.id.contract_type, R.id.start_time, R.id.end_time, R.id.save, R.id.commit})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.apply_type:
				initApplyTypeDialog();
				break;
			case R.id.contract_type:
				initServiceTypeDialog();
				break;
			case R.id.start_time:
				initDatePicker(startTime);
				break;
			case R.id.end_time:
				initDatePicker(endTime);
				break;
			case R.id.save:
				urlPath=CommonData.SAVE_CONTRACT;
				postToServer();
				break;
			case R.id.commit:
				urlPath=CommonData.SUBMIT_CONTRACT;
				postToServer();
				break;
		}
	}


	private class MyStringCallBack extends RespnseCallBack {


		@Override
		public void onError(Call call, Exception e, int id) {


		}

		@Override
		public void onResponse(ResponseBean response, int id) {
			if (response.getCode() == 0) {
				Toast.makeText(ContractApplyActivity.this, "单据处理成功", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(ContractApplyActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class MyCallBack extends RespnseCallBack {
		@Override
		public void onError(Call call, Exception e, int id) {

		}

		@Override
		public void onResponse(ResponseBean response, int id) {
			if (response.getCode() == 0) {
				code = response.getObj().getCode();
				processes.addAll(response.getObj().getProcesses());
				businessTypes.addAll(response.getObj().getBusinessTypes());
				workArea.setText(response.getObj().getPname());
			} else {
				Toast.makeText(ContractApplyActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
			}

		}
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
				editText.setText(year + "-" + (month + 1) + "-" + day );
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
				ContractApplyActivity.this.year = year;
				ContractApplyActivity.this.month = monthOfYear;
				ContractApplyActivity.this.day = dayOfMonth;

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
}
