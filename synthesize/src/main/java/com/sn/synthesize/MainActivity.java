package com.sn.synthesize;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sn.synthesize.bean.Newsnew;
import com.squareup.picasso.Picasso;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //B.使用Handler每个一段时间进行一次判断，如果初始化完成，则获取网络数据
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //对于IntentService里的标记进行判断,初始化完成,使用这个第三方框架,没有完成,继续每隔一个时间段判断一下。
            if (InitService.isInit) {
                //NOhttp默认请求网络是运行在子线程中
                Request<String> request = NoHttp.createStringRequest("http://169.254.53.96:8080/newsnew.json", RequestMethod.GET);
                NoHttp.newRequestQueue().add(10, request, listener);
            } else {
                this.sendEmptyMessageDelayed(100, 10);
            }

        }
    };

    //B.在界面可见是,发送handler
    @Override
    protected void onResume() {
        super.onResume();
        //B.开启handle的定时器
        handler.sendEmptyMessage(100);
    }

    //C.Activity生命周期中,界面加载成功后的回调
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //C.修改windows背景,把使用第二种方式所带来的问题,解决
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    }

    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private OnResponseListener<String> listener = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {

        }
        @Override
        public void onSucceed(int what, Response<String> response) {
            recyclerView.setAdapter(new MyAdapter(response.get()));
            recyclerView.setVisibility(View.VISIBLE);
        }
        @Override
        public void onFailed(int what, Response<String> response) {
        }
        @Override
        public void onFinish(int what) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.loading);
        recyclerView = (RecyclerView) findViewById(R.id.app_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


        private final List<Newsnew.NewslistBean.NewsBean> appList;

        public MyAdapter(String response) {

            Gson gson = new Gson();
            Newsnew newsnew = gson.fromJson(response, Newsnew.class);
            appList = newsnew.getNewslist().getNews();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.app_item, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, getResources().getDisplayMetrics())));
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.setData(position);
        }

        @Override
        public int getItemCount() {
            return appList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            ImageView icon;

            public MyViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.title);
                icon = (ImageView) itemView.findViewById(R.id.item_appinfo_iv_icon);
            }

            public void setData(int position) {
                title.setText(appList.get(position).getTitle());
                String path = appList.get(position).getImage();
                Picasso.with(MainActivity.this).load(path).into(icon);
            }
        }
    }
}
