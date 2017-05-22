package com.bishe.nongcun.activity;

import android.support.v4.app.Fragment;
import com.bishe.nongcun.R;
import com.bishe.nongcun.fragment.TabFragment1;
import com.bishe.nongcun.fragment.TabFragment2;
import com.bishe.nongcun.fragment.TabFragment3;
import com.bishe.nongcun.view.BottomTabView;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 陈序员 on 2017/4/27.
 * Email: Matthew_Chen_1994@163.com
 * Blog: https://blog.ifmvo.cn
 */

public class TestBottomTabBaseActivity extends BottomTabBaseActivity {

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
        fragments.add(new TabFragment1());
        fragments.add(new TabFragment2());
        fragments.add(new TabFragment3());
        fragments.add(new TabFragment3());
        return fragments;
    }

   /*
   中间按钮
   @Override
    protected View getCenterView() {
        ImageView centerView = new ImageView(this);
        centerView.setImageResource(R.mipmap.ic_launcher_round);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 200);
        layoutParams.leftMargin = 60;
        layoutParams.rightMargin = 60;
        layoutParams.bottomMargin = 0;
        centerView.setLayoutParams(layoutParams);
        centerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestBottomTabBaseActivity.this, "centerView 点击了", Toast.LENGTH_SHORT).show();
            }
        });
        return centerView;
    }*/
}
