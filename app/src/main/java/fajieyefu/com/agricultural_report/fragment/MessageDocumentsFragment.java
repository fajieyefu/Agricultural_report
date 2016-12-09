package fajieyefu.com.agricultural_report.fragment;


import android.app.Fragment;
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
import butterknife.OnClick;
import fajieyefu.com.agricultural_report.R;
import fajieyefu.com.agricultural_report.audit.ContractApplyActivity;
import fajieyefu.com.agricultural_report.audit.FundApplyActivity;
import fajieyefu.com.agricultural_report.audit.IncomeApplyActivity;
import fajieyefu.com.agricultural_report.audit.RequestApplyActivity;
import fajieyefu.com.agricultural_report.audit.SealApplyActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageDocumentsFragment extends Fragment implements View.OnClickListener {


	@BindView(R.id.feedback)
	Button feedback;
	@BindView(R.id.fund_application)
	TextView fundApplication;
	@BindView(R.id.request)
	TextView request;
	@BindView(R.id.pre_contract)
	TextView preContract;
	@BindView(R.id.income_declaration)
	TextView incomeDeclaration;
	@BindView(R.id.official_seal_application)
	TextView officialSealApplication;
	@BindView(R.id.title_name)
	TextView titleName;
	@BindView(R.id.title)
	RelativeLayout title;

	public MessageDocumentsFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.documents_fragment, container, false);
		ButterKnife.bind(this, view);
		initView();
		return view;
	}

	private void initView() {
		feedback.setVisibility(View.INVISIBLE);
		titleName.setText("单据申报");
	}


	@OnClick({R.id.fund_application, R.id.request, R.id.pre_contract, R.id.income_declaration, R.id.official_seal_application})
	public void onClick(View view) {
		Intent intent;
		switch (view.getId()) {
			case R.id.fund_application:
				 intent =  new Intent(getActivity(),FundApplyActivity.class);
				startActivity(intent);
				break;
			case R.id.request:
				intent =  new Intent(getActivity(), RequestApplyActivity.class);
				startActivity(intent);
				break;
			case R.id.pre_contract:
				intent= new Intent(getActivity(), ContractApplyActivity.class);
				startActivity(intent);
				break;
			case R.id.income_declaration:
				intent = new Intent(getActivity(), IncomeApplyActivity.class);
				startActivity(intent);
				break;
			case R.id.official_seal_application:
				intent = new Intent(getActivity(), SealApplyActivity.class);
				startActivity(intent);
				break;
		}
	}
}
