package fajieyefu.com.agricultural_report.bean;

/**
 * Created by qiancheng on 2016/11/26.
 */
public class ProcessDetails {
	private int id;
	private String auditby;
	private String auditFlag;
	private String auditbyName;
	private long auditTime=0;
	private String opinion;
	private String auditOrder;
	private String code;

	public String getAuditby() {
		return auditby;
	}

	public void setAuditby(String auditby) {
		this.auditby = auditby;
	}

	public String getAuditByName() {
		return auditbyName;
	}

	public void setAuditByName(String auditByName) {
		this.auditbyName = auditByName;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public String getAuditOrder() {
		return auditOrder;
	}

	public void setAuditOrder(String auditOrder) {
		this.auditOrder = auditOrder;
	}

	public long getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(long auditTime) {
		this.auditTime = auditTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
}