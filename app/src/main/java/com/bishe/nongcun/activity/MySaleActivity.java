package com.bishe.nongcun.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bishe.nongcun.R;
import com.bishe.nongcun.adapter.MySaleAdapter;
import com.bishe.nongcun.bean.PriceItem;
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

/**
 * 我的出售
 */
public class MySaleActivity extends BaseActivity {

    @Bind(R.id.rv_my_sale)
    RecyclerView rvMySale;

    ArrayList<PriceItem> data;
    private MySaleAdapter mySaleAdapter;
    private boolean tag = false;


    @Override
    int getLayoutId() {
        return R.layout.activity_my_sale;
    }

    @Override
    void initView() {
        mySaleAdapter = new MySaleAdapter();
        data = new ArrayList<>();
        rvMySale.setLayoutManager(new LinearLayoutManager(MySaleActivity.this));
    }

    @Override
    void initListener() {
        //        我的出售条目长按点击事件
        mySaleAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull View view, final int position) {
                manageMySale(position);
                return true;
            }
        });

        //        我的出售条目点击跳转
        mySaleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int adapterPosition) {
                //跳转到详情页面，携带参数为已经经过序列化的出售条目的bean类。
                Intent intent = new Intent(MySaleActivity.this, FoodDetailActivity.class);
                intent.putExtra("newprice", data.get(adapterPosition));
                startActivity(intent);
            }
        });
    }

    @Override
    void initData() {
        BmobQuery<PriceItem> query = new BmobQuery<PriceItem>();
        query.order("-createdAt");
        query.addWhereEqualTo("author", BmobUser.getCurrentUser());
        query.setLimit(10);
        // 希望在查询的同时也把发布人的信息查询出来
        query.include("author");
        //执行查询方法
        query.findObjects(new FindListener<PriceItem>() {
            @Override
            public void done(List<PriceItem> object, BmobException e) {
                if (e == null) {
                    LogUtils.e("查询成功：共" + object.size() + "条数据。");
                    data.clear();
                    for (PriceItem priceItem : object) {
                        data.add(priceItem);
                    }

                    if (mySaleAdapter == null) {
                        mySaleAdapter = new MySaleAdapter();
                    }
                    mySaleAdapter.setData(data);
                    if (!tag) {
                        rvMySale.setAdapter(mySaleAdapter);
                    } else {
                        mySaleAdapter.notifyDataSetChanged();
                    }

                } else {
                    LogUtils.e("失败：" + e.getMessage() + "," + e.getErrorCode());
                }
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
        //执行删除
        //执行删除
        query.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    LogUtils.e("删除成功");
                    Toast.makeText(MySaleActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                    tag = true;
                    initData();
                } else {
                    LogUtils.e("删除失败" + e);
                    Toast.makeText(MySaleActivity.this, "删除失败，请稍后尝试！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private CommonPopupWindow popupWindow;

    /**
     * 出售操作框
     *
     * @param position
     */
    public void manageMySale(final int position) {
        //如果已经弹出popupWindow，则为重复点击，不做操作。
        if (popupWindow != null && popupWindow.isShowing())
            return;
        // 获取填充布局对象
        View upView = LayoutInflater.from(this).inflate(R.layout.popup_up, null);
        //测量View的宽高
        CommonUtil.measureWidthAndHeight(upView);
//        final AdminBean adminBean = adminBeen.get(position);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_up)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)//设置宽度以及高度
                .setBackGroundLevel(0.6f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)//设置动画类型
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
                                        Intent intent = new Intent(MySaleActivity.this, FoodDetailActivity.class);
                                        intent.putExtra("newprice", data.get(position));
                                        startActivity(intent);

                                        if (popupWindow != null) {
                                            popupWindow.dismiss();
                                        }
                                    }
                                });

                                btn_admin_send_msg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //跳转页面
                                        Intent intent = new Intent(MySaleActivity.this, ResultActivity.class);
                                        intent.putExtra("kind1", data.get(position).getKind1());
                                        intent.putExtra("kind2", data.get(position).getKind2());
                                        intent.putExtra("need", "wantbuy");
                                        startActivity(intent);

                                        if (popupWindow != null) {
                                            popupWindow.dismiss();
                                        }
                                    }
                                });

                                btn_cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //弹出确认删除提示框
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
//        从底部弹出popupWindow，进行显示
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);

    }

}
