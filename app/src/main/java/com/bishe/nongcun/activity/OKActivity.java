package com.bishe.nongcun.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bishe.nongcun.R;
import com.bishe.nongcun.adapter.MyPushAdapter;
import com.bishe.nongcun.adapter.MySaleAdapter;
import com.bishe.nongcun.bean.MyInstallation;
import com.bishe.nongcun.bean.MyUser;
import com.bishe.nongcun.bean.PriceItem;
import com.bishe.nongcun.bean.WantBuyItem;
import com.bishe.nongcun.utils.LogUtils;
import com.bishe.nongcun.view.LoadDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.PushListener;
import xyz.zpayh.adapter.OnItemClickListener;

public class OKActivity extends BaseActivity {
    @Bind(R.id.btn_my_wantbuy)
    Button btnMyWantbuy;
    @Bind(R.id.btn_btn_my_price)
    Button btnBtnMyPrice;
    @Bind(R.id.rv_ok_wantbuy)
    RecyclerView rvOkWantbuy;
    @Bind(R.id.rv_ok_price)
    RecyclerView rvOkPrice;

    private String kind1;
    private String kind2;
    private MyPushAdapter myPushAdapter;
    private ArrayList<WantBuyItem> wantBuyItems;
    private MySaleAdapter mySaleAdapter;
    private ArrayList<PriceItem> priceItems;

    @Override
    int getLayoutId() {
        return R.layout.activity_ok;
    }

    @Override
    void initData() {
        kind1 = getIntent().getStringExtra("kind1");
        kind2 = getIntent().getStringExtra("kind2");
        String needs = getIntent().getStringExtra("need");
        rvOkWantbuy.setLayoutManager(new LinearLayoutManager(OKActivity.this));
        rvOkPrice.setLayoutManager(new LinearLayoutManager(OKActivity.this));

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
        rvOkWantbuy.setVisibility(View.VISIBLE);
        myPushAdapter = new MyPushAdapter();
        wantBuyItems = new ArrayList<>();
        LoadDialog.show(OKActivity.this, "正在进行匹配");
        BmobQuery<WantBuyItem> query = new BmobQuery<WantBuyItem>();
        if (!TextUtils.isEmpty(kind1) && !TextUtils.isEmpty(kind2)) {
            query.addWhereEqualTo("kind1", kind1);
            query.addWhereEqualTo("kind2", kind2);
        }
        query.order("-createdAt");
        query.setLimit(10);
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
                    myPushAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(@NonNull View view, int adapterPosition) {
                            Intent intent = new Intent(OKActivity.this, WantBuyDetailActivity.class);
                            intent.putExtra("wantbuy", wantBuyItems.get(adapterPosition));
                            startActivity(intent);
                        }
                    });

                    rvOkWantbuy.setAdapter(myPushAdapter);
                    LogUtils.e("初始化加载完毕");
                    LoadDialog.dismiss(OKActivity.this);
                    if (wantBuyItems != null) {
                        push();
                    }

                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    LoadDialog.dismiss(OKActivity.this);
                }
            }
        });


    }

    private void push() {
        ArrayList<MyUser> myUsers = new ArrayList<>();
        for (int i = 0; i < wantBuyItems.size(); i++) {
            myUsers.add(wantBuyItems.get(i).getAuthor());
        }
        BmobPushManager bmobPushManager = new BmobPushManager();
        BmobQuery<MyInstallation> query0 = MyInstallation.getQuery();
        //TODO 替换成你作为判断需要推送的属性名和属性值，推送前请确认installation表已有该属性
        query0.addWhereContainedIn("myuser", myUsers);
        bmobPushManager.setQuery(query0);
        bmobPushManager.pushMessage("消息内容", new PushListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    LogUtils.e("推送成功！");
                } else {
                    LogUtils.e("异常：" + e.getMessage());
                }
            }
        });
    }

    /**
     * 寻找相匹配的报价
     */
    private void initFindPrice() {
        rvOkPrice.setVisibility(View.VISIBLE);
        mySaleAdapter = new MySaleAdapter();
        priceItems = new ArrayList<>();
        LoadDialog.show(OKActivity.this, "正在进行匹配");
        BmobQuery<PriceItem> query = new BmobQuery<PriceItem>();
        query.order("-createdAt");
        if (!TextUtils.isEmpty(kind1) && !TextUtils.isEmpty(kind2)) {
            LogUtils.e("添加条件" + kind1 + kind2);
            query.addWhereEqualTo("kind1", kind1);
            query.addWhereEqualTo("kind2", kind2);
        }
        query.setLimit(10);
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
                    mySaleAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(@NonNull View view, int position) {
                            Intent intent = new Intent(OKActivity.this, FoodDetailActivity.class);
                            intent.putExtra("newprice", priceItems.get(position));
                            startActivity(intent);
                        }
                    });

                    rvOkPrice.setAdapter(mySaleAdapter);
                    LoadDialog.dismiss(OKActivity.this);
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    LoadDialog.dismiss(OKActivity.this);
                }
            }
        });

    }

    @OnClick({R.id.btn_my_wantbuy, R.id.btn_btn_my_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_my_wantbuy:
                Intent intent = new Intent(OKActivity.this, MyPushActivity.class);
                startActivity(intent);

                break;
            case R.id.btn_btn_my_price:
                Intent intent0 = new Intent(OKActivity.this, MySaleActivity.class);
                startActivity(intent0);
                break;
        }
    }

}
