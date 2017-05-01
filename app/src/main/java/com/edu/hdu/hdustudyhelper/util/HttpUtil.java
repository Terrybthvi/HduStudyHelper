package com.edu.hdu.hdustudyhelper.util;


import http.AsyncHttpClient;
import http.AsyncHttpResponseHandler;
import http.BinaryHttpResponseHandler;
import http.JsonHttpResponseHandler;
import http.RequestParams;


/**
 * Http请求工具类
 * @author lizhangqu
 * @date 2015-2-1
 */

/**
 * @author Administrator
 * 
 */
public class HttpUtil {
	private static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象
	// Host地址
	public static final String HOST = "cas.hdu.edu.cn";
	// 基础地址
	public static final String URL_BASE = "http://i.hdu.edu.cn/dcp/xphone/";
	// 验证码地址
	public static final String URL_CODE = "http://cas.hdu.edu.cn/cas/Captcha.jpg";
	// 登陆地址
	public static final String URL_LOGIN = "http://cas.hdu.edu.cn/cas/login?service=http%3a%2f%2fi.hdu.edu.cn%2fdcp%2fxphone%2fm.jsp";
	// 登录成功的首页
	public static String URL_MAIN = "http://i.hdu.edu.cn/dcp/xphone/m.jsp";
	//当前学期课表
	public static String URL_Course= "http://i.hdu.edu.cn/dcp/xphone/kbcx0.jsp";
	//考试安排url
	public static String URl_KSAP = "http://i.hdu.edu.cn/dcp/xphone/ksap.jsp";
	//校园新闻
	public static String Url_XYXW = "http://m.hdu.edu.cn/?cat=3";
	//上课时间
	public static String Url_Time = URL_BASE+"TimeTable.jsp";
	//我的学费
	public static String Url_WDXF = "http://yxt.hdu.edu.cn/EducationManager/xphone/m.jsp";

	/**
	 * 请求参数
	 */
	public static String captcha = "";//验证码
	public static String encodedService = "http%3a%2f%2fi.hdu.edu.cn%2fdcp%2fxphone%2fm.jsp";
	public static String loginErrCnt = "0";
	public static String lt = "LT-1552500-7P9jMewfE3wH2dWObBuJ";
	public static String password = "19191cf09b99b03ab0df1db04c3840ed";
	public static String username = null;
	public static String service = "http://i.hdu.edu.cn/dcp/xphone/m.jsp";
	public static String serviceName = null;
	public static String ticket = null;
	public static String Cookie = "key_dcp_cas=HHxRYyDMhr0GmDfQ6vgPxXQT8yCsvPSCg0MWBnnnWMGv4dQngpLS!748587538; route=4376efc7edf61c9fe699e82a2fb7a34f" ;
	// 静态初始化
	static {
		client.setTimeout(10000); // 设置链接超时，如果不设置，默认为10s
		// 设置请求头
		client.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		client.addHeader("Accept-Encoding","gzip, deflate");
		client.addHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		client.addHeader("Connection","keep-alive");
		client.addHeader("Cookie", Cookie);
		client.addHeader("Host", HOST);
		client.addHeader("Referer", URL_LOGIN);
		client.addHeader("User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:52.0) Gecko/20100101 Firefox/52.0");
		client.addHeader("Upgrade-Insecure-Requests","1");

	}



	/**
	 * get,用一个完整url获取一个string对象
	 * 
	 * @param urlString
	 * @param res
	 */
	public static void get(String urlString, AsyncHttpResponseHandler res) {
		client.get(urlString, res);
	}

	/**
	 * get,url里面带参数
	 *  @param urlString
	 * @param params
	 * @param res
	 */
	public static void get(String urlString, RequestParams params,
						   AsyncHttpResponseHandler res) {
		client.get(urlString, params, res);
	}

	/**
	 * get,不带参数，获取json对象或者数组
	 * 
	 * @param urlString
	 * @param res
	 */
	public static void get(String urlString, JsonHttpResponseHandler res) {
		client.get(urlString, res);
	}

	/**
	 * get,带参数，获取json对象或者数组
	 * 
	 * @param urlString
	 * @param params
	 * @param res
	 */
	public static void get(String urlString, RequestParams params,
                           JsonHttpResponseHandler res) {
		client.get(urlString, params, res);
	}

	/**
	 * get,下载数据使用，会返回byte数据
	 * 
	 * @param uString
	 * @param bHandler
	 */
	public static void get(String uString, BinaryHttpResponseHandler bHandler) {
		client.get(uString, bHandler);
	}

	/**
	 * post,不带参数
	 * 
	 * @param urlString
	 * @param res
	 */
	public static void post(String urlString, AsyncHttpResponseHandler res) {
		client.post(urlString, res);
	}

	/**
	 * post,带参数
	 * 
	 * @param urlString
	 * @param params
	 * @param res
	 */
	public static void post(String urlString, RequestParams params,
                            AsyncHttpResponseHandler res) {
		client.post(urlString, params, res);
	}

	/**
	 * post,不带参数，获取json对象或者数组
	 * 
	 * @param urlString
	 * @param res
	 */
	public static void post(String urlString, JsonHttpResponseHandler res) {
		client.post(urlString, res);
	}

	/**
	 * post,带参数，获取json对象或者数组
	 * 
	 * @param urlString
	 * @param params
	 * @param res
	 */
	public static void post(String urlString, RequestParams params,
                            JsonHttpResponseHandler res) {
		client.post(urlString, params, res);
	}

	/**
	 * post,返回二进制数据时使用，会返回byte数据
	 * 
	 * @param uString
	 * @param bHandler
	 */
	public static void post(String uString, BinaryHttpResponseHandler bHandler) {
		client.post(uString, bHandler);
	}

	/**
	 * 返回请求客户端
	 * 
	 * @return
	 */
	public static AsyncHttpClient getClient() {
		return client;
	}

	/**
	 * 获得登录时所需的请求参数
	 * 
	 * @return
	 */
	public static RequestParams getLoginRequestParams() {
		// 设置请求参数
		RequestParams params = new RequestParams();
		params.add("captcha", captcha);
		params.add("encodedService", encodedService);
		params.add("loginErrCnt", loginErrCnt);
		params.add("lt", lt);
		params.add("password", password);
		params.add("username", username);
		params.add("service", service);
		params.add("serviceName", serviceName);
		return params;
	}

	/**
	 * 获取请求首页参数
	 * @return
	 */
	public static RequestParams gethomeRequestParams(){
		RequestParams params = new RequestParams();
		params.add("ticket",ticket);
		return params;
	}


}