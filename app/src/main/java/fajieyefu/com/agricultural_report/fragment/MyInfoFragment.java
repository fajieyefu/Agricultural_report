package fajieyefu.com.agricultural_report.fragment;


import android.app.Dialog;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.agricultural_report.R;
import fajieyefu.com.agricultural_report.bean.ActivityCollector;
import fajieyefu.com.agricultural_report.bean.BaseDialog;
import fajieyefu.com.agricultural_report.main.AppFeedBackActivity;
import fajieyefu.com.agricultural_report.main.Login_Activity;
import fajieyefu.com.agricultural_report.main.MyInfoActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyInfoFragment extends Fragment implements View.OnClickListener {


	@BindView(R.id.title_name)
	TextView titleName;
	@BindView(R.id.feedback)
	Button feedback;
	@BindView(R.id.myInfo_layout)
	RelativeLayout myInfoLayout;
	@BindView(R.id.app_feeback)
	RelativeLayout appFeeback;
	@BindView(R.id.check_version)
	RelativeLayout checkVersion;
	@BindView(R.id.exit)
	RelativeLayout exit;
	private Dialog dialog;
	public MyInfoFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.myinfo_fragment, container, false);
		ButterKnife.bind(this, view);
		initView();

		return view;
	}

	private void initView() {
		titleName.setText("个人信息");
		feedback.setVisibility(View.INVISIBLE);
		myInfoLayout.setOnClickListener(this);
		appFeeback.setOnClickListener(this);
		exit.setOnClickListener(this);
		checkVersion.setOnClickListener(this);


	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()){
			case R.id.myInfo_layout:
				intent = new Intent(getActivity(), MyInfoActivity.class);
				startActivity(intent);
				break;
			case R.id.app_feeback:
				intent= new Intent(getActivity(), AppFeedBackActivity.class);
				startActivity(intent);
				break;
			case R.id.exit:
				initExitDialog();
				break;
			case R.id.check_version:
				break;
			
		}
	}

	private void initExitDialog() {
		dialog = new BaseDialog.Builder(getActivity())
				.setTitle("提示").setMessage("您确定要退出吗？").setPositiveButton("取消", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				}).setNegativeButton("确定", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						ActivityCollector.finishAll();
						Intent intent=new Intent(getActivity(),Login_Activity.class);
						startActivity(intent);
						getActivity().finish();

					}
				}).create();
		dialog.show();
	}
}
