package com.bishe.nongcun.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.nongcun.R;
import com.bishe.nongcun.bean.MyUser;
import com.bishe.nongcun.utils.LogUtils;
import com.bishe.nongcun.utils.SPUtils;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

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
    @Bind(R.id.btnSound)
    Button btnSound;
    @Bind(R.id.tv_info_tel_state)
    TextView tvInfoTelState;
    @Bind(R.id.bt_update_info)
    Button btUpdateInfo;
    private MediaPlayer mediaPlayer;
    private String filename;
    private TextView tv_update_tel_old;
    private EditText new_address;
    private EditText new_username;

    @Override
    int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    void initData() {
        final MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        if (myUser != null) {

            BmobQuery<MyUser> bmobQuery = new BmobQuery<MyUser>();
            bmobQuery.getObject(myUser.getObjectId(), new QueryListener<MyUser>() {
                @Override
                public void done(MyUser object, BmobException e) {
                    if (e == null) {
                        tvInfoUsername.setText(object.getUsername());
                        tvInfoName.setText("用户名：" + object.getUsername());
                        tvInfoAddress.setText("地址：" + object.getAddress());
                        tvInfoTel.setText("手机号：" + object.getMobilePhoneNumber());
                        Boolean state = object.getMobilePhoneNumberVerified();
                        LogUtils.e(state + "");
                        if (state) {
                            tvInfoTelState.setText("认证状态：已认证");
                            tvInfoState.setText("已认证");
                            tvInfoState.setTextColor(getResources().getColor(R.color.realred));
                        } else {
                            tvInfoTelState.setText("认证状态:未认证");
                            tvInfoState.setText("未认证");
                            tvInfoState.setTextColor(getResources().getColor(R.color.gray));
                        }
                        tvInfoCreateTime.setText("创建时间：" + object.getCreatedAt());
                    } else {
//

                    }
                }
            });


        } else {
            //缓存用户对象为空时， 可打开用户注册界面…
            Toast.makeText(this, "登录已经过期，请重新登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UserInfoActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    /**
     * 播放提示语音
     *
     * @param filename 文件名
     */
    private void play(String filename) {
        this.filename = filename;
        try {
            AssetManager assetManager = this.getAssets();   ////获得该应用的AssetManager
            AssetFileDescriptor afd = assetManager.openFd(filename);   //根据文件名找到文件
            //对mediaPlayer进行实例化
            mediaPlayer = new MediaPlayer();
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.reset();    //如果正在播放，则重置为初始状态
            }
            mediaPlayer.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());     //设置资源目录
            mediaPlayer.prepare();//缓冲
            mediaPlayer.start();//开始或恢复播放
        } catch (IOException e) {
            LogUtils.e("没有找到这个文件");
            e.printStackTrace();
        }
    }


    @OnClick({R.id.btnSound, R.id.bt_update_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSound:
                Boolean sound = (Boolean) SPUtils.get(UserInfoActivity.this, "sound", true);
                if (sound) {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    } else {
                        play("001.mp3");
                    }

                } else {
                    Toast.makeText(this, "暂未开启语音帮助！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_update_info:
                showUpdateDialog();
                break;
        }
    }

    /**
     * 显示更新信息的对话框
     */
    private void showUpdateDialog() {
        View viewUpInfo = LayoutInflater.from(UserInfoActivity.this).inflate(R.layout.dialog_upate_info, null);
        tv_update_tel_old = (TextView) viewUpInfo.findViewById(R.id.tv_update_tel_old);
        new_address = (EditText) viewUpInfo.findViewById(R.id.et_update_address_new);
        new_username = (EditText) viewUpInfo.findViewById(R.id.et_update_username_new);

        AlertDialog UpTeldialog = new AlertDialog.Builder(UserInfoActivity.this)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String address = new_address.getText().toString();
                        String username = new_username.getText().toString();
                        if (TextUtils.isEmpty(address) || TextUtils.isEmpty(username)) {
                            Toast.makeText(UserInfoActivity.this, "信息不能为空", Toast.LENGTH_SHORT).show();

                        } else {
                            UpdateInfo();
                        }
                    }
                })
                .setTitle("修改个人信息")
                .setIcon(R.mipmap.ic_launcher)
                .setView(viewUpInfo)
                .create();
        UpTeldialog.setCanceledOnTouchOutside(false);
        UpTeldialog.show();
    }

    /**
     * 更新信息
     */
    private void UpdateInfo() {
        String address = new_address.getText().toString();
        String username = new_username.getText().toString();
        if (TextUtils.isEmpty(address) || TextUtils.isEmpty(username)) {
            Toast.makeText(this, "信息不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        MyUser newUser = new MyUser();
        newUser.setAddress(address);
        newUser.setUsername(username);
        MyUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
        newUser.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    LogUtils.e("个人信息更新成功！");
                    Toast.makeText(UserInfoActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
                } else {
                    LogUtils.e("错误：" + e.getMessage());
                }
            }
        });
    }
}
