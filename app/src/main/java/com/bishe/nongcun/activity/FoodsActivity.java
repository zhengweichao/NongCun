package com.bishe.nongcun.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bishe.nongcun.R;
import com.bishe.nongcun.fragment.FragmentFoods1;
import com.bishe.nongcun.fragment.FragmentFoods2;
import com.bishe.nongcun.fragment.FragmentFoods3;
import com.bishe.nongcun.fragment.FragmentFoods4;
import com.bishe.nongcun.utils.LogUtils;

import java.util.ArrayList;

import butterknife.Bind;

public class FoodsActivity extends BaseActivity {

    @Bind(R.id.rbtn_qiugou)
    RadioButton rbtnQiugou;
    @Bind(R.id.rbtn_price)
    RadioButton rbtnPrice;
    @Bind(R.id.rg_cando_list)
    RadioGroup rgCandoList;
    @Bind(R.id.vp_foods)
    ViewPager vpFoods;

    private ArrayList<Fragment> fragmentList;

    @Override
    int getLayoutId() {
        return R.layout.activity_foods;
    }

    @Override
    void initData() {
        fragmentList = new ArrayList<>();
        String selectable = getIntent().getStringExtra("select");
        if ("false".equals(selectable)) {
            fragmentList.add(new FragmentFoods3());
            fragmentList.add(new FragmentFoods4());
        } else {
            fragmentList.add(new FragmentFoods1());
            fragmentList.add(new FragmentFoods2());
        }
        vpFoods.setAdapter(new FoodsAdapter(getSupportFragmentManager()));
    }

    @Override
    void initListener() {
        rgCandoList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                LogUtils.i(checkedId + "条目id");
                if (checkedId == R.id.rbtn_qiugou) {
                    vpFoods.setCurrentItem(0);
                } else {
                    vpFoods.setCurrentItem(1);
                }
            }
        });

        vpFoods.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtils.e(position + "条目id");
                if (position == 0) {
                    rgCandoList.check(R.id.rbtn_qiugou);
                } else {
                    rgCandoList.check(R.id.rbtn_price);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        String item = getIntent().getStringExtra("item");
        if ("price".equals(item)) {
            rgCandoList.check(R.id.rbtn_price);
        } else {
            rgCandoList.check(R.id.rbtn_qiugou);
        }

    }

    class FoodsAdapter extends FragmentPagerAdapter {

        public FoodsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

}