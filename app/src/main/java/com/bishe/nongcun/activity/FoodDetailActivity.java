package com.bishe.nongcun.activity;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bishe.nongcun.R;
import com.bishe.nongcun.bean.PriceItem;
import com.bishe.nongcun.imageloader.GlideImageLoader;
import com.bishe.nongcun.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;

import butterknife.Bind;

public class FoodDetailActivity extends BaseActivity {

    @Bind(R.id.banner_food_detail)
    Banner bannerFoodDetail;
    @Bind(R.id.tv_detail_foods_name)
    TextView tvDetailFoodsName;
    @Bind(R.id.tv_detail_foods_price)
    TextView tvDetailFoodsPrice;
    @Bind(R.id.tv_detail_foods_tel)
    TextView tvDetailFoodsTel;
    @Bind(R.id.tv_detail_foods_time)
    TextView tvDetailFoodsTime;

    Integer[] imageses = {R.mipmap.girl, R.mipmap.bg_splash, R.mipmap.girl, R.mipmap.img_userhead};
    @Bind(R.id.tv_detail_foods_area)
    TextView tvDetailFoodsArea;
    @Bind(R.id.bt_detail_foods_tel)
    Button btDetailFoodsTel;
    @Bind(R.id.tv_detail_foods_desc)
    TextView tvDetailFoodsDesc;
    @Bind(R.id.iv_detail_foods_1)
    ImageView ivDetailFoods1;
    @Bind(R.id.iv_detail_foods_2)
    ImageView ivDetailFoods2;
    @Bind(R.id.iv_detail_foods_3)
    ImageView ivDetailFoods3;
    private PriceItem newprice;

    @Override
    int getLayoutId() {
        return R.layout.activity_food_detail;
    }

    @Override
    void initView() {

        newprice = (PriceItem) getIntent().getExtras().get("newprice");
        //设置banner样式
        bannerFoodDetail.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        bannerFoodDetail.setImageLoader(new GlideImageLoader());
        ArrayList images = new ArrayList<>();

        images.add(newprice.getPic1().getFileUrl());
        images.add(newprice.getPic2().getFileUrl());
        images.add(newprice.getPic3().getFileUrl());

//        for (int i = 0; i < imageses.length; i++) {
//            images.add(imageses[i]);
//        }

        //设置图片集合
        bannerFoodDetail.setImages(images);
        //设置banner动画效果
//        bannerFoodDetail.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        bannerFoodDetail.setBannerTitles(titles);
        //设置自动轮播，默认为true
        bannerFoodDetail.isAutoPlay(true);
        //设置轮播时间
        bannerFoodDetail.setDelayTime(2800);
        //设置指示器位置（当banner模式中有指示器时）
        bannerFoodDetail.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        bannerFoodDetail.start();
    }

    @Override
    void initData() {


        tvDetailFoodsName.setText(newprice.getTitle());
        tvDetailFoodsArea.setText("地区：" + newprice.getAuthor().getAddress());
        tvDetailFoodsDesc.setText(newprice.getContent());
        tvDetailFoodsPrice.setText("报价：" + newprice.getPrice());
        tvDetailFoodsTel.setText("联系方式：" + newprice.getAuthor().getMobilePhoneNumber());
        tvDetailFoodsTime.setText("更新时间：" + newprice.getUpdatedAt());

        LogUtils.e(newprice.getPic1().getFileUrl());
        Glide.with(FoodDetailActivity.this)                             //配置上下文
                .load(newprice.getPic1().getFileUrl())      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(R.mipmap.default_image)           //设置错误图片
                .placeholder(R.mipmap.default_image)     //设置占位图片
//                .centerCrop()                             //完全填充
                .fitCenter()
                .into(ivDetailFoods1);

        Glide.with(FoodDetailActivity.this)                             //配置上下文
                .load(newprice.getPic2().getFileUrl())      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(R.mipmap.default_image)           //设置错误图片
                .placeholder(R.mipmap.default_image)     //设置占位图片
                .fitCenter()                            //完全显示
                .into(ivDetailFoods2);

        Glide.with(FoodDetailActivity.this)                             //配置上下文
                .load(newprice.getPic3().getFileUrl())      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(R.mipmap.default_image)           //设置错误图片
                .placeholder(R.mipmap.default_image)     //设置占位图片
                .fitCenter()                            //完全显示
                .into(ivDetailFoods3);


    }

}
