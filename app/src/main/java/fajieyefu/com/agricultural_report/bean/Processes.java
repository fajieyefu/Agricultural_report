package fajieyefu.com.agricultural_report.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qiancheng on 2016/11/30.
 */
public class Processes {
	@SerializedName(value = "code", alternate = {"accountNo"})
	private String code;
	@SerializedName(value = "name", alternate = {"accountName"})
	private String name;
	private String type;
	private int select_flag;

	public int getSelect_flag() {
		return select_flag;
	}

	public void setSelect_flag(int select_flag) {
		this.select_flag = select_flag;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
