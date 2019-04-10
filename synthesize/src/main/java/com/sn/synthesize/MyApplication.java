package com.sn.synthesize;

import android.app.Application;
import android.os.Debug;
import android.os.SystemClock;

/**
 * 这里做ONhttp的初始化操作
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {

        //A.如果没有办法手动操作监控，可以使用代码监控重点代码
        //A.监控的结果会生成文件存储SDCard上,要到处,用工具进行查看
        Debug.startMethodTracing("ycfDemo");// 文件的名称

        super.onCreate();

        //A.开启IntentService
        InitService.start(this);

        //C.为了看把启动白色背景改为透明的效果,故意睡了1秒
        SystemClock.sleep(1000);

        //A.如果没有办法手动操作监控，可以使用代码监控重点代码
        Debug.stopMethodTracing();
    }

}
