package com.bishe.nongcun.activity;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bishe.nongcun.R;
import com.bishe.nongcun.fragment.FragmentFoods1;
import com.bishe.nongcun.fragment.FragmentFoods2;
import com.bishe.nongcun.fragment.FragmentFoods3;
import com.bishe.nongcun.fragment.FragmentFoods4;
import com.bishe.nongcun.utils.LogUtils;
import com.bishe.nongcun.utils.SPUtils;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FoodsActivity extends BaseActivity {

    @Bind(R.id.rbtn_qiugou)
    RadioButton rbtnQiugou;
    @Bind(R.id.rbtn_price)
    RadioButton rbtnPrice;
    @Bind(R.id.rg_cando_list)
    RadioGroup rgCandoList;
    @Bind(R.id.vp_foods)
    ViewPager vpFoods;
    @Bind(R.id.btnSound)
    Button btnSound;
    private MediaPlayer mediaPlayer;
    private String filename;

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

    @OnClick(R.id.btnSound)
    public void onViewClicked() {
        Boolean sound = (Boolean) SPUtils.get(FoodsActivity.this, "sound", true);
        if (sound) {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                play("001.mp3");
            }

        } else {
            Toast.makeText(this, "暂未开启语音帮助！", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

}