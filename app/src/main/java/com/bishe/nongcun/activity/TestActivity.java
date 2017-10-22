package com.bishe.nongcun.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.bishe.nongcun.R;
import com.bishe.nongcun.bean.MyUser;
import com.bishe.nongcun.bean0.User;
import com.bishe.nongcun.ui.ChatActivity;
import com.bishe.nongcun.utils.LogUtils;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.BmobUser;

public class TestActivity extends Activity {

    @Bind(R.id.aaa)
    TextView aaa;
    @Bind(R.id.bt_test)
    Button btTest;
    private MediaPlayer mediaPlayer;
    private int position;
    private String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);


//TODO 连接：3.1、登录成功、注册成功或处于登录状态重新打开应用后执行连接IM服务器的操作
/*        MyUser user = BmobUser.getCurrentUser(MyUser.class);

        if (!StringUtils.isEmpty(user.getObjectId())) {
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
        }*/
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
    //如果突然电话到来，停止播放音乐
    @Override
    protected void onPause()
    {
        if(mediaPlayer.isPlaying())
        {
            //保存当前播放点
            position = mediaPlayer.getCurrentPosition();
            mediaPlayer.stop();
        }
        super.onPause();
    }

    //
    @Override
    protected void onResume()
    {
        //如果电话结束，继续播放音乐
        if(position>0 && filename!=null)
        {
            try
            {
                play(filename);
                mediaPlayer.seekTo(position);
                position = 0;
            }
            catch (Exception e)
            {
                LogUtils.e(e.toString());
            }
        }
        super.onResume();
    }

    private void play(String filename) {
        this.filename=filename;
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

    @OnClick(R.id.bt_test)
    public void onViewClicked() {
       play("001.mp3");
//        mediaPlayer.pause();//暂停播放
//        mediaPlayer.start();//恢复播放
//        mediaPlayer.stop();//停止播放
//        mediaPlayer.release();//释放资源


/*聊天
//        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        LogUtils.e("开始" );
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        LogUtils.e(user.getObjectId() );
//        BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(), user.getUsername(),null);
        BmobIMUserInfo info = new BmobIMUserInfo("95cd4ee1b5", user.getUsername(),null);

        BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, null);
        Bundle bundle = new Bundle();
        bundle.putSerializable("c", conversationEntrance);
//        startActivity(ChatActivity.class, bundle);
       Intent intent = new Intent(TestActivity.this,ChatActivity.class);
        intent.putExtra(TestActivity.this.getPackageName(),bundle);
        startActivity(intent);*/

    }
}
