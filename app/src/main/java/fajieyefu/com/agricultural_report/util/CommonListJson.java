package fajieyefu.com.agricultural_report.util;


import java.io.Serializable;
import java.util.List;

/**
 * 常用的List数据的json  头部解析
 * 
 * @author 袁锦明
 * @param <T>
 * "status":"1","message":"","data":
 * 
 */
@SuppressWarnings("serial")
public class CommonListJson<T> implements Serializable {

	private String flag;
	private String msg;
	private List<T> data;//data

	public CommonListJson(String flag, String msg, List<T> data) {
		this.flag = flag;
		this.msg = msg;
		this.data = data;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "CommonListJson{" +
				"data=" + data +
				", msg='" + msg + '\'' +
				", flag='" + flag + '\'' +
				'}';
	}
}
