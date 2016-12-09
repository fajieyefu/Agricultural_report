package fajieyefu.com.agricultural_report.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.agricultural_report.R;
import fajieyefu.com.agricultural_report.bean.ProcessDetails;

/**
 * Created by qiancheng on 2016/11/28.
 */
public class ProcessAdapter extends BaseAdapter {
	private Context mContext;
	private List<ProcessDetails> mData;
	private SimpleDateFormat simpleDateFormat;

	public ProcessAdapter(Context context, List<ProcessDetails> data) {
		mContext = context;
		mData = data;
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
		ProcessDetails processDetails = mData.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.process_item, null);
			viewHolder=new ViewHolder(convertView);
			viewHolder.checkMan.setText(processDetails.getAuditByName());
			if (processDetails.getAuditTime()!=0){
				viewHolder.checkTime.setText(simpleDateFormat.format(processDetails.getAuditTime()));
			}
			String audit_flag=null;
			switch (processDetails.getAuditFlag()){
				case "B":
					audit_flag="审核中";
					break;
				case "A":
					audit_flag="等待审核";
					break;
				case "Y":
					audit_flag="审核通过";
					break;

			}
			viewHolder.applyFlag.setText(audit_flag);
			viewHolder.applyOrder.setText(processDetails.getAuditOrder());
			viewHolder.checkDescription.setText(processDetails.getOpinion());


		}
		return convertView;
	}

	static class ViewHolder {
		@BindView(R.id.check_man)
		TextView checkMan;
		@BindView(R.id.check_time)
		TextView checkTime;
		@BindView(R.id.apply_flag)
		TextView applyFlag;
		@BindView(R.id.apply_order)
		TextView applyOrder;
		@BindView(R.id.check_description)
		TextView checkDescription;

		ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
