package com.bishe.nongcun.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.nongcun.R;
import com.bishe.nongcun.bean.MyUser;
import com.bishe.nongcun.bean.PriceItem;
import com.bishe.nongcun.imageloader.GlideImageLoader;
import com.bishe.nongcun.ui.ChatActivity;
import com.bishe.nongcun.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;

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
    @Bind(R.id.bt_detail_foods_im)
    Button btDetailFoodsIm;
    @Bind(R.id.tv_detail_foods_count)
    TextView tvDetailFoodsCount;

    private PriceItem newprice;

    @Override
    int getLayoutId() {
        return R.layout.activity_food_detail;
    }

    @Override
    void initView() {
        newprice = (PriceItem) getIntent().getExtras().get("newprice");

        ArrayList images = new ArrayList<>();
        if (newprice.getPic1() != null) {
            images.add(newprice.getPic1().getFileUrl());
        }
        if (newprice.getPic2() != null) {
            images.add(newprice.getPic2().getFileUrl());
        }
        if (newprice.getPic3() != null) {
            images.add(newprice.getPic3().getFileUrl());
        }
        if (images.size() == 0) {
            bannerFoodDetail.setVisibility(View.GONE);
        } else {
            //设置banner样式
            bannerFoodDetail.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置图片加载器
            bannerFoodDetail.setImageLoader(new GlideImageLoader());

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
    }

    @Override
    void initData() {
        if (!TextUtils.isEmpty(newprice.getCount())) {
            tvDetailFoodsCount.setText("产品数量：" + newprice.getCount());
        } else {
            tvDetailFoodsCount.setVisibility(View.GONE);
        }

        tvDetailFoodsName.setText(newprice.getTitle());
        tvDetailFoodsArea.setText("地区：" + newprice.getAuthor().getAddress());
        tvDetailFoodsDesc.setText(newprice.getContent());
        tvDetailFoodsPrice.setText("报价：" + newprice.getPrice());
        tvDetailFoodsTel.setText("联系方式：" + newprice.getAuthor().getMobilePhoneNumber());
        tvDetailFoodsTime.setText("更新时间：" + newprice.getUpdatedAt());

        if (newprice.getPic1() != null) {
            Glide.with(FoodDetailActivity.this)                             //配置上下文
                    .load(newprice.getPic1().getFileUrl())      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .error(R.mipmap.default_image)           //设置错误图片
                    .placeholder(R.mipmap.default_image)     //设置占位图片
//                .centerCrop()                             //完全填充
                    .fitCenter()
                    .into(ivDetailFoods1);
        } else {
            ivDetailFoods1.setVisibility(View.GONE);
        }
        if (newprice.getPic2() != null) {
            Glide.with(FoodDetailActivity.this)                             //配置上下文
                    .load(newprice.getPic2().getFileUrl())      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .error(R.mipmap.default_image)           //设置错误图片
                    .placeholder(R.mipmap.default_image)     //设置占位图片
                    .fitCenter()                            //完全显示
                    .into(ivDetailFoods2);
        } else {
            ivDetailFoods2.setVisibility(View.GONE);
        }
        if (newprice.getPic3() != null) {
            Glide.with(FoodDetailActivity.this)                             //配置上下文
                    .load(newprice.getPic3().getFileUrl())      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .error(R.mipmap.default_image)           //设置错误图片
                    .placeholder(R.mipmap.default_image)     //设置占位图片
                    .fitCenter()                            //完全显示
                    .into(ivDetailFoods3);
        } else {
            ivDetailFoods3.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.bt_detail_foods_tel, R.id.bt_detail_foods_im})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_detail_foods_tel:

                String mobilePhoneNumber = newprice.getAuthor().getMobilePhoneNumber();
//                如果用户手机号不为空
                if (!TextUtils.isEmpty(mobilePhoneNumber)) {
                    //通过intent携带数据，跳转至拨号页面
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobilePhoneNumber));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
            /*直接拨打电话
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:13930023254"));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);*/
                } else {
                    Toast.makeText(this, "该用户没有留下电话信息，请私信尝试", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_detail_foods_im:
                //获取到当前发布信息人的用户信息
                MyUser user = newprice.getAuthor();
                LogUtils.e("开始im" + user.getObjectId());
                BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(), user.getUsername(), null);
                //开启常态私聊会话，保存该会话到本地会话表中
                BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, null);
                //给intent设置数据，将序列化之后的常态私聊会话信息传递至聊天页面
                Bundle bundle = new Bundle();
                bundle.putSerializable("c", conversationEntrance);
                //跳转聊天页面
                Intent intent = new Intent(FoodDetailActivity.this, ChatActivity.class);
                intent.putExtra(FoodDetailActivity.this.getPackageName(), bundle);
                startActivity(intent);
                break;
        }
    }

}