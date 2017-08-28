package com.bishe.nongcun.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bishe.nongcun.R;
import com.bishe.nongcun.adapter.MyPushAdapter;
import com.bishe.nongcun.bean.WantBuyItem;
import com.bishe.nongcun.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import xyz.zpayh.adapter.OnItemClickListener;

public class MyPushActivity extends BaseActivity {

    @Bind(R.id.rv_my_push)
    RecyclerView rvMyPush;
    ArrayList<WantBuyItem> data;
    private MyPushAdapter myPushAdapter;

    @Override
    int getLayoutId() {
        return R.layout.activity_my_push;
    }

    @Override
    void initView() {
        myPushAdapter = new MyPushAdapter();
        data = new ArrayList<>();
        rvMyPush.setLayoutManager(new LinearLayoutManager(MyPushActivity.this));

        BmobQuery<WantBuyItem> query = new BmobQuery<WantBuyItem>();
        query.order("-createdAt");
        query.addWhereEqualTo("author", BmobUser.getCurrentUser());
        //返回6条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        // 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.include("author");
        //执行查询方法
        query.findObjects(new FindListener<WantBuyItem>() {
            @Override
            public void done(List<WantBuyItem> object, BmobException e) {
                if (e == null) {
                    LogUtils.e("查询成功：共" + object.size() + "条数据。");
                    for (WantBuyItem wantBuyItem : object) {
                        data.add(wantBuyItem);
                    }

                    myPushAdapter.setData(data);
                    rvMyPush.setAdapter(myPushAdapter);

                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });


    }

    @Override
    void initData() {
//        最近报价条目点击跳转
        myPushAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int adapterPosition) {
                Intent intent = new Intent(MyPushActivity.this, FoodDetailActivity.class);
                intent.putExtra("newprice",data.get(adapterPosition));
                startActivity(intent);
            }
        });
    }
}
