package com.bishe.nongcun.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.bishe.nongcun.R;
import com.bishe.nongcun.view.BottomTabView;
import com.stephentuso.welcome.WelcomeHelper;

import java.util.List;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_base_bottom_tab);

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

    protected abstract List<BottomTabView.TabItemView> getTabViews();

    protected abstract List<Fragment> getFragments();

    protected View getCenterView() {
        return null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        welcomeScreen.onSaveInstanceState(outState);
    }

}
