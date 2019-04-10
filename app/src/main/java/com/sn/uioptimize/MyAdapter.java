package com.sn.uioptimize;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * date:2017/5/9
 * author:易宸锋(dell)
 */
public class MyAdapter extends BaseAdapter {
    final Context context;
    final List<String> list;
    public MyAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoulder houlder ;
        if(convertView==null){
            convertView = View.inflate(context,R.layout.list_item,null);
            houlder = new ViewHoulder();
            houlder.tv_text = (TextView) convertView.findViewById(R.id.tv_text);

            convertView.setTag(houlder);
        }else {
            houlder = (ViewHoulder) convertView.getTag();
        }

        //为了看出Traceview的效果,我故意这这个地方睡了3秒
        SystemClock.sleep(3000);
        houlder.tv_text.setText(list.get(position));
        return convertView;
    }

    static class ViewHoulder{
        TextView tv_text;
    }
}
