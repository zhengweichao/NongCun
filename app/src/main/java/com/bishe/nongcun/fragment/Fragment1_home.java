package com.bishe.nongcun.fragment;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.nongcun.R;
import com.bishe.nongcun.activity.FoodDetailActivity;
import com.bishe.nongcun.activity.FoodsActivity;
import com.bishe.nongcun.activity.MainActivity;
import com.bishe.nongcun.activity.MouDetailActivity;
import com.bishe.nongcun.activity.ShopListActivity;
import com.bishe.nongcun.activity.TestActivity;
import com.bishe.nongcun.activity.WXdemoActivity;
import com.bishe.nongcun.activity.WantBuyDetailActivity;
import com.bishe.nongcun.adapter.MainNewsBuyAdapter;
import com.bishe.nongcun.adapter.MainNewsPriceAdapter;
import com.bishe.nongcun.adapter.MoudleAdapter;
import com.bishe.nongcun.bean.MainNewsPrice;
import com.bishe.nongcun.bean.MoudleItem;
import com.bishe.nongcun.bean.PriceItem;
import com.bishe.nongcun.bean.WantBuyItem;
import com.bishe.nongcun.utils.LogUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import xyz.zpayh.adapter.IMultiItem;
import xyz.zpayh.adapter.OnItemClickListener;

/**
 * Created by 郑卫超 on 2017/5/23.
 */

public class Fragment1_home extends BaseFragment {
    private static final int IMAGE_PICKER = 1;
    ArrayList<IMultiItem> data;
    String[] MoudleName = {"庭院菜", "大棚菜", "粮油", "水果",
            "采摘", "蛋类", "菜地承包", "农家院"
    };
    int[] MoudleLogo = {R.mipmap.shucai, R.mipmap.shuiguo, R.mipmap.yangzhi, R.mipmap.liangyou,
            R.mipmap.miaomu, R.mipmap.zhongyao, R.mipmap.nongzi, R.mipmap.nongji};
    Class[] clazz = {MouDetailActivity.class, MouDetailActivity.class, MouDetailActivity.class, FoodDetailActivity.class,
            WXdemoActivity.class, TestActivity.class, MainActivity.class, MainActivity.class
    };
    private MoudleAdapter moudleAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView rv_main_newbuy;
    private RecyclerView rv_main_newprice;
    private GridLayoutManager mGridLayoutManager;
    private LinearLayoutManager mLMNewPrice;
    private LinearLayoutManager mLMNewBuy;
    private MainNewsPriceAdapter mainNewsPriceAdapter;
    private ArrayList<PriceItem> mainNewsesdataPrice;
    private ArrayList<WantBuyItem> mainNewsesdataBuy;
    private MainNewsBuyAdapter mainNewsBuyAdapter;
    private TextView bt_main_more_buy;
    private TextView bt_main_more_price;

    @Override
    protected View initView() {
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.fragment_1_home, null);

        mRecyclerView = (RecyclerView) inflate.findViewById(R.id.rv_main);
        rv_main_newbuy = (RecyclerView) inflate.findViewById(R.id.rv_main_newbuy);
        rv_main_newprice = (RecyclerView) inflate.findViewById(R.id.rv_main_newprice);
        bt_main_more_buy = (TextView) inflate.findViewById(R.id.bt_main_more_buy);
        bt_main_more_price = (TextView) inflate.findViewById(R.id.bt_main_more_price);

        return inflate;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void initData() {
        //设置布局管理器
        mGridLayoutManager = new GridLayoutManager(mActivity, 4);
        mLMNewPrice = new LinearLayoutManager(mActivity);
        mLMNewBuy = new LinearLayoutManager(mActivity);

        mRecyclerView.setLayoutManager(mGridLayoutManager);
        rv_main_newprice.setLayoutManager(mLMNewPrice);
        rv_main_newbuy.setLayoutManager(mLMNewBuy);
        //        数据
        data = new ArrayList<>();
        for (int i = 0; i < MoudleName.length; i++) {
            MoudleItem moudleItem = new MoudleItem(MoudleLogo[i], MoudleName[i], clazz[i]);
            data.add(moudleItem);
        }
        mainNewsesdataPrice = new ArrayList<>();
        mainNewsesdataBuy = new ArrayList<>();

        mainNewsBuyAdapter = new MainNewsBuyAdapter();

        iniNewsSaleData();
        initNewsBuyData();

       /* for (int i = 0; i < 6; i++) {
            MainNewsPrice mainNewsPrice = new MainNewsPrice("硬粉", "0.90元/斤", "05月21日", "山东临沂");

            mainNewsesdataPrice.add(mainNewsPrice);
        }*/

        //新建适配器
        moudleAdapter = new MoudleAdapter();
        mainNewsPriceAdapter = new MainNewsPriceAdapter();

        //绑定数据

        moudleAdapter.setData(data);

    }

    /**
     * 报价列表
     */
    private void iniNewsSaleData() {
        BmobQuery<PriceItem> query = new BmobQuery<PriceItem>();
        query.order("-createdAt");
        //返回6条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(6);
        // 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.include("author");
        //执行查询方法
        query.findObjects(new FindListener<PriceItem>() {
            @Override
            public void done(List<PriceItem> object, BmobException e) {
                if (e == null) {
                    LogUtils.e("查询成功：共" + object.size() + "条数据。");
                    for (PriceItem priceItem : object) {

                        mainNewsesdataPrice.add(priceItem);
                    }
                    mainNewsPriceAdapter.setData(mainNewsesdataPrice);
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }

    /**
     * 初始化求购信息
     */
    private void initNewsBuyData() {
        BmobQuery<WantBuyItem> query = new BmobQuery<WantBuyItem>();
        query.order("-createdAt");
        //返回6条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(6);
        // 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.include("author");
        //执行查询方法
        query.findObjects(new FindListener<WantBuyItem>() {
            @Override
            public void done(List<WantBuyItem> object, BmobException e) {
                if (e == null) {
                    LogUtils.e("查询成功：共" + object.size() + "条数据。");
                    for (WantBuyItem wantBuyItem : object) {

                        mainNewsesdataBuy.add(wantBuyItem);

                    }
                    mainNewsBuyAdapter.setData(mainNewsesdataBuy);
                    rv_main_newbuy.setAdapter(mainNewsBuyAdapter);
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void initListener() {
//        顶部分类项目===》跳转
        moudleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int adapterPosition) {
//                Toast.makeText(getActivity(), "aaaaa" + adapterPosition, Toast.LENGTH_SHORT).show();
                if (adapterPosition < MoudleLogo.length) {
                    MoudleItem item = (MoudleItem) data.get(adapterPosition);
                    //跳转到对应功能的详情页面
                    Class clazz = item.getClazz();
                    Intent intent = new Intent(getActivity(), clazz);
                    intent.putExtra("position", adapterPosition);
                    startActivity(intent);
                }
            }
        });
//        最近报价条目点击跳转
        mainNewsPriceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int adapterPosition) {
                Intent intent = new Intent(mActivity, FoodDetailActivity.class);
                intent.putExtra("newprice",mainNewsesdataPrice.get(adapterPosition));
                startActivity(intent);
            }
        });
//        最新求购条目点击跳转
        mainNewsBuyAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int adapterPosition) {
                Intent intent = new Intent(mActivity, WantBuyDetailActivity.class);
                intent.putExtra("wantbuy", mainNewsesdataBuy.get(adapterPosition));
                startActivity(intent);
            }
        });


//      设置适配器
        mRecyclerView.setAdapter(moudleAdapter);

        rv_main_newprice.setAdapter(mainNewsPriceAdapter);

        //        更多求购====》跳转
        bt_main_more_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, FoodsActivity.class);
                startActivity(intent);
            }
        });
//        更多报价===》跳转
        bt_main_more_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                MyAdapter adapter = new MyAdapter(images);
//                gridView.setAdapter(adapter);
            } else {
                Toast.makeText(mActivity, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
