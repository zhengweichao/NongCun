package com.bishe.nongcun.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bishe.nongcun.R;
import com.bishe.nongcun.adapter.MySaleAdapter;
import com.bishe.nongcun.bean.PriceItem;
import com.bishe.nongcun.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import xyz.zpayh.adapter.OnItemClickListener;
import xyz.zpayh.adapter.OnItemLongClickListener;

/**
 * 我的出售
 */
public class MySaleActivity extends BaseActivity {

    @Bind(R.id.rv_my_sale)
    RecyclerView rvMySale;

    ArrayList<PriceItem> data;
    private MySaleAdapter mySaleAdapter;

    @Override
    int getLayoutId() {
        return R.layout.activity_my_sale;
    }

    @Override
    void initView() {
        mySaleAdapter = new MySaleAdapter();
        data = new ArrayList<>();
        rvMySale.setLayoutManager(new LinearLayoutManager(MySaleActivity.this));

        BmobQuery<PriceItem> query = new BmobQuery<PriceItem>();
        query.order("-createdAt");
        query.addWhereEqualTo("author", BmobUser.getCurrentUser());
        //返回6条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        // 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.include("author");
        //执行查询方法
        query.findObjects(new FindListener<PriceItem>() {
            @Override
            public void done(List<PriceItem> object, BmobException e) {
                if (e == null) {
                    LogUtils.e("查询成功：共" + object.size() + "条数据。");
                    for (PriceItem priceItem : object) {
                        data.add(priceItem);
                    }

                    mySaleAdapter.setData(data);
                    rvMySale.setAdapter(mySaleAdapter);

                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }


    @Override
    void initListener() {
        mySaleAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull View view, final int adapterPosition) {

                AlertDialog dialog = new AlertDialog.Builder(MySaleActivity.this)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DelMyPush(data.get(adapterPosition).getObjectId());
                            }
                        })
                        .setMessage("确定要删除么？")
                        .setTitle("确定删除")
                        .setIcon(R.mipmap.ic_launcher)
                        .create();
                dialog.show();


                return true;
            }

        });
    }

    @Override
    void initData() {
//        最近报价条目点击跳转
        mySaleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int adapterPosition) {
                Intent intent = new Intent(MySaleActivity.this, FoodDetailActivity.class);
                intent.putExtra("newprice", data.get(adapterPosition));
                startActivity(intent);
            }
        });

    }

    /**
     * 删除发布信息
     *
     * @param Objectid
     */
    private void DelMyPush(String Objectid) {

        PriceItem query = new PriceItem();
        query.setObjectId(Objectid);
        //返回6条数据，如果不加上这条语句，默认返回10条数据
        //执行查询方法
        query.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {

                if (e == null) {
                    LogUtils.e("删除成功");
                } else {
                    LogUtils.e("删除失败" + e);
                }

            }
        });

    }


}
