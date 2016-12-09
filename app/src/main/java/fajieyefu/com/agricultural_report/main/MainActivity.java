package fajieyefu.com.agricultural_report.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.BinderThread;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.agricultural_report.R;
import fajieyefu.com.agricultural_report.bean.ActivityCollector;
import fajieyefu.com.agricultural_report.bean.BaseActivity;
import fajieyefu.com.agricultural_report.bean.CommonData;
import fajieyefu.com.agricultural_report.fragment.BillMessageFragment;
import fajieyefu.com.agricultural_report.fragment.MessageDocumentsFragment;
import fajieyefu.com.agricultural_report.fragment.MessageHistoryFragment;
import fajieyefu.com.agricultural_report.fragment.MyInfoFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {
	private BillMessageFragment billMessageFragment;
	private MessageHistoryFragment messageHistoryFragment;
	private MessageDocumentsFragment messageDocumentsFragment;
	private MyInfoFragment myInfoFragment;
	@BindView(R.id.message)
	RadioButton message;
	@BindView(R.id.message_history)
	RadioButton messageHistory;
	@BindView(R.id.documents)
	RadioButton messageDocument;
	@BindView(R.id.myInfo)
	RadioButton myInfo;
	private Map<Integer, Fragment> map = new HashMap<>();
	private boolean doubleBackToExitPressedOnce = false;//用于判断双击退出程序

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		initListener();
		if (savedInstanceState == null) {
			setDefaultFragment();
		}
		initNeedData();


	}

	private void initNeedData() {
		File file = new File(CommonData.PIC_TEMP);
		if (!file.exists()){
				file.mkdirs();
		}
		for (int i=1;i<11;i++){
			file = new File(CommonData.PIC_TEMP+i+".jpg");
			if (!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void initListener() {
		message.setOnClickListener(this);
		messageHistory.setOnClickListener(this);
		messageDocument.setOnClickListener(this);
		myInfo.setOnClickListener(this);
	}

	private void setDefaultFragment() {

		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		billMessageFragment = new BillMessageFragment();
		map.put(0, billMessageFragment);
		transaction.replace(R.id.id_content, billMessageFragment);
		transaction.commit();

	}

	@Override
	public void onClick(View v) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		switch (v.getId()) {
			case R.id.message:
				if (billMessageFragment == null) {
					billMessageFragment = new BillMessageFragment();
					map.put(0, billMessageFragment);
				}
				transaction.replace(R.id.id_content, billMessageFragment);
				Toast.makeText(MainActivity.this, "message", Toast.LENGTH_SHORT).show();
				break;
			case R.id.message_history:
				if (messageHistoryFragment==null){
					messageHistoryFragment = new MessageHistoryFragment();
					map.put(1,messageHistoryFragment);
				}
				transaction.replace(R.id.id_content, messageHistoryFragment);
				Toast.makeText(MainActivity.this, "message_history", Toast.LENGTH_SHORT).show();
				break;
			case R.id.documents:
				if (messageDocumentsFragment==null){
					messageDocumentsFragment = new MessageDocumentsFragment();
					map.put(2,messageDocumentsFragment);
				}
				transaction.replace(R.id.id_content, messageDocumentsFragment);
				Toast.makeText(MainActivity.this, "message_history", Toast.LENGTH_SHORT).show();
				break;
			case R.id.myInfo:
				if (myInfoFragment==null){
					myInfoFragment = new MyInfoFragment();
					map.put(3,myInfoFragment);
				}
				transaction.replace(R.id.id_content, myInfoFragment);
				Toast.makeText(MainActivity.this, "message_history", Toast.LENGTH_SHORT).show();
				break;
		}
		transaction.commit();
	}

	@Override
	public void onBackPressed() {
		if (doubleBackToExitPressedOnce) {
			ActivityCollector.finishAll();
			System.exit(0);
			return;
		}
		this.doubleBackToExitPressedOnce = true;
		Toast.makeText(this, "再点击一次退出程序", Toast.LENGTH_SHORT).show();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				doubleBackToExitPressedOnce = false;
			}
		}, 2000);

	}
}
