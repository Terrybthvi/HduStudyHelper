package com.edu.hdu.hdustudyhelper.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.edu.hdu.hdustudyhelper.R;
import com.edu.hdu.hdustudyhelper.application.HduApplication;
import com.edu.hdu.hdustudyhelper.service.CourseService;
import com.edu.hdu.hdustudyhelper.service.LinkService;
import com.edu.hdu.hdustudyhelper.util.CommonUtil;
import com.edu.hdu.hdustudyhelper.util.HttpUtil;
import com.edu.hdu.hdustudyhelper.util.MD5Until;
import com.edu.hdu.hdustudyhelper.util.SharedPreferenceUtil;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.litepal.parser.LitePalParser;
import org.litepal.tablemanager.Connector;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import http.AsyncHttpResponseHandler;
import http.PersistentCookieStore;
import http.RequestParams;

public class BindActivity extends Activity {
    private EditText username, password, secrectCode;// 账号，密码，验证码
    private ImageView code;// 验证码
    private Button flashCode, login;//刷新验证码，登录
    public static PersistentCookieStore cookie;
    private SQLiteDatabase db;
    private String urlll;
    private LinkService linkService;
    private CourseService courseService;
    static DefaultHttpClient httpClient = new DefaultHttpClient();
    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.getCode:
                    getCode();
                    break;
                case R.id.login:
                    login();
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        initValue();//变量初始化
        initView();//视图初始化
        initEvent();// 事件初始化
        initCookie(this);// cookie初始化
        initDatabase();// 数据库初始化
        new Thread(new Runnable() {
            @Override
            public void run() {
                getlt();
            }
        }).start();

    }
    private void initValue() {
        HduApplication application= (HduApplication) getApplicationContext();
        linkService=application.getLinkService();
        courseService=application.getCourseService();
    }
    /**
     * 初始化View
     */
    private void initView() {
        secrectCode = (EditText) findViewById(R.id.secrectCode);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        flashCode = (Button) findViewById(R.id.getCode);
        login = (Button) findViewById(R.id.login);
        code = (ImageView) findViewById(R.id.codeImage);
        getCode();
    }
    /**
     * 获取lt
     */
    private void getlt(){
        Document doc = null;
            doc = Jsoup.parse(GetShouye());
            if (doc != null)
            Log.d("doc",doc.toString());
        if (doc != null) {
            Log.d("doc_body", doc.toString());
            Elements login = doc.body().getElementsByClass("login_form");
            Document containerDoc = Jsoup.parse(login.toString());
            Log.d("doc_login", login.toString());
            Elements ddd = containerDoc.getElementsByTag("input");
            Log.d("doc_ddd", ddd.toString());
            for (Element aaa : ddd) {
                Log.d("doc_aaa", aaa.toString());
                if (aaa.toString().contains("lt")) {
                    Log.d("doc_lyyy", aaa.toString());
                    String l = aaa.toString();
                    String lt = l.substring(38, l.length() - 4);
                    Log.e("doc_LT", lt);
                    HttpUtil.lt = lt;
                }
            }
        }

    }


    /**
     * 初始事件
     */
    private void initEvent() {
        // 一些列点击事件的初始化
        flashCode.setOnClickListener(listener);
        login.setOnClickListener(listener);
    }
    /**
     * 初始化数据库
     */
    private void initDatabase() {
        db = Connector.getDatabase();
        // 在assets目录下的litepal.xml李配置数据库名，版本，映射关系
    }

    /**
     * 初始化Cookie
     */
    private void initCookie(Context context) {
        //必须在请求前初始化
        cookie = new PersistentCookieStore(context);
        HttpUtil.getClient().setCookieStore(cookie);
        httpClient.setCookieStore(cookie);
    }
    private void jump2Main() {
        SharedPreferenceUtil util=new SharedPreferenceUtil(getApplicationContext(), "accountInfo");
        util.setKeyData("username", HttpUtil.username);
        util.setKeyData("password", HttpUtil.password);
        util.setKeyData("isLogin", "TRUE");
        Intent intent=new Intent(BindActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    /**
     * 登录
     */
    private void login() {
        HttpUtil.username = username.getText().toString().trim();
        String md5 = "@";
        try {
            md5= MD5Until.GetMD5Code(password.getText().toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("md5  ",md5);
        HttpUtil.password = md5;
        //需要时打开验证码注释
        HttpUtil.captcha = secrectCode.getText().toString().trim();
        Log.d("cookie",HttpUtil.Cookie);
        if (TextUtils.isEmpty(HttpUtil.username)
                || TextUtils.isEmpty(HttpUtil.password)) {
            Toast.makeText(getApplicationContext(), "账号或者密码不能为空!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog dialog =CommonUtil.getProcessDialog(BindActivity.this,"正在登录中！！！");
        dialog.show();
        RequestParams params = HttpUtil.getLoginRequestParams();// 获得请求参数
        Log.d("http",params.toString());
        HttpUtil.getClient().setURLEncodingEnabled(true);
        HttpUtil.post(HttpUtil.URL_LOGIN, params,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        try {
                            String resultContent = new String(arg2, "gb2312");
                            Log.d("Header",arg1.toString());
                            Log.d("resunt",resultContent);
//							List<String> list = Getlt.match(resultContent,"input","name=\"lt\" ");
//							Log.d("list",list.toString());
                            if(linkService.isLogin(resultContent)!=null){
                                String ret = linkService.parseMenu(resultContent);
                                Log.d("cas", "login success:"+ret);
                                GetHerfUrl(resultContent);
                                Toast.makeText(getApplicationContext(),
                                        "登录成功！！！", Toast.LENGTH_SHORT).show();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String s = GetShouye();
                                        Log.d("sdfsdaf",s);
                                        String ss = Getcourse();
                                        Log.d("qqqqqq",ss);
                                        jump2Main();
                                    }
                                }).start();




                            }else{
                                getCode();
                                Toast.makeText(getApplicationContext(),"账号或者密码错误！！！", Toast.LENGTH_SHORT).show();
                            }

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } finally {
                            dialog.dismiss();
                        }

                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        Toast.makeText(getApplicationContext(), "登录失败！！！！",
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    }

    /**
     * 请求首页
     * @return
     */
    public String GetShouye()
    {
        String result= "";

//    	创建HttpGet或HttpPost对象，将要请求的URL通过构造方法传入HttpGet或HttpPost对象。
        if (urlll == null){
            urlll = HttpUtil.URL_MAIN;
        }
        HttpGet httpRequst = new HttpGet(urlll);

//    	new DefaultHttpClient().execute(HttpUriRequst requst);
        try {
            //使用DefaultHttpClient类的execute方法发送HTTP GET请求，并返回HttpResponse对象。
            HttpResponse httpResponse = httpClient.execute(httpRequst);//其中HttpGet是HttpUriRequst的子类
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);//取出应答字符串
                // 一般来说都要删除多余的字符
                result.replaceAll("\r", "");//去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
            } else{
                httpRequst.abort();
            }
        }catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = e.getMessage().toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = e.getMessage().toString();
        }
        return result;
    }
    /**
     * 获得验证码
     */
    private void getCode() {
        final ProgressDialog dialog = CommonUtil.getProcessDialog(BindActivity.this,"正在获取验证码");
        dialog.show();
        HttpUtil.get(HttpUtil.URL_CODE, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {

                InputStream is = new ByteArrayInputStream(arg2);
                Bitmap decodeStream = BitmapFactory.decodeStream(is);
                code.setImageBitmap(decodeStream);
                Toast.makeText(getApplicationContext(), "验证码获取成功！！！",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {

                Toast.makeText(getApplicationContext(), "验证码获取失败！！！",
                        Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
    }

    /**
     * 获取重定位url
     * * */
    private void GetHerfUrl(String s){
        Document document = Jsoup.parse(s,"UTF_8");
        Elements body = document.body().getElementsByTag("a");
        Log.e("doc_",body.toString());
        urlll = body.toString().substring(9,body.toString().length()-8);
        Log.d("docurl",urlll);
    }

    /**
     * 获取课表
     * @return
     */
    public static String Getcourse(){
        String result= "";
//    	HttpGet httpRequst = new HttpGet(URI uri);
//    	HttpGet httpRequst = new HttpGet(String uri);
//    	创建HttpGet或HttpPost对象，将要请求的URL通过构造方法传入HttpGet或HttpPost对象。
        HttpGet httpRequst = new HttpGet(HttpUtil.URL_Course);

//    	new DefaultHttpClient().execute(HttpUriRequst requst);
        try {
            //使用DefaultHttpClient类的execute方法发送HTTP GET请求，并返回HttpResponse对象。

            httpClient.setCookieStore(cookie);
            HttpResponse httpResponse = httpClient.execute(httpRequst);//其中HttpGet是HttpUriRequst的子类
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);//取出应答字符串
                // 一般来说都要删除多余的字符
                result.replaceAll("\r", "");//去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
            } else{
                httpRequst.abort();
            }
        }catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = e.getMessage().toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = e.getMessage().toString();
        }
        return result;
    }

}
