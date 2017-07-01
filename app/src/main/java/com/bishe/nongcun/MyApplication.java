package com.bishe.nongcun;

import android.app.Application;

import com.lzy.okgo.OkGo;

/**
 * @ 创建时间: 2017/6/10 on 17:36.
 * @ 描述：
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //okgo必须调用初始化
        OkGo.init(this);

    }
}
