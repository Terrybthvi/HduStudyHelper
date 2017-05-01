package com.edu.hdu.hdustudyhelper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.edu.hdu.hdustudyhelper.R;
import com.edu.hdu.hdustudyhelper.adapter.MyGriadviewAdapter;
import com.edu.hdu.hdustudyhelper.service.CourseService;
import com.edu.hdu.hdustudyhelper.util.HttpUtil;
import com.edu.hdu.hdustudyhelper.view.MyGridView;

import net.micode.notes.ui.NotesListActivity;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;

import http.AsyncHttpResponseHandler;
import http.ResponseHandlerInterface;

public class MainActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener, AdapterView.OnItemClickListener {
    private GridView gridView;
    private SliderLayout mDemoSlider1;
    private CourseService courseService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDemoSlider1 = (SliderLayout) findViewById(R.id.slider1);

        courseService = new CourseService();


        initgridView();
        getimage();
        // 点击暂停开始

        // 设置默认过渡效果
        mDemoSlider1.setPresetTransformer(SliderLayout.Transformer.Accordion);
        // 设置默认指示器位置(默认指示器白色,位置在sliderlayout底部)
        mDemoSlider1.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        // 设置自定义指示器(位置自定义)
        mDemoSlider1.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
        // 设置TextView自定义动画
        mDemoSlider1.setCustomAnimation(new DescriptionAnimation());
        //mDemoSlider2.setCustomAnimation(new ChildAnimationExample()); // 多种效果，进入类修改，参考效果github/daimajia/AndroidViewAnimations
        // 设置持续时间
        mDemoSlider1.setDuration(6000);
        mDemoSlider1.addOnPageChangeListener(this);

    }

    /**
     * 获取轮播图控件图片
     */
    private void getimage() {
//        HashMap<String, String> url_maps = new HashMap<String, String>();
//        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
//        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
//        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        HashMap<String, Integer> url_maps = new HashMap<String, Integer>();

       url_maps.put("后悔过去不如，奋斗将来",R.drawable.view0);
        url_maps.put("不要懒惰",R.drawable.view1);
        url_maps.put("学习",R.drawable.view2);
        url_maps.put("今天礼拜一",R.drawable.view3);
        url_maps.put("学习改变命运",R.drawable.view4);


        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider1.addSlider(textSliderView);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    protected void onStop() {
        // 停止自动滚动
        mDemoSlider1.stopAutoCycle();
        super.onStop();
    }

    private void initgridView() {
        gridView = (MyGridView) findViewById(R.id.gridview);
        gridView.setAdapter(new MyGriadviewAdapter(this));
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                Intent i = new Intent(MainActivity.this,WebViewActivity.class);
                startActivity(i);
                break;
            case 1:
                Intent i2 = new Intent(MainActivity.this,CourseTimeActivity.class);
                startActivity(i2);
                break;
            case 2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String s = BindActivity.Getcourse();
                        Log.e("coursw",Jsoup.parse(s).body().toString());
                        courseService.parseCourse(Jsoup.parse(s).body().toString());

                    }
                }).start();
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                Intent i8 = new Intent(MainActivity.this,NotesListActivity.class);
                startActivity(i8);
                break;
        }
    }




}
