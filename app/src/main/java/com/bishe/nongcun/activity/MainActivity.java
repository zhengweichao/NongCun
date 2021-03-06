package com.bishe.nongcun.activity;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.Toast;

import com.bishe.nongcun.R;
import com.bishe.nongcun.fragment.Fragment1_home;
import com.bishe.nongcun.fragment.Fragment2_sale;
import com.bishe.nongcun.fragment.Fragment3_buy;
import com.bishe.nongcun.fragment.Fragment4_my;
import com.bishe.nongcun.view.BottomTabView;

import java.util.ArrayList;
import java.util.List;

/**
 * @ 创建时间: 2017/5/21 on 10:39.
 * @ 描述：此页面进行底部标签的初始化及设置
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class MainActivity extends BottomTabBaseActivity {
    private long mExitTime;

    @Override
    protected List<BottomTabView.TabItemView> getTabViews() {
        List<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();

        tabItemViews.add(new BottomTabView.TabItemView(this, "首页", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.main_home_nor, R.mipmap.main_home_pre));
        tabItemViews.add(new BottomTabView.TabItemView(this, "发布供应", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.main_issue_nor, R.mipmap.main_issue_pre));
        tabItemViews.add(new BottomTabView.TabItemView(this, "发布求购", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.main_buy_nor, R.mipmap.main_buy_pre));
        tabItemViews.add(new BottomTabView.TabItemView(this, "我的消息", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.main_user_nor, R.mipmap.main_user_pre));
        return tabItemViews;
    }

    @Override
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new Fragment1_home());
        fragments.add(new Fragment2_sale());
        fragments.add(new Fragment3_buy());
        fragments.add(new Fragment4_my());
        return fragments;
    }

    /**
     * 重写返回键返回方法，防止误触退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    //重写按键响应事件
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果按下的按键是返回键，并且按下次数不为0时，进入判断
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //如果距离上次点击返回键时间大于2秒，则弹出吐司，提示再按一次退出，并记录本次时间
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            }
            //否则的话，结束当前页面，并退出系统
            else {
                finish();
                System.exit(0);
            }
            return true;
        }
        //其它按键的响应事件仍按照父类中的逻辑执行
        return super.onKeyDown(keyCode, event);
    }

}
