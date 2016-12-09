package fajieyefu.com.agricultural_report.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qiancheng on 2016/11/24.
 */
public class ApplyInfo {
	private String pname;
	private String content;
	private int id;
	private String applyTypeName;
	private String capitalFlag;
	private String code;
	private long recordTime;
	private String cname;

	public String getApplyTypeName() {
		return applyTypeName;
	}

	public void setApplyTypeName(String applyTypeName) {
		this.applyTypeName = applyTypeName;
	}

	public String getCapitalFlag() {
		return capitalFlag;
	}

	public void setCapitalFlag(String capitalFlag) {
		this.capitalFlag = capitalFlag;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}
}
