package com.bishe.nongcun.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bishe.nongcun.R;

import butterknife.Bind;
import cn.bmob.v3.Bmob;


public class SplashActivity extends BaseActivity {

    @Bind(R.id.sp_jump_btn)
    Button btn_splash_jump;
    private CountDownTimer countDownTimer = new CountDownTimer(200, 100) {

        @Override
        public void onTick(long millisUntilFinished) {
            Log.e("zwc", "onTick: " + millisUntilFinished);
            btn_splash_jump.setText("跳过(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            btn_splash_jump.setText("跳过(" + 0 + "s)");
            goLoginActivity();
        }
    };

    private void goLoginActivity() {
        //直接跳转主页面
//        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_splash;
    }


    @Override
    void initData() {
        btn_splash_jump.setVisibility(View.VISIBLE);
        countDownTimer.start();

        //第一：默认初始化
        Bmob.initialize(this, "80c67b4c1ceca9635ef33bc3248debca");
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        //Bmob.initialize(this, "Your Application ID","bmob");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);


    }

    @Override
    void initListener() {
        btn_splash_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行跳转逻辑
                goLoginActivity();
            }
        });
    }

}
