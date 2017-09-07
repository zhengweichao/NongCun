package com.bishe.nongcun.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.bishe.nongcun.R;
import com.bishe.nongcun.bean.MyUser;
import com.bishe.nongcun.utils.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class UserInfoActivity extends BaseActivity {


    @Bind(R.id.tv_info_username)
    TextView tvInfoUsername;
    @Bind(R.id.tv_info_state)
    TextView tvInfoState;
    @Bind(R.id.tv_info_name)
    TextView tvInfoName;
    @Bind(R.id.tv_info_tel)
    TextView tvInfoTel;
    @Bind(R.id.tv_info_sex)
    TextView tvInfoSex;
    @Bind(R.id.tv_info_address)
    TextView tvInfoAddress;
    @Bind(R.id.tv_info_create_time)
    TextView tvInfoCreateTime;

    @Override
    int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    void initData() {

        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser != null) {

            // 允许用户使用应用
            BmobQuery<MyUser> query = new BmobQuery<MyUser>();
            query.getObject(bmobUser.getObjectId(), new QueryListener<MyUser>() {
                @Override
                public void done(MyUser myUser, BmobException e) {
                    if (e == null) {
                        tvInfoUsername.setText("用户名：" + myUser.getUsername());
                        tvInfoAddress.setText("手机号：" + myUser.getAddress());
                        tvInfoCreateTime.setText("创建时间：" + myUser.getCreatedAt());
                        tvInfoName.setText(myUser.getUsername());
                        tvInfoTel.setText("地址：" + myUser.getMobilePhoneNumber());
                    } else {
                        LogUtils.e("失败：" + e.getMessage() + "," + e.getErrorCode());
                    }
                }
            });


        } else {
            //缓存用户对象为空时， 可打开用户注册界面…
        }


    }

}
