package com.edu.hdu.hdustudyhelper.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.*;
import com.edu.hdu.hdustudyhelper.R;
import com.edu.hdu.hdustudyhelper.util.HttpUtil;
import com.edu.hdu.hdustudyhelper.util.X5WebView;

public class WebViewActivity extends AppCompatActivity {

    private WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webview = (WebView) findViewById(R.id.webview);

        webview.loadUrl(HttpUtil.Url_XYXW);
//        webview.loadUrl("https://api.hdu.edu.cn/oauth/authorize?response_type=code&client_id=hduhelper&redirect_uri=http%3A%2F%2Fhduhelp.hdu.edu.cn%2Fapi%2Findex.php%2FbindStuno2%2Fbind%2FopQS5jj8clZQ08l4Xnv7zg6p1688%2Fweiqin%2Fbind_2&scope=read%2Cwrite");
    }
}
