package fajieyefu.com.agricultural_report.bean;

import android.os.Environment;

import java.io.File;

/**
 * Created by qiancheng on 2016/11/24.
 */
public class CommonData {
	public final static String IP = "http://" + "116.255.182.140:8089/mobile/";//服务器地址
	public final static String MESSAGE_PATH = IP + "getApplys";//获取处理中的单据信息
	public final static String MESSAGE_HISTORY_PATH = IP + "getHistoryApplys";
	public final static String LOGIN_PATH = IP + "login";//登录
	public final static String MONEY_MESSAGE_DETAILS = IP + "capital/details";//资金申请详情
	public final static String SEAL_MESSAGE_DETAILS = IP + "seal/details";//用章申请详情
	public final static String CONTRACT_MESSAGE_DETAILS = IP + "contract/details";//合同申请详情
	public final static String REQUEST_MESSAGE_DETAILS = IP + "request/details";//请示单详情
	public final static String INCOME_MESSAGE_DETAILS = IP + "include/details";//收入申报单


	public final static String ADD_CAPTICAL=IP+"capital/addPage";//新增资金申请单
	public final static String SAVE_CAPTICAL=IP+"capital/save";//保存资金申请单
	public final static String SUBMIT_CAPITICAL=IP+"capital/saveAndSubmit";//提交资金申请单

	public final static String ADD_REQUEST=IP+"request/addPage";//新增资金申请单
	public final static String SAVE_REQUEST=IP+"request/save";//保存资金申请单
	public final static String SUBMIT_REQUEST=IP+"request/saveAndSubmit";//提交资金申请单

	public final static String ADD_CONTRACT=IP+"contract/addPage";//新增合同预审单
	public final static String SAVE_CONTRACT=IP+"contract/save";//保存合同预审单
	public final static String SUBMIT_CONTRACT=IP+"contract/saveAndSubmit";//提交合同预审单

	public final static String ADD_INCOME=IP+"include/addPage";//新增收入预审单
	public final static String SAVE_INCOME=IP+"include/save";//保存收入审单
	public final static String SUBMIT_INCOME=IP+"include/saveAndSubmit";//提交收入预审单

	public final static String ADD_SEAL=IP+"seal/addPage";//新增公章申请单
	public final static String SAVE_SEAL=IP+"seal/save";//用章申请保存
	public final static String SUBMIT_SEAL=IP+"seal/saveAndSubmit";//用章申请保存和提交

	public final static String FEEDBACK=IP+"seal/saveAndSubmit";//用户意见反馈
	public final static String APP_FEEDBACK=IP+"seal/saveAndSubmit";//APP意见反馈

	public final static String MY_INFO=IP+"seal/saveAndSubmit";//我的资料




	public static final String PIC_TEMP = Environment.getExternalStorageDirectory().getPath() + "/Agricultural_report/pic_temp/";

}
