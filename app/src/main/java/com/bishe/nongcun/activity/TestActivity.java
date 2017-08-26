package com.bishe.nongcun.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.nongcun.R;
import com.bishe.nongcun.bean.MyUser;
import com.bishe.nongcun.bean.WantBuyItem;
import com.bishe.nongcun.bean.user_info;
import com.bishe.nongcun.utils.LogUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class TestActivity extends Activity {

    @Bind(R.id.aaa)
    TextView aaa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        //注册
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
        });



        /*BmobQuery<user_info> query = new BmobQuery<user_info>();
        query.getObject("R6mODDDQ", new QueryListener<user_info>() {

            @Override
            public void done(user_info object, BmobException e) {
                if (e == null) {
                    //获得playerName的信息
                    object.getUsername();
                    //获得数据的objectId信息
                    object.getObjectId();
                    //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                    object.getCreatedAt();
                    object.getPassword();
                    object.getIsreal();
                    object.getTel();
                    aaa.setText(object.getUsername() + "====" + object.getPassword());

                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }


        });*/


    }
}
