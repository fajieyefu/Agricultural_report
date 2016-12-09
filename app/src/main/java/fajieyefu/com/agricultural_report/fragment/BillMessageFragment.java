package fajieyefu.com.agricultural_report.fragment;


import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.utils.HttpClientUtils;
import cz.msebera.android.httpclient.entity.StringEntity;
import fajieyefu.com.agricultural_report.R;
import fajieyefu.com.agricultural_report.adapter.BillAdapter;
import fajieyefu.com.agricultural_report.adapter.SpinnerAdapter;
import fajieyefu.com.agricultural_report.bean.ApplyInfo;
import fajieyefu.com.agricultural_report.bean.BeanUtil;
import fajieyefu.com.agricultural_report.bean.CacheForInfo;
import fajieyefu.com.agricultural_report.bean.CommonData;
import fajieyefu.com.agricultural_report.bean.Processes;
import fajieyefu.com.agricultural_report.bean.ResponseBean;
import fajieyefu.com.agricultural_report.main.ApplyDetailsActivity;
import fajieyefu.com.agricultural_report.main.FeedBackActivity;
import fajieyefu.com.agricultural_report.widget.XListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillMessageFragment extends Fragment implements View.OnClickListener, XListView.IXListViewListener {
	private XListView message_lv;
	private BillAdapter billAdapter;
	private List<ApplyInfo> list = new ArrayList<>();
	private String username, password;
	private CacheForInfo cacheForInfo;
	private final static String TAG = "BillMessageFragment";
	private int pagesInt = 1;
	private String urlPath;
	private Dialog dialog;
	private ListView listView;
	private List<Processes> processes= new ArrayList<>();
	private Button chooseText;
	private String flagType = "0";
	private int selectedItem = 0;
	private Button feedBack;
	private Handler mHandler;
	private Context context;


	public BillMessageFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bill_state_fragment, container, false);
		mHandler= new Handler();
		message_lv = (XListView) view.findViewById(R.id.bill_lv);
		message_lv.setPullRefreshEnable(true);
		message_lv.setPullLoadEnable(true);
		message_lv.setAutoLoadEnable(true);
		message_lv.setXListViewListener(this);
		message_lv.setRefreshTime(getTime());
		chooseText = (Button) view.findViewById(R.id.chooseType);
		feedBack = (Button) view.findViewById(R.id.feedback);
		feedBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), FeedBackActivity.class);
				startActivity(intent);
			}
		});
		billAdapter = new BillAdapter(getActivity(), list);
		message_lv.setAdapter(billAdapter);
		cacheForInfo = CacheForInfo.getInstance(getActivity().getApplication());
		username = cacheForInfo.getUsername();
		password = cacheForInfo.getPassword();
		message_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ApplyInfo applyInfo = list.get(position);
				String applyTypeName = applyInfo.getApplyTypeName();
				switch (applyTypeName) {
					case "合同预审单":
						urlPath = CommonData.CONTRACT_MESSAGE_DETAILS;
						break;
					case "请示单":
						urlPath = CommonData.REQUEST_MESSAGE_DETAILS;
						break;
					case "资金申请单":
						urlPath = CommonData.MONEY_MESSAGE_DETAILS;
						break;
					case "公章申请单":
						urlPath = CommonData.SEAL_MESSAGE_DETAILS;
						break;
					case "收入申报单":
						urlPath=CommonData.INCOME_MESSAGE_DETAILS;
				}
				Intent intent = new Intent(getActivity(), ApplyDetailsActivity.class);
				intent.putExtra("url", urlPath);
				intent.putExtra("id", applyInfo.getId());
				intent.putExtra("applyTypeName", applyTypeName);
				startActivity(intent);
			}
		});
		chooseText.setOnClickListener(this);
		return view;

	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context=getActivity();
		message_lv.autoRefresh();

	}

	private void initData(int pagesInt, String applyType) {
		final BeanUtil beanUtil = new BeanUtil();
		RequestQueue mQueue = Volley.newRequestQueue(context);
		Map<String, String> map = new HashMap<>();
		map.put("username", username);
		map.put("password", password);
		map.put("applyType", applyType);
		map.put("pageIndex", pagesInt + "");
		map.put("pageSize", "10");
		JSONObject jsonObject = new JSONObject(map);
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, CommonData.MESSAGE_PATH, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jsonObject_resp) {
						Gson gson = new Gson();
						Log.i(TAG, jsonObject_resp.toString());
						ResponseBean responseBean = gson.fromJson(jsonObject_resp.toString(), ResponseBean.class);
						if (responseBean.getCode() == 0) {
							List applyInfo = responseBean.getData().apply;
							processes = responseBean.getData().applyType;
							processes.get(selectedItem).setSelect_flag(1);
							if (applyInfo == null) {
								Toast.makeText(getActivity(), "数据为空", Toast.LENGTH_SHORT).show();
							}
							if (applyInfo != null) {
								list.addAll(applyInfo);
							}
							billAdapter.notifyDataSetChanged();
						} else {
							Toast.makeText(getActivity(), responseBean.getMsg(), Toast.LENGTH_SHORT).show();
						}
						onLoad();
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						onLoad();
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.chooseType:
				initTypeDialog();
		}
	}

	private void initTypeDialog() {

		dialog = new Dialog(getActivity());
		View view = LayoutInflater.from(context).inflate(R.layout.spinner_dialog, null);
		listView = (ListView) view.findViewById(R.id.spinner_list);
		SpinnerAdapter spinnerAdapter = new SpinnerAdapter(context, processes);
		list.clear();
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				chooseText.setText(processes.get(position).getName());
				for (Processes processes_item : processes) {
					processes_item.setSelect_flag(0);
				}
				processes.get(position).setSelect_flag(1);
				pagesInt = 1;
				flagType=position+"";
				selectedItem = position;
				initData(pagesInt, position + "");
				dialog.dismiss();
			}
		});

		listView.setAdapter(spinnerAdapter);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		dialog.show();

	}
	private String getTime() {
		return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				pagesInt=1;
				list.clear();
				initData(pagesInt,flagType);
//				onLoad();
			}
		}, 2500);
	}

	private void onLoad() {
		message_lv.stopRefresh();
		message_lv.stopLoadMore();
		message_lv.setRefreshTime(getTime());
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				pagesInt++;
				initData(pagesInt,flagType);
//				onLoad();
			}
		}, 2500);
	}

}
