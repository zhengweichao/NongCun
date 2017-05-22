package com.bishe.nongcun.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.bishe.nongcun.R;
import com.bishe.nongcun.activity.MainActivity;
import com.bishe.nongcun.adapter.MainNewsAdapter;
import com.bishe.nongcun.adapter.MoudleAdapter;
import com.bishe.nongcun.bean.MainNews;
import com.bishe.nongcun.bean.MoudleItem;
import java.util.ArrayList;
import xyz.zpayh.adapter.IMultiItem;
import xyz.zpayh.adapter.OnItemClickListener;

/**
 * Created by 郑卫超 on 2017/5/3.
 */

public class TabFragment1 extends BaseFragment {
    ArrayList<IMultiItem> data;
    String[] MoudleName = {"庭院菜","大棚菜","粮油",
            "水果","采摘","蛋类",
            "菜地承包","农家院"
    };
    int[] MoudleLogo={R.mipmap.shucai,R.mipmap.shuiguo,R.mipmap.yangzhi,
            R.mipmap.liangyou,R.mipmap.miaomu,R.mipmap.zhongyao,
            R.mipmap.nongzi,R.mipmap.nongji};
    Class[] clazz={MainActivity.class,MainActivity.class,MainActivity.class,
            MainActivity.class,MainActivity.class,MainActivity.class,
            MainActivity.class,MainActivity.class
    };
    private MoudleAdapter moudleAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView rv_main_newbuy;
    private RecyclerView rv_main_newprice;
    private GridLayoutManager mGridLayoutManager;
    private LinearLayoutManager mLMNewPrice;
    private LinearLayoutManager mLMNewBuy;
    private MainNewsAdapter mainNews;
    private ArrayList<MainNews> mainNewsesdata;


    @Override
    protected View initView() {
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.activity_main, null);

        mRecyclerView = (RecyclerView) inflate.findViewById(R.id.rv_main);
        rv_main_newbuy = (RecyclerView) inflate.findViewById(R.id.rv_main_newbuy);
        rv_main_newprice = (RecyclerView) inflate.findViewById(R.id.rv_main_newprice);

        return inflate;
    }

    @Override
    public void initData() {
        //设置布局管理器
        mGridLayoutManager = new GridLayoutManager(mActivity, 4);
        mLMNewPrice = new LinearLayoutManager(mActivity);
        mLMNewBuy = new LinearLayoutManager(mActivity);

        mRecyclerView.setLayoutManager(mGridLayoutManager);
        rv_main_newprice.setLayoutManager(mLMNewPrice);
        rv_main_newbuy.setLayoutManager(mLMNewBuy);
        data = new ArrayList<>();
        for (int i = 0; i < MoudleName.length; i++) {
            MoudleItem moudleItem = new MoudleItem(MoudleLogo[i], MoudleName[i],clazz[i]);
            data.add(moudleItem);
        }

        mainNewsesdata = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            MainNews mainNews = new MainNews("" + i, "" + i, "" + i, "" + i);
//            MoudleItem moudleItem = new MoudleItem(MoudleLogo[i], MoudleName[i],clazz[i]);
            mainNewsesdata.add(mainNews);
        }

        int width = getResources().getDisplayMetrics().widthPixels / mGridLayoutManager.getSpanCount();
        Log.i("zwc", "onCreateView: "+width);

        moudleAdapter = new MoudleAdapter();
        mainNews = new MainNewsAdapter();
        mainNews.setData(mainNewsesdata);
        moudleAdapter.setData(data);

        rv_main_newbuy.setAdapter(mainNews);
        rv_main_newprice.setAdapter(mainNews);

    }

    @Override
    public void initListener() {
        moudleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int adapterPosition) {
                Toast.makeText(getActivity(), "aaaaa"+adapterPosition, Toast.LENGTH_SHORT).show();
                if (adapterPosition<MoudleLogo.length){
                    MoudleItem item = (MoudleItem) data.get(adapterPosition);
                    //跳转到对应功能的详情页面
                    Class clazz =item.getClazz();
                    Intent intent = new Intent(getActivity(), clazz);
                    startActivity(intent);
                }
            }
        });

        mRecyclerView.setAdapter(moudleAdapter);

    }
}
