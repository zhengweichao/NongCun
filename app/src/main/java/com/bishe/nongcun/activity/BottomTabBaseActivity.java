package com.bishe.nongcun.activity;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bishe.nongcun.R;
import com.bishe.nongcun.bean.MyUser;
import com.bishe.nongcun.event.RefreshEvent;
import com.bishe.nongcun.util.IMMLeaks;
import com.bishe.nongcun.utils.LogUtils;
import com.bishe.nongcun.utils.SPUtils;
import com.bishe.nongcun.view.BottomTabView;
import com.orhanobut.logger.Logger;
import com.stephentuso.welcome.WelcomeHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * @ 创建时间: 2017/6/19 on 15:40.
 * @ 描述：底部标签基类
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public abstract class BottomTabBaseActivity extends AppCompatActivity {

    ViewPager viewPager;
    BottomTabView bottomTabView;
    FragmentPagerAdapter adapter;
    WelcomeHelper welcomeScreen;
    @Bind(R.id.btnSound)
    Button btnSound;
    @Bind(R.id.iv_newim_tips)
    ImageView ivNewimTips;
    private MediaPlayer mediaPlayer;
    private int position;
    private String filename;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_base_bottom_tab);
        ButterKnife.bind(this);

        initChatContact();
        welcomeScreen = new WelcomeHelper(this, VWelcomeActivity.class);
        welcomeScreen.show(savedInstanceState);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        bottomTabView = (BottomTabView) findViewById(R.id.bottomTabView);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return getFragments().get(position);
            }

            @Override
            public int getCount() {
                return getFragments().size();
            }
        };

        viewPager.setAdapter(adapter);

        if (getCenterView() == null) {
            bottomTabView.setTabItemViews(getTabViews());
        } else {
            bottomTabView.setTabItemViews(getTabViews(), getCenterView());
        }

        bottomTabView.setUpWithViewPager(viewPager);

    }

    protected void initChatContact() {
        final MyUser user = BmobUser.getCurrentUser(MyUser.class);
        //TODO 连接：3.1、登录成功、注册成功或处于登录状态重新打开应用后执行连接IM服务器的操作
        //判断用户是否登录，并且连接状态不是已连接，则进行连接操作
        if (!TextUtils.isEmpty(user.getObjectId()) &&
                BmobIM.getInstance().getCurrentStatus().getCode() != ConnectionStatus.CONNECTED.getCode()) {
            BmobIM.connect(user.getObjectId(), new ConnectListener() {
                @Override
                public void done(String uid, BmobException e) {
                    if (e == null) {
                        //服务器连接成功就发送一个更新事件，同步更新会话及主页的小红点
                        EventBus.getDefault().post(new RefreshEvent());
                        //TODO 会话：2.7、更新用户资料，用于在会话页面、聊天页面以及个人信息页面显示
                        BmobIM.getInstance().
                                updateUserInfo(new BmobIMUserInfo(user.getObjectId(),
                                        user.getUsername(), user.getAvatar()));
                    } else {
                        LogUtils.e(e.getMessage());
                    }
                }
            });
            //TODO 连接：3.3、监听连接状态，可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
            BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
                @Override
                public void onChange(ConnectionStatus status) {
                    LogUtils.e(status.getMsg());
                    Logger.i(BmobIM.getInstance().getCurrentStatus().getMsg());
                }
            });
        }
        //解决leancanary提示InputMethodManager内存泄露的问题
        IMMLeaks.fixFocusedViewLeak(getApplication());
    }

    protected abstract List<BottomTabView.TabItemView> getTabViews();

    protected abstract List<Fragment> getFragments();

    /**
     * 中间按钮
     *
     * @return
     */
    protected View getCenterView() {
        return null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        welcomeScreen.onSaveInstanceState(outState);
    }


    @OnClick(R.id.btnSound)
    public void onViewClicked() {
        // 读取SharedPreferences中sound对应的值，当其为true时则判定打开了语音帮助。当读取不到时，默认为true
        Boolean sound = (Boolean) SPUtils.get(BottomTabBaseActivity.this, "sound", true);
        //如果打开了语音帮助，则进行语音的播放或者暂停操作
        if (sound) {
            // 如果音频播放器不为空，并且正在播放声音，那么停止播放
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
            // 否则，进行声音的播放
            else {
                play("001.mp3");
            }
        }
        //如果没有打开语音帮助，则进行吐司提示
        else {
            Toast.makeText(this, "暂未开启语音帮助！", Toast.LENGTH_SHORT).show();
        }

    }

    private void checkRedPoint() {
        //TODO 会话：4.4、获取全部会话的未读消息数量
        int count = (int) BmobIM.getInstance().getAllUnReadCount();
        if (count > 0) {
            ivNewimTips.setVisibility(View.VISIBLE);
        } else {
            ivNewimTips.setVisibility(View.GONE);
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
            //获得应用的AssetManager
            AssetManager assetManager = this.getAssets();
            //根据文件名找到文件
            AssetFileDescriptor afd = assetManager.openFd(filename);
            //对mediaPlayer进行实例化
            mediaPlayer = new MediaPlayer();
            //如果正在播放，则重置为初始状态
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.reset();
            }
            //设置资源目录
            mediaPlayer.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());
            //缓冲
            mediaPlayer.prepare();
            //开始或恢复播放
            mediaPlayer.start();
        } catch (IOException e) {
            //如果找不到这个文件，则会产生IOException,包裹异常,防止APP崩溃，并打印异常信息
            LogUtils.e("没有找到这个文件" + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 如果音频播放器不为空，并且正在播放声音，那么停止播放
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    /**
     * 注册消息接收事件
     *
     * @param event
     */
    //TODO 消息接收：8.3、通知有在线消息接收
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        checkRedPoint();
    }

    /**
     * 注册离线消息接收事件
     *
     * @param event
     */
    //TODO 消息接收：8.4、通知有离线消息接收
    @Subscribe
    public void onEventMainThread(OfflineMessageEvent event) {
        checkRedPoint();
    }

    /**
     * 注册自定义消息接收事件
     *
     * @param event
     */
    //TODO 消息接收：8.5、通知有自定义消息接收
    @Subscribe
    public void onEventMainThread(RefreshEvent event) {
        checkRedPoint();
    }

}