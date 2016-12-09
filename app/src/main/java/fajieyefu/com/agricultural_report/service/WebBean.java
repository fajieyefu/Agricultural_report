package fajieyefu.com.agricultural_report.service;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by qiancheng on 2016/10/11.
 */
public class WebBean {
	//获得Get方式请求数据
	public static String getHttp(String path) {
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			conn = (HttpURLConnection) new URL(path).openConnection();
			conn.setConnectTimeout(6000);//设置连接超时时间
			conn.setReadTimeout(3000);
			conn.setDoInput(true);
			conn.setRequestMethod("GET");//设置获取信息方式
			conn.setRequestProperty("Charset", "UTF-8");//设置接收数据编码格式

			if (conn.getResponseCode() == 200) {
				in = conn.getInputStream();
				return parseInfo(in);
			}
		} catch (Exception e) {
			return "-1";
		} finally {
			//意外退出时关闭连接
			if (conn != null) {
				conn.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "-1";

	}

	//获得post方式请求数据
	public static String postHttp(Map<String, String> params, String path) {
		byte[] data=new byte[0];
		if (params!=null){
			 data= getRequestData2(params, "UTF-8").getBytes();//获得请求体
		}
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(path).openConnection();
			conn.setConnectTimeout(6000);//设置连接超时时间
			conn.setReadTimeout(6000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");//设置获取信息方式
			conn.setUseCaches(false);//使用post方式不能使用缓存
			//设置请求体的类型是文本类型
			conn.setRequestProperty("Content-Type", "application/json");
			//设置请求体的长度
			conn.setRequestProperty("Content-Length", String.valueOf(data.length));
			conn.setRequestProperty("Charset", "UTF-8");//设置接收数据编码格式
			//获得输出流，向服务器写入数据
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(data);
			int response = conn.getResponseCode();            //获得服务器的响应码
			if (response == HttpURLConnection.HTTP_OK) {        //HTTP_OK=200
				InputStream inputStream = conn.getInputStream();
				return parseInfo(inputStream);                  //处理服务器的响应结果
			}
			else{
				return  "-1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

	}
	//以json形式封装post
	public static String getRequestData2(Map<String, String> params, String encode){
		JSONObject  jsonObject =new JSONObject();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			try {
				jsonObject.put(entry.getKey(),URLEncoder.encode(entry.getValue(), encode));
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}
		return  jsonObject.toString();

	}

	//封装post请求
	public static StringBuffer getRequestData(Map<String, String> params, String encode) {
		StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
		try {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				stringBuffer.append(entry.getKey())
						.append("=")
						.append(URLEncoder.encode(entry.getValue(), encode))
						.append("&");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuffer;
	}


	//将输入流转化为String类型
	private static String parseInfo(InputStream in) throws IOException {
		byte[] data = read(in);

		return new String(data, "UTF-8");

	}

	//将输入流转化为byte数组
	private static byte[] read(InputStream in) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];

		int len = 0;

		while ((len = in.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		in.close();
		return outputStream.toByteArray();

	}
}
