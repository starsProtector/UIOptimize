package com.sn.uioptimize;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 进行UI的基本优化,去除无用的背景颜色
 */
public class MainActivity extends AppCompatActivity {

    List<String> mList = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            mList.add("易总好酷" + i);
        }
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(MainActivity.this, mList);
        listView.setAdapter(adapter);
    }
}
