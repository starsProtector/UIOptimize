package com.sn.synthesize;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

/**
 * A.将MyApplication中onCreate方法内容耗时的初始化工作移动到该类中
 * 此类的父类就是Service,Service是运行在主线程的后台进程
 * 提示:注意这个类要在清单文件下进行注册,步骤和Service注册一样
 */

public class InitService extends IntentService {

    // 问题：由于将NoHttp的初始化工作移动到了子线程，当主线程使用NoHttp发现没有初始化完成，报异常了。
    // 提示:实际这和同步异步问题原理一样,东西还没处理完,就拿着东西去用,肯定会出问题

    // 方案一：使用boolean值进行初始化工作的标记，如果完成boolean为true，可以在使用该工具的地方每隔一个时间段判断一下。
    // 方案二：当初始化工作完成后，发出一个通知，如果有观察者，则进行后续工作的处理(开发中常用这个模式,可以用RxAndroid完成)

    //A.构造方法,实际就是给开启的子线程起一个名称
    public InitService() {
        super("init");
    }

    //B.标记是否初始化完成,false没有完成
    public static boolean isInit=false;

    //A.此方法的代码运行在子线程中,可以做耗时的操作
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        //初始化时耗时操作
        Logger.setTag("NoHttp");
        Logger.setDebug(true);

        NoHttp.initialize(this, new NoHttp.Config()
                .setConnectTimeout(30 * 1000)
                .setReadTimeout(30 * 1000)
        );

        //B.初始完成,把标记改为true
        isInit=true;
    }

    /**
     * A.启动service,在MyApplication调用了此方法
     * @param myApplication
     */
    public static void start(MyApplication myApplication) {
        Intent intent = new Intent(myApplication, InitService.class);
        myApplication.startService(intent);
    }

}
