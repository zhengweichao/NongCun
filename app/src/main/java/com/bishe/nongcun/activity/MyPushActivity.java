package com.bishe.nongcun.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.nongcun.R;
import com.bishe.nongcun.adapter.MyPushAdapter;
import com.bishe.nongcun.bean.WantBuyItem;
import com.bishe.nongcun.utils.CommonUtil;
import com.bishe.nongcun.utils.LogUtils;
import com.bishe.nongcun.view.CommonPopupWindow;

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
    private boolean tag = false;
    private CommonPopupWindow popupWindow;

    @Override
    int getLayoutId() {
        return R.layout.activity_my_push;
    }

    @Override
    void initView() {
        myPushAdapter = new MyPushAdapter();
        data = new ArrayList<>();
        rvMyPush.setLayoutManager(new LinearLayoutManager(MyPushActivity.this));
    }

    @Override
    void initData() {
        BmobQuery<WantBuyItem> query = new BmobQuery<WantBuyItem>();
        query.order("-createdAt");
        query.addWhereEqualTo("author", BmobUser.getCurrentUser());
        query.setLimit(10);
        query.include("author");
        //执行查询方法
        query.findObjects(new FindListener<WantBuyItem>() {

            @Override
            public void done(List<WantBuyItem> object, BmobException e) {
                if (e == null) {
                    data.clear();
                    LogUtils.e("查询成功：共" + object.size() + "条数据。");
                    for (WantBuyItem wantBuyItem : object) {
                        data.add(wantBuyItem);
                    }
                    if (myPushAdapter == null) {
                        myPushAdapter = new MyPushAdapter();
                    }
                    myPushAdapter.setData(data);
                    if (!tag) {
                        rvMyPush.setAdapter(myPushAdapter);
                    } else {
                        myPushAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }

        });

    }

    @Override
    void initListener() {
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
            public boolean onItemLongClick(@NonNull View view, final int position) {
                managerAdmin(position);
                return true;
            }

        });

    }

    /**
     * 删除发布信息
     *
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

                if (e == null) {
                    LogUtils.e("删除成功");
                    Toast.makeText(MyPushActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                    tag=true;
                    initData();
                } else {
                    LogUtils.e("删除失败" + e);
                    Toast.makeText(MyPushActivity.this, "删除失败，请稍后尝试！", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /**
     * 管理员操作框
     *
     * @param position
     */
    public void managerAdmin(final int position) {

        if (popupWindow != null && popupWindow.isShowing())
            return;
        View upView = LayoutInflater.from(this).inflate(R.layout.popup_up, null);
        //测量View的宽高
        CommonUtil.measureWidthAndHeight(upView);
//        final AdminBean adminBean = adminBeen.get(position);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_up)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .setBackGroundLevel(0.6f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        switch (layoutResId) {
                            case R.layout.popup_up:
                                Button btn_look_admin_info = (Button) view.findViewById(R.id.btn_take_photo);
                                Button btn_admin_send_msg = (Button) view.findViewById(R.id.btn_admin_send_msg);
                                Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
                                btn_look_admin_info.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // TODO: 2017/8/11 查看信息
                                        Intent intent = new Intent(MyPushActivity.this, WantBuyDetailActivity.class);
                                        intent.putExtra("wantbuy", data.get(position));
                                        startActivity(intent);

                                        if (popupWindow != null) {
                                            popupWindow.dismiss();
                                        }
                                    }
                                });

                                btn_admin_send_msg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // TODO: 2017/8/9 查看匹配信息
                                        Intent intent = new Intent(MyPushActivity.this, ResultActivity.class);
                                        intent.putExtra("kind1", data.get(position).getKind1());
                                        intent.putExtra("kind2", data.get(position).getKind2());
                                        intent.putExtra("need", "price");
                                        startActivity(intent);

                                        if (popupWindow != null) {
                                            popupWindow.dismiss();
                                        }
                                    }
                                });

                                btn_cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

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
                                                        DelMyPush(data.get(position).getObjectId());
                                                    }
                                                })
                                                .setMessage("确定要删除么？")
                                                .setTitle("确定删除")
                                                .setIcon(R.mipmap.ic_launcher)
                                                .create();
                                        dialog.show();

                                        if (popupWindow != null) {
                                            popupWindow.dismiss();
                                        }
                                    }
                                });
                        }
                    }
                })
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }



}
