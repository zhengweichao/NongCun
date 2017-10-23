package com.bishe.nongcun.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bishe.nongcun.R;
import com.bishe.nongcun.adapter.MyPushAdapter;
import com.bishe.nongcun.adapter.MySaleAdapter;
import com.bishe.nongcun.bean.PriceItem;
import com.bishe.nongcun.bean.WantBuyItem;
import com.bishe.nongcun.utils.LogUtils;
import com.bishe.nongcun.view.LoadDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ResultActivity extends BaseActivity {

    @Bind(R.id.rv_result_price)
    RecyclerView rvResultPrice;
    @Bind(R.id.rv_result_wantbuy)
    RecyclerView rvResultWantbuy;
    private String kind1;
    private String kind2;
    private MyPushAdapter myPushAdapter;
    private ArrayList<WantBuyItem> wantBuyItems;
    private MySaleAdapter mySaleAdapter;
    private ArrayList<PriceItem> priceItems;
    @Override
    int getLayoutId() {
        return R.layout.activity_result;
    }

    @Override
    void initView() {
        kind1 = getIntent().getStringExtra("kind1");
        kind2 = getIntent().getStringExtra("kind2");
        String needs = getIntent().getStringExtra("need");

        rvResultWantbuy.setLayoutManager(new LinearLayoutManager(ResultActivity.this));
        rvResultPrice.setLayoutManager(new LinearLayoutManager(ResultActivity.this));

        if ("price".equals(needs)) {
            initFindPrice();
        } else if ("wantbuy".equals(needs)) {
            initFindWantBuy();
        } else {
            return;
        }
    }

    /**
     * 寻找相匹配的求购
     */
    private void initFindWantBuy() {
        rvResultWantbuy.setVisibility(View.VISIBLE);
        myPushAdapter = new MyPushAdapter();
        wantBuyItems = new ArrayList<>();
        LoadDialog.show(ResultActivity.this,"正在进行匹配");
        BmobQuery<WantBuyItem> query = new BmobQuery<WantBuyItem>();
        if (!TextUtils.isEmpty(kind1) && !TextUtils.isEmpty(kind2)) {
            query.addWhereEqualTo("kind1", kind1);
            query.addWhereEqualTo("kind2", kind2);
        }
        query.order("-createdAt");
        query.setLimit(20);
        query.include("author");
        //执行查询方法
        query.findObjects(new FindListener<WantBuyItem>() {
            @Override
            public void done(List<WantBuyItem> object, BmobException e) {
                if (e == null) {
                    LogUtils.e("查询成功：共" + object.size() + "条数据。");
                    for (WantBuyItem wantBuyItem : object) {
                        wantBuyItems.add(wantBuyItem);
                    }
                    myPushAdapter.setData(wantBuyItems);
                    rvResultWantbuy.setAdapter(myPushAdapter);
                    LogUtils.e("初始化加载完毕");
                    LoadDialog.dismiss(ResultActivity.this);
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    LoadDialog.dismiss(ResultActivity.this);
                }
            }
        });
    }

    /**
     * 寻找相匹配的报价
     */
    private void initFindPrice() {
        rvResultPrice.setVisibility(View.VISIBLE);
        mySaleAdapter = new MySaleAdapter();
        priceItems = new ArrayList<>();
        LoadDialog.show(ResultActivity.this,"正在进行匹配");
        BmobQuery<PriceItem> query = new BmobQuery<PriceItem>();
        query.order("-createdAt");
        if (!TextUtils.isEmpty(kind1) && !TextUtils.isEmpty(kind2)) {
            LogUtils.e("添加条件:"+kind1+kind2);
            query.addWhereEqualTo("kind1", kind1);
            query.addWhereEqualTo("kind2", kind2);
        }
        query.setLimit(20);
        query.include("author");
        //执行查询方法
        query.findObjects(new FindListener<PriceItem>() {
            @Override
            public void done(List<PriceItem> object, BmobException e) {
                if (e == null) {
                    LogUtils.e("查询成功：共" + object.size() + "条数据。");
                    for (PriceItem priceItem : object) {
                        priceItems.add(priceItem);
                    }
                    mySaleAdapter.setData(priceItems);
                    rvResultPrice.setAdapter(mySaleAdapter);
                    LoadDialog.dismiss(ResultActivity.this);
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    LoadDialog.dismiss(ResultActivity.this);
                }
            }
        });

    }
    
}
