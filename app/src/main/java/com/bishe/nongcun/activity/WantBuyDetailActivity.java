package com.bishe.nongcun.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.bishe.nongcun.R;
import com.bishe.nongcun.bean.WantBuyItem;
import com.bishe.nongcun.utils.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WantBuyDetailActivity extends BaseActivity {

    @Bind(R.id.tv_wantbuy_title)
    TextView tvWantbuyTitle;
    @Bind(R.id.tv_wantbuy_count)
    TextView tvWantbuyCount;
    @Bind(R.id.tv_wantbuy_time)
    TextView tvWantbuyTime;
    @Bind(R.id.tv_wantbuy_anthor)
    TextView tvWantbuyAnthor;
    @Bind(R.id.tv_wantbuy_author_tel)
    TextView tvWantbuyAuthorTel;
    @Bind(R.id.tv_wantbuy_author_address)
    TextView tvWantbuyAuthorAddress;
    @Bind(R.id.tv_wantbuy_detail_content)
    TextView tvWantbuyDetailContent;
    @Bind(R.id.tv_wantbuy_kind)
    TextView tvWantbuyKind;

    @Override
    int getLayoutId() {
        return R.layout.activity_want_buy_detail;
    }

    @Override
    void initView() {
        WantBuyItem wantbuy = (WantBuyItem) getIntent().getExtras().get("wantbuy");
        LogUtils.e(wantbuy.getCreatedAt().toString());
        tvWantbuyTitle.setText("求购：" + wantbuy.getTitle());
        tvWantbuyKind.setText("求购品种：" + wantbuy.getKind1() + "-" + wantbuy.getKind2() + "-" + wantbuy.getKind3() + "-");
        tvWantbuyCount.setText("求购数量：" + wantbuy.getCount());
        tvWantbuyTime.setText("求购时间：" + wantbuy.getCreatedAt().substring(5, 7) + "月" +
                wantbuy.getCreatedAt().substring(8, 10) + "日");
        tvWantbuyAnthor.setText("采购人：" + wantbuy.getAuthor().getUsername());
        tvWantbuyAuthorTel.setText("联系方式：" + wantbuy.getAuthor().getMobilePhoneNumber());
        tvWantbuyAuthorAddress.setText("所在地区：" + wantbuy.getAuthor().getAddress());
        tvWantbuyDetailContent.setText(wantbuy.getContent());
    }

    @Override
    void initData() {

    }

}
