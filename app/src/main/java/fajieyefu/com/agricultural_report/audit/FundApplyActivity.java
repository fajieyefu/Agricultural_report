package fajieyefu.com.agricultural_report.audit;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import fajieyefu.com.agricultural_report.bean.MultipartRequest;
import fajieyefu.com.agricultural_report.bean.Processes;
import fajieyefu.com.agricultural_report.bean.ResponseBean;
import fajieyefu.com.agricultural_report.layout.TitleLayout;
import fajieyefu.com.agricultural_report.util.RespnseCallBack;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;

/**
 * Created by qiancheng on 2016/11/30.
 */
public class FundApplyActivity extends BaseActivity {
	@BindView(R.id.title)
	TitleLayout title;
	@BindView(R.id.work_area)
	TextView workArea;
	@BindView(R.id.apply_content)
	EditText applyContent;
	@BindView(R.id.service_type)
	TextView serviceType;
	@BindView(R.id.apply_type)
	TextView applyType;
	@BindView(R.id.apply_amt)
	EditText applyAmt;
	@BindView(R.id.apply_code)
	EditText applyCode;
	@BindView(R.id.contract_code)
	EditText contractCode;
	@BindView(R.id.files_rv)
	RecyclerView filesRv;
	@BindView(R.id.save)
	Button save;
	@BindView(R.id.commit)
	Button commit;

	private AddFilesAdapter addFilesAdapter;
	private List<FilesInfo> list;
	private ArrayList<String> mPhotoList = new ArrayList<>();
	private final static int REQUEST_IMAGE = 1;
	private List<Processes> processes = new ArrayList<>();
	private List<Processes> businessTypes = new ArrayList<>();
	private ListView listView;
	private Dialog dialog;
	private static final String TAG = "FundApplyActivity";
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


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fund_apply_layout);
		ButterKnife.bind(this);
		initData();
		initFiles();

	}

	private void initData() {
		title.setTitleText("新增资金申请单");
		CacheForInfo cache = CacheForInfo.getInstance(this);
		username = cache.getUsername();
		password = cache.getPassword();
		RequestQueue mQueue = Volley.newRequestQueue(this);
		Map<String, String> map = new HashMap<>();
		map.put("username", username);
		map.put("password", password);
		JSONObject json = new JSONObject(map);
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, CommonData.ADD_CAPTICAL, json,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						Log.i(TAG, jsonObject.toString());
						Gson gson = new Gson();
						ResponseBean responseBean = gson.fromJson(jsonObject.toString(), ResponseBean.class);
						if (responseBean.getCode() == 0) {
							code = responseBean.getObj().getCode();
							processes.addAll(responseBean.getObj().getProcesses());
							businessTypes.addAll(responseBean.getObj().getBusinessTypes());
							workArea.setText(responseBean.getObj().getPname());
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {

					}
				}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<>();
				headers.put("Accept", "application/json");
				headers.put("Content-Type", "application/json;charset=UTF-8");
				return headers;
			}
		};
		mQueue.add(jsonRequest);
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
														 MultiImageSelector.create(FundApplyActivity.this)
																 .showCamera(true)
																 .count(10)
																 .multi()//设置为多选模式，.single为单选模式
																 .origin(mPhotoList).start(FundApplyActivity.this, REQUEST_IMAGE);
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

	@OnClick({R.id.title, R.id.work_area, R.id.apply_content, R.id.apply_type, R.id.apply_amt, R.id.apply_code, R.id.contract_code, R.id.files_rv, R.id.service_type, R.id.save, R.id.commit})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.title:
				break;
			case R.id.work_area:
				break;
			case R.id.apply_content:
				break;
			case R.id.apply_type:
				initApplyTypeDialog();
				break;
			case R.id.apply_amt:
				break;
			case R.id.apply_code:
				break;
			case R.id.contract_code:
				break;
			case R.id.files_rv:
				break;
			case R.id.service_type:
				initServiceTypeDialog();
				break;
			case R.id.save:
				urlPath = CommonData.SAVE_CAPTICAL;
				postToServer();
				break;
			case R.id.commit:
				urlPath = CommonData.SUBMIT_CAPITICAL;
				postToServer();
				break;
		}
	}

	private void postToServer() {

		String account_String = applyAmt.getText().toString();
		content = applyContent.getText().toString();
		contractcode = contractCode.getText().toString();
		requestcode = applyCode.getText().toString();
		if (TextUtils.isEmpty(account_String)) {
			Toast.makeText(FundApplyActivity.this, "请输入金额", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(content)) {
			Toast.makeText(FundApplyActivity.this, "请输入申请内容", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(businessCode)) {
			Toast.makeText(FundApplyActivity.this, "请选择业务类型", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(processCode)) {
			Toast.makeText(FundApplyActivity.this, "请选择申请类型", Toast.LENGTH_SHORT).show();
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
				.addParams("businessCode", businessCode)
				.addParams("businessName", businessName)
				.addParams("processCode", processCode)
				.addParams("processName", processName)
				.addParams("requestCode", requestcode)
				.addParams("contractCode", contractcode)
				.addParams("content", content)
				.addParams("account", account + "")
				.addParams("processType",processType)
				.build()
				.execute(new MyStringCallBack());

	}

	private void initApplyTypeDialog() {
		dialog = new Dialog(FundApplyActivity.this);
		View view = LayoutInflater.from(FundApplyActivity.this).inflate(R.layout.spinner_dialog, null);
		listView = (ListView) view.findViewById(R.id.spinner_list);
		SpinnerAdapter spinnerAdapter = new SpinnerAdapter(FundApplyActivity.this, processes);

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
				processType=processes.get(position).getType();
				dialog.dismiss();
			}
		});

		listView.setAdapter(spinnerAdapter);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		dialog.show();

	}

	private void initServiceTypeDialog() {
		dialog = new Dialog(FundApplyActivity.this);
		View view = LayoutInflater.from(FundApplyActivity.this).inflate(R.layout.spinner_dialog, null);
		listView = (ListView) view.findViewById(R.id.spinner_list);
		SpinnerAdapter spinnerAdapter = new SpinnerAdapter(FundApplyActivity.this, businessTypes);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				serviceType.setText(businessTypes.get(position).getName());
				for (Processes processes_item : businessTypes) {
					processes_item.setSelect_flag(0);
				}
				businessTypes.get(position).setSelect_flag(1);
				businessCode = businessTypes.get(position).getCode();
				businessName = businessTypes.get(position).getName();
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


		}

		@Override
		public void onResponse(ResponseBean response, int id) {
			if (response.getCode() == 0) {
				Toast.makeText(FundApplyActivity.this, "单据处理成功", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(FundApplyActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
			}
		}
	}
}
