package fajieyefu.com.agricultural_report.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by qiancheng on 2016/11/26.
 */
public class ApplyDetailsInfo {
	private int id;
	private String code;//申请单号
	private String businessCode;//业务类型编号
	private String businessName;//业务类型名称
	private String processCode;//申请类型编号
	private String processName;//申请类型名称
	private String processType;//申请类型
	private String content;//内容
	private float account=0;//申请金额
	private String createBy;//申报人编号（登录人）
	private String createByName;//申报人名称
	private String pname;//工作区
	private String cname;//社区
	private long recordTime=0;//创建时间
	@SerializedName(value = "capitalFlag", alternate = {"sealFlag"})
	private String capitalFlag;//审核状态
	private String doFlag;//处理结果
	private String requestCode;
	private String contractCode;
	private List<ProcessDetails> details;
	private List<FilesInfo> files;
	private long startTime=0;//开始时间（合同）
	private long endTime=0;//结束时间（合同)
	private String name;//合同名称
	private String contractA;//甲方
	private String contractB;//乙方
	private String saveTime;//存储时间（时间戳）
	private String accountNo;//银行账号
	private String accountName;//银行名字
	private String doManName;//经办人
	private String saveDate;//存数时间（date类型）
	private String realAmt;//实际金额
	private String proedit;
	@SerializedName(value = "processes", alternate = {"processList"})
	private List<Processes> processes;

	public List<Processes> getBanks() {
		return banks;
	}

	public void setBanks(List<Processes> banks) {
		this.banks = banks;
	}

	private List<Processes> banks;
	@SerializedName(value = "businessTypes", alternate = {"contractTypeList"})
	private List<Processes> businessTypes;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public List<Processes> getBusinessTypes() {
		return businessTypes;
	}

	public void setBusinessTypes(List<Processes> businessTypes) {
		this.businessTypes = businessTypes;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getDoManName() {
		return doManName;
	}

	public void setDoManName(String doManName) {
		this.doManName = doManName;
	}

	public List<FilesInfo> getFiles() {
		return files;
	}

	public void setFiles(List<FilesInfo> files) {
		this.files = files;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public List<Processes> getProcesses() {
		return processes;
	}

	public void setProcesses(List<Processes> processes) {
		this.processes = processes;
	}

	public String getProedit() {
		return proedit;
	}

	public void setProedit(String proedit) {
		this.proedit = proedit;
	}

	public String getRealAmt() {
		return realAmt;
	}

	public void setRealAmt(String realAmt) {
		this.realAmt = realAmt;
	}

	public String getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(String saveDate) {
		this.saveDate = saveDate;
	}

	public String getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}

	public List<FilesInfo> getImages() {
		return files;
	}

	public void setImages(List<FilesInfo> images) {
		this.files = images;
	}

	public float getAccount() {
		return account;
	}

	public void setAccount(float account) {
		this.account = account;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getCapitalFlag() {
		return capitalFlag;
	}

	public void setCapitalFlag(String capitalFlag) {
		this.capitalFlag = capitalFlag;
	}

	public String getcName() {
		return cname;
	}

	public void setcName(String cName) {
		this.cname = cName;
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

	public String getContractA() {
		return contractA;
	}

	public void setContractA(String contractA) {
		this.contractA = contractA;
	}

	public String getContractB() {
		return contractB;
	}

	public void setContractB(String contractB) {
		this.contractB = contractB;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public List<ProcessDetails> getDetails() {
		return details;
	}

	public void setDetails(List<ProcessDetails> details) {
		this.details = details;
	}

	public String getDoFlag() {
		return doFlag;
	}

	public void setDoFlag(String doFlag) {
		this.doFlag = doFlag;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}




	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpName() {
		return pname;
	}

	public void setpName(String pName) {
		this.pname = pName;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
}
