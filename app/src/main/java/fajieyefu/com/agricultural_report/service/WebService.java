package fajieyefu.com.agricultural_report.service;


import java.util.Map;

/**
 * Created by qiancheng on 2016/10/11.
 */
public class WebService {
	public static String IP ="http://" + "116.255.182.140:8082";//服务器地址
	public static String constant_path =  IP + "/mobile/";
	/**
	 * 登录
	 * @param params
	 * @return
	 */
	public static String login(Map<String,String> params){
		return  WebBean.postHttp(params,constant_path+"login");
	}



}
