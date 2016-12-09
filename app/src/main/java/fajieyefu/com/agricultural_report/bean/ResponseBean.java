package fajieyefu.com.agricultural_report.bean;

import java.util.List;

/**
 * Created by qiancheng on 2016/11/24.
 */
public class ResponseBean {
	private int code ;
	private String  msg;
	private T data;
	private ApplyDetailsInfo obj;

	public ApplyDetailsInfo getObj() {
		return obj;
	}

	public void setObj(ApplyDetailsInfo obj) {
		this.obj = obj;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	public static class T{
		public List<ApplyInfo> apply;
		public List<Processes> applyType;
	}
}
