package fajieyefu.com.agricultural_report.layout;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import fajieyefu.com.agricultural_report.R;


/**
 * Created by qiancheng on 2016/10/4.
 */
public class TitleLayout extends LinearLayout {
	private Button back;
	private TextView textView;
	public TitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.top_title,this);
		back= (Button) findViewById(R.id.back);
		textView = (TextView) findViewById(R.id.title_text);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				((Activity)getContext()).finish();
			}
		});

	}
	public void setTitleText(String text) {
		textView.setText(text);
	}
}
