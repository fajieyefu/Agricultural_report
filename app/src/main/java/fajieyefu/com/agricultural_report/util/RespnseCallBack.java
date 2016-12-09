package fajieyefu.com.agricultural_report.util;

import android.util.Log;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import fajieyefu.com.agricultural_report.bean.ResponseBean;
import okhttp3.Response;

/**
 * Created by qiancheng on 2016/12/2.
 */
public abstract class RespnseCallBack extends Callback<ResponseBean> {
	@Override
	public ResponseBean parseNetworkResponse(Response response, int id) throws Exception {
		String string = response.body().string();
		Log.i("返回数据",string);
		ResponseBean responseBean = new Gson().fromJson(string, ResponseBean.class);
		return responseBean;
	}


}
