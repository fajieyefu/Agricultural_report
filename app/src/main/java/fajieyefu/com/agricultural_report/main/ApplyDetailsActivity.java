package fajieyefu.com.agricultural_report.main;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import fajieyefu.com.agricultural_report.R;
import fajieyefu.com.agricultural_report.adapter.FileAdapter;
import fajieyefu.com.agricultural_report.adapter.ProcessAdapter;
import fajieyefu.com.agricultural_report.bean.ApplyDetailsInfo;
import fajieyefu.com.agricultural_report.bean.BaseActivity;
import fajieyefu.com.agricultural_report.bean.BeanUtil;
import fajieyefu.com.agricultural_report.bean.CacheForInfo;
import fajieyefu.com.agricultural_report.bean.FilesInfo;
import fajieyefu.com.agricultural_report.bean.ProcessDetails;
import fajieyefu.com.agricultural_report.bean.ResponseBean;
import fajieyefu.com.agricultural_report.layout.TitleLayout;
import fajieyefu.com.agricultural_report.util.RespnseCallBack;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by qiancheng on 2016/11/28.
 */
public class ApplyDetailsActivity extends BaseActivity {
	@BindView(R.id.apply_flag)
	TextView applyFlag;
	@BindView(R.id.work_area)
	TextView workArea;
	@BindView(R.id.work_area_layout)
	LinearLayout workAreaLayout;
	@BindView(R.id.community)
	TextView community;
	@BindView(R.id.community_layout)
	LinearLayout communityLayout;
	@BindView(R.id.service_type)
	TextView serviceType;
	@BindView(R.id.service_type_layout)
	LinearLayout serviceTypeLayout;
	@BindView(R.id.apply_type)
	TextView applyType;
	@BindView(R.id.apply_type_layout)
	LinearLayout applyTypeLayout;
	@BindView(R.id.apply_amt)
	TextView applyAmt;
	@BindView(R.id.apply_amt_layout)
	LinearLayout applyAmtLayout;
	@BindView(R.id.record_time)
	TextView recordTime;
	@BindView(R.id.record_time_layout)
	LinearLayout recordTimeLayout;
	@BindView(R.id.contract_A)
	TextView contractA;
	@BindView(R.id.contract_A_layout)
	LinearLayout contractALayout;
	@BindView(R.id.contract_B)
	TextView contractB;
	@BindView(R.id.contract_B_layout)
	LinearLayout contractBLayout;
	@BindView(R.id.start_time)
	TextView startTime;
	@BindView(R.id.start_time_layout)
	LinearLayout startTimeLayout;
	@BindView(R.id.end_time)
	TextView endTime;
	@BindView(R.id.end_time_layout)
	LinearLayout endTimeLayout;
	@BindView(R.id.contract_name)
	TextView contractName;
	@BindView(R.id.contract_name_layout)
	LinearLayout contractNameLayout;
	@BindView(R.id.apply_man)
	TextView applyMan;
	@BindView(R.id.apply_man_layout)
	LinearLayout applyManLayout;
	@BindView(R.id.process_lv)
	ListView processLv;
	@BindView(R.id.files_rv)
	RecyclerView filesRv;
	@BindView(R.id.apply_code)
	TextView applyCode;
	@BindView(R.id.apply_code_layout)
	LinearLayout applyCodeLayout;
	@BindView(R.id.scrollView)
	ScrollView scrollView;
	@BindView(R.id.title)
	TitleLayout title;
	@BindView(R.id.file_layout)
	LinearLayout fileLayout;
	private ProcessAdapter processAdapter;
	private FileAdapter fileAdapter;
	private List<ProcessDetails> list = new ArrayList<>();
	private List<FilesInfo> list_file = new ArrayList<>();
	private String TAG = "ApplyDetailsActivity";
	private SimpleDateFormat simpleDateFormat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_details);
		ButterKnife.bind(this);
		initData();

	}

	private void initData() {
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		processAdapter = new ProcessAdapter(this, list);
		processLv.setAdapter(processAdapter);
		initRecycleView();
		Intent intent = getIntent();
		String urlPath = intent.getStringExtra("url");
		int id = intent.getIntExtra("id", 0);
		String applyTypeName = intent.getStringExtra("applyTypeName");
		title.setTitleText(applyTypeName);
		final CacheForInfo cache = CacheForInfo.getInstance(this);
		String username = cache.getUsername();
		String password = cache.getPassword();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("username", username);
			jsonObject.put("password", password);
			jsonObject.put("id", id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		OkHttpUtils.postString()
				.url(urlPath)
				.content(jsonObject.toString())
				.mediaType(MediaType.parse("application/json;charset=utf-8"))
				.build()
				.execute(new MyCallBack());
	}


	private void initRecycleView() {
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		//默认为vertical，可以不设置
		layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		fileAdapter = new FileAdapter(list_file, ApplyDetailsActivity.this);
		filesRv.setLayoutManager(layoutManager);
		filesRv.setAdapter(fileAdapter);
	}


	private class MyCallBack extends RespnseCallBack {
		@Override
		public void onError(Call call, Exception e, int id) {

		}

		@Override
		public void onResponse(ResponseBean response, int id) {
			if (response.getCode() == 0) {
				ApplyDetailsInfo applyDetailsInfo = response.getObj();
				if (applyDetailsInfo.getCapitalFlag() != null) {
					String capitalFlag="";
					switch (applyDetailsInfo.getCapitalFlag()){
						case "A":
							capitalFlag="待提交";
							break;
						case "B":
							capitalFlag="审批中";
							break;
						case "C":
							capitalFlag="退回修改";
							break;
						case "N":
							capitalFlag="审批退回";
							break;
						case "Y":
							capitalFlag="审批通过";
							break;

					}
					applyFlag.setText("状态："+capitalFlag);
				}
				if (applyDetailsInfo.getpName() != null) {
					workArea.setText(applyDetailsInfo.getpName());
					workAreaLayout.setVisibility(View.VISIBLE);
				}
				if (applyDetailsInfo.getcName() != null) {
					community.setText(applyDetailsInfo.getcName());
					communityLayout.setVisibility(View.VISIBLE);
				}
				if (applyDetailsInfo.getProcessName() != null) {
					applyType.setText(applyDetailsInfo.getProcessName());
					applyTypeLayout.setVisibility(View.VISIBLE);
				}
				if (applyDetailsInfo.getCode() != null) {
					applyCode.setText(applyDetailsInfo.getCode());
					applyCodeLayout.setVisibility(View.VISIBLE);
				}
				if (applyDetailsInfo.getAccount() != 0) {
					applyAmt.setText(applyDetailsInfo.getAccount() + "");
					applyAmtLayout.setVisibility(View.VISIBLE);
				}

				if (applyDetailsInfo.getCreateByName() != null) {
					applyMan.setText(applyDetailsInfo.getCreateByName());
					applyManLayout.setVisibility(View.VISIBLE);
				}
				if (applyDetailsInfo.getContent() != null) {
					contractName.setText(applyDetailsInfo.getContent());
					contractNameLayout.setVisibility(View.VISIBLE);
				}
				if (applyDetailsInfo.getRecordTime() != 0) {
					recordTime.setText(simpleDateFormat.format(applyDetailsInfo.getRecordTime()));
					recordTimeLayout.setVisibility(View.VISIBLE);
				}
				if (applyDetailsInfo.getContractA() != null) {
					contractA.setText(applyDetailsInfo.getContractA());
					contractALayout.setVisibility(View.VISIBLE);
				}
				if (applyDetailsInfo.getContractB() != null) {
					contractB.setText(applyDetailsInfo.getContractB());
					contractBLayout.setVisibility(View.VISIBLE);
				}
				if (applyDetailsInfo.getStartTime() != 0) {
					startTime.setText(simpleDateFormat.format(applyDetailsInfo.getStartTime()));
					startTimeLayout.setVisibility(View.VISIBLE);
				}
				if (applyDetailsInfo.getEndTime() != 0) {
					endTime.setText(simpleDateFormat.format(applyDetailsInfo.getEndTime()));
					endTimeLayout.setVisibility(View.VISIBLE);
				}
				if (applyDetailsInfo.getBusinessName() != null) {
					serviceType.setText(applyDetailsInfo.getBusinessName());
					serviceTypeLayout.setVisibility(View.VISIBLE);
				}

				if (applyDetailsInfo.getDetails() != null) {
					list.clear();
					list.addAll(applyDetailsInfo.getDetails());
					processAdapter.notifyDataSetChanged();
					BeanUtil.setListViewHeightBasedOnChildren(processLv);
					scrollView.scrollTo(0, 0);
				}
				if (applyDetailsInfo.getImages() != null&&applyDetailsInfo.getImages().size()!=0) {
					list_file.clear();
					list_file.addAll(applyDetailsInfo.getImages());
					fileAdapter.notifyDataSetChanged();
				} else {
					fileLayout.setVisibility(View.GONE);
				}


			}


		}
	}
}
