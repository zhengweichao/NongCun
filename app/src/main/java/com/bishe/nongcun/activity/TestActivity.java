package com.bishe.nongcun.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.nongcun.R;
import com.bishe.nongcun.bean.MyUser;
import com.bishe.nongcun.utils.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class TestActivity extends Activity {

    @Bind(R.id.aaa)
    TextView aaa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);


//TODO 连接：3.1、登录成功、注册成功或处于登录状态重新打开应用后执行连接IM服务器的操作
        MyUser user = BmobUser.getCurrentUser(MyUser.class);

        if (!TextUtils.isEmpty(user.getObjectId())) {
            BmobIM.connect(user.getObjectId(), new ConnectListener() {
                @Override
                public void done(String uid, BmobException e) {
                    if (e == null) {
                        //连接成功
                    } else {
                        //连接失败
//                        toast(e.getMessage());
                    }
                }
            });
        }
//        BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, null);

        /*//注册
        MyUser bu = new MyUser();
        bu.setUsername("004");
        bu.setPassword("123");
        bu.setMobilePhoneNumber("13932023254");
        bu.setAddress("河北邯郸");
//        bu.setEmail("sendi@163.com");
        //注意：不能用save方法进行注册
        bu.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser s, BmobException e) {
                if (e == null) {
                    LogUtils.e(s.toString());
                    Toast.makeText(TestActivity.this, "注册成功:" + s.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    LogUtils.e(e + "");
                }
            }
        });*/

    }
}
