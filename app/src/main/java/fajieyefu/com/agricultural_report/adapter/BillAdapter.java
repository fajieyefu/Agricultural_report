package fajieyefu.com.agricultural_report.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import fajieyefu.com.agricultural_report.R;
import fajieyefu.com.agricultural_report.bean.ApplyInfo;
import fajieyefu.com.agricultural_report.bean.BaseActivity;
import fajieyefu.com.agricultural_report.bean.BillInfo;

/**
 * Created by qiancheng on 2016/11/23.
 */
public class BillAdapter extends BaseAdapter {
	private Context mContext;
	private List<ApplyInfo> mData;
	private SimpleDateFormat dateFormat;

	public BillAdapter(Context context, List<ApplyInfo> data) {
		mContext = context;
		mData = data;
		dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);

	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ApplyInfo info = mData.get(position);
		BillViewHolder billViewHolder =null;
		if (convertView==null){
			billViewHolder= new BillViewHolder();
			convertView= LayoutInflater.from(mContext).inflate(R.layout.bill_item_layout,null);
			billViewHolder.billCode= (TextView) convertView.findViewById(R.id.bill_code);
			billViewHolder.billFlag= (TextView) convertView.findViewById(R.id.bill_flag);
			billViewHolder.billTitle= (TextView) convertView.findViewById(R.id.bill_title);
			billViewHolder.applyDate= (TextView) convertView.findViewById(R.id.apply_date);
			billViewHolder.applyArea= (TextView) convertView.findViewById(R.id.apply_area);
			convertView.setTag(billViewHolder);

		}else{
			billViewHolder= (BillViewHolder) convertView.getTag();
		}
		String capitalFlag="";
		switch (info.getCapitalFlag()){
			case "A":
				capitalFlag="未提交";
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
		billViewHolder.billCode.setText(info.getCode());
		billViewHolder.billFlag.setText(capitalFlag);
		billViewHolder.billTitle.setText(info.getApplyTypeName());
		billViewHolder.applyDate.setText(dateFormat.format(info.getRecordTime()));
		billViewHolder.applyArea.setText(info.getPname()+info.getCname());
		return convertView;
	}

	class BillViewHolder {
		private TextView billCode;
		private TextView billFlag;
		private TextView billTitle;
		private TextView applyDate;
		private TextView applyArea;
	}
}
