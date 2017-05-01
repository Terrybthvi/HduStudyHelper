package com.edu.hdu.hdustudyhelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.hdu.hdustudyhelper.R;

/**
 * Created by leiqi on 2017/3/13.
 */

public class MyGriadviewAdapter extends BaseAdapter {
    private Context mContext;

    public String[] img_text = { "校园新闻","上课时间","我的课表","考试安排","成绩查询", "学习计划", "资料管理", "考试倒计时", "事情备忘录"};
    public int[] imgs = { R.drawable.news, R.drawable.time,
            R.drawable.kebiao, R.drawable.ic_launcher,
            R.drawable.search,
            R.drawable.xxjd, R.drawable.ic_launcher,
            R.drawable.time2, R.drawable.bwl2 };

    public MyGriadviewAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return img_text.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.grid_item, parent, false);
        }
        TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
        iv.setBackgroundResource(imgs[position]);

        tv.setText(img_text[position]);
        return convertView;
    }


}
