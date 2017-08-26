package com.bishe.nongcun.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.bishe.nongcun.R;

import butterknife.ButterKnife;

/**
 * @ 创建时间: 2017/6/13 on 15:40.
 * @ 描述：Activity基类
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {
    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //得到布局文件
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        //初始化View
        initView();

        //初始化界面数据
        initData();

        registComBtn();

        //绑定监听器与适配器
        initListener();
    }

    /**
     * 注册共有控件
     */
    final void registComBtn() {
        View btn_back = findViewById(R.id.btn_back);
        if (btn_back != null) {
            btn_back.setOnClickListener(this);
        }
    }

    /**
     * @return 布局文件id
     */
    abstract int getLayoutId();

    /**
     * 初始化View
     */
    void initView() {
    }

    /**
     * 初始化界面数据
     */
    void initData() {
    }

    /**
     * 绑定监听器与适配器
     */
    void initListener() {
    }

    /**
     * 对统一的按钮进行统一处理
     *
     * @param v 点击的View
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            default:
                processClick(v);
                break;
        }
    }

    /**
     * 点击事件
     *
     * @param v 点击的View
     */
    void processClick(View v) {
    }
}
