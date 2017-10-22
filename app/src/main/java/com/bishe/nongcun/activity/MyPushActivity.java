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
import cn.bmob.v3.listener.UpdateListener;
import xyz.zpayh.adapter.OnItemClickListener;
import xyz.zpayh.adapter.OnItemLongClickListener;

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
        query.setLimit(50);
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
                Intent intent = new Intent(MyPushActivity.this, WantBuyDetailActivity.class);
                intent.putExtra("wantbuy", data.get(adapterPosition));
                startActivity(intent);
            }
        });

        myPushAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull View view, final int adapterPosition) {

                AlertDialog dialog = new AlertDialog.Builder(MyPushActivity.this)
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

    /**
     * 删除发布信息
     * @param Objectid
     */
    private void DelMyPush(String Objectid) {

        WantBuyItem query = new WantBuyItem();
        query.setObjectId(Objectid);
        //返回6条数据，如果不加上这条语句，默认返回10条数据
        //执行查询方法
        query.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {

                if(e==null){
                    LogUtils.e("删除成功");
                }else{
                    LogUtils.e("删除失败"+e);
                }

            }
        });

    }


}
