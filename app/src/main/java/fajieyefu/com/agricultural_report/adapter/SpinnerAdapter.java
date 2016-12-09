package fajieyefu.com.agricultural_report.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import fajieyefu.com.agricultural_report.R;
import fajieyefu.com.agricultural_report.bean.Processes;

/**
 * Created by qiancheng on 2016/11/30.
 */
public class SpinnerAdapter extends BaseAdapter {
	private List<Processes> mData;
	private Context mContext;

	public SpinnerAdapter(Context context, List<Processes> data) {
		mContext = context;
		mData = data;
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
		Processes processes = mData.get(position);
		convertView= LayoutInflater.from(mContext).inflate(R.layout.spinner_list_item,null);
		TextView textView = (TextView) convertView.findViewById(R.id.item_text);
		LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.item_layout);
		if (!TextUtils.isEmpty(processes.getName())){
			textView.setText(processes.getName());
		}else{
			textView.setText(processes.getType());
		}

		if (processes.getSelect_flag()==1){
			layout.setBackgroundColor(mContext.getResources().getColor(R.color.headgreen));
		}
		return convertView;
	}
}

