package fajieyefu.com.agricultural_report.audit;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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

import com.google.gson.JsonObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.io.File;
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
import fajieyefu.com.agricultural_report.bean.Processes;
import fajieyefu.com.agricultural_report.bean.ResponseBean;
import fajieyefu.com.agricultural_report.layout.TitleLayout;
import fajieyefu.com.agricultural_report.util.RespnseCallBack;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by qiancheng on 2016/12/5.
 */
public class SealApplyActivity extends BaseActivity {
	@BindView(R.id.title)
	TitleLayout title;
	@BindView(R.id.work_area)
	TextView workArea;
	@BindView(R.id.apply_content)
	EditText applyContent;
	@BindView(R.id.apply_type)
	TextView applyType;
	@BindView(R.id.files_rv)
	RecyclerView filesRv;
	@BindView(R.id.save)
	Button save;
	@BindView(R.id.commit)
	Button commit;
	private String processCode;
	private String processName;
	private String processType;
	private String username;
	private String password;
	private List<Processes> processes = new ArrayList<>();
	private List<FilesInfo> list;
	private ArrayList<String> mPhotoList = new ArrayList<>();
	private AddFilesAdapter addFilesAdapter;
	private final static int REQUEST_IMAGE = 1;
	private Dialog dialog;
	private ListView listView;
	private String code;
	private String pName,cName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seal_apply_layout);
		ButterKnife.bind(this);
		initView();
		initData();
		initFiles();
	}

	private void initData() {
		Map<String,String> map = new HashMap<>();
		map.put("username",username);
		map.put("password",password);
		JSONObject jsonObject= new JSONObject(map);

		OkHttpUtils.postString()
				.url(CommonData.ADD_SEAL)
				.content(jsonObject.toString())
				.mediaType(MediaType.parse("application/json;charset=utf-8"))
				.build()
				.execute(new MyCallBack());

	}

	private void initView() {
		title.setTitleText("新增公章申请单");
		CacheForInfo cache = CacheForInfo.getInstance(SealApplyActivity.this);
		username=cache.getUsername();
		password=cache.getPassword();
		workArea.setText(pName+"("+cName+")");

	}

	@OnClick({R.id.apply_type, R.id.save, R.id.commit})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.apply_type:
				initApplyTypeDialog();
				break;
			case R.id.save:
				postToServer(CommonData.SAVE_SEAL);
				break;
			case R.id.commit:
				postToServer(CommonData.SUBMIT_SEAL);
				break;
		}
	}

	private void postToServer(String urlPath) {
		String content = applyContent.getText().toString();

		if (TextUtils.isEmpty(content)) {
			Toast.makeText(SealApplyActivity.this, "请输入申请内容", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(processCode)) {
			Toast.makeText(SealApplyActivity.this, "请选择申请类型", Toast.LENGTH_SHORT).show();
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
		OkHttpUtils.post()
				.url(urlPath)
				.files("file", map)
				.addParams("username", username)
				.addParams("password", password)
				.addParams("code", code)
				.addParams("processCode", processCode)
				.addParams("processName", processName)
				.addParams("processType", processType)
				.addParams("content",content)
				.build()
				.execute(new MyStringCallBack());
	}

	private class MyCallBack extends RespnseCallBack {

		@Override
		public void onError(Call call, Exception e, int id) {
			Toast.makeText(SealApplyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onResponse(ResponseBean response, int id) {
			if (response.getCode()==0){
				List<Processes> process = response.getObj().getProcesses();
				if (process==null){
					Toast.makeText(SealApplyActivity.this, "请联系管理员设置", Toast.LENGTH_SHORT).show();
				}
				processes.addAll(process);
				code=response.getObj().getCode();
				pName=response.getObj().getpName();
				cName=response.getObj().getcName();

			}else{
				String msg =response.getMsg();
				Toast.makeText(SealApplyActivity.this, msg, Toast.LENGTH_SHORT).show();
			}
		}
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
														 MultiImageSelector.create(SealApplyActivity.this)
																 .showCamera(true)
																 .count(10)
																 .multi()//设置为多选模式，.single为单选模式
																 .origin(mPhotoList).start(SealApplyActivity.this, REQUEST_IMAGE);
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
	private void initApplyTypeDialog() {
		dialog = new Dialog(SealApplyActivity.this);
		View view = LayoutInflater.from(SealApplyActivity.this).inflate(R.layout.spinner_dialog, null);
		listView = (ListView) view.findViewById(R.id.spinner_list);
		SpinnerAdapter spinnerAdapter = new SpinnerAdapter(SealApplyActivity.this, processes);

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

	private class MyStringCallBack extends RespnseCallBack {

		@Override
		public void onError(Call call, Exception e, int id) {

		}

		@Override
		public void onResponse(ResponseBean response, int id) {
			if (response.getCode() == 0) {
				Toast.makeText(SealApplyActivity.this, "单据处理成功", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(SealApplyActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
			}
		}

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
}
