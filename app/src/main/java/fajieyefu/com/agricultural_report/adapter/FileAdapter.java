package fajieyefu.com.agricultural_report.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.squareup.picasso.Picasso;

import java.util.List;

import fajieyefu.com.agricultural_report.R;
import fajieyefu.com.agricultural_report.bean.CacheForInfo;
import fajieyefu.com.agricultural_report.bean.CommonData;
import fajieyefu.com.agricultural_report.bean.FilesInfo;

/**
 * Created by qiancheng on 2016/10/14.
 */
public class FileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private List<FilesInfo> mData;
	private Context mContext;
	private String username, psw_md5;
	private OnItemClickListener clickListener;

	public void setClickListener(OnItemClickListener clickListener) {
		this.clickListener = clickListener;
	}

	public static interface OnItemClickListener {
		void onClick(View view, int position);
	}

	public FileAdapter(List<FilesInfo> list, Context context) {
		this.mData = list;
		mContext = context;
		CacheForInfo cache = CacheForInfo.getInstance(context);
		username = cache.getUsername();
		psw_md5 = cache.getPassword();
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		//创建一个view
		View view = View.inflate(viewGroup.getContext(), R.layout.file_item, null);
		//创建一个ViewHolder
		FileViewHolder fileViewHolder = new FileViewHolder(view);


		return fileViewHolder;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		FilesInfo fileInfo = mData.get(position);
		Picasso.with(mContext).load(CommonData.IP+ "/contract/downloadSign?sign=" + fileInfo.getFile_path() + "&username=" + username + "&password=" + psw_md5).into(((FileViewHolder) holder).file_img);
	}

//	@Override
//	public void onBindViewHolder(FileViewHolder fileViewHolder, int position) {
//
//	}


	@Override
	public int getItemCount() {
		return mData.size();
	}

	public class FileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		private ImageView file_img;
		private ImageView amplification;

		public FileViewHolder(View itemView) {
			super(itemView);
			file_img = (ImageView) itemView.findViewById(R.id.file_img);
			amplification = (ImageView) itemView.findViewById(R.id.amplification);
			RelativeLayout rootView = (RelativeLayout) itemView.findViewById(R.id.root_view);
			rootView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (clickListener != null) {
				clickListener.onClick(itemView, getPosition());
			}
		}
	}


}
