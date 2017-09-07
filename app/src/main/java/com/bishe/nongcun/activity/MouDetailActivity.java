package com.bishe.nongcun.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bishe.nongcun.R;
import com.bishe.nongcun.adapter.MoudleAdapter;
import com.bishe.nongcun.bean.CityBean;
import com.bishe.nongcun.bean.DetailMoudleItem;
import com.bishe.nongcun.utils.CityData;
import com.bishe.nongcun.utils.LogUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import xyz.zpayh.adapter.IMultiItem;
import xyz.zpayh.adapter.OnItemClickListener;

public class MouDetailActivity extends BaseActivity {

    ArrayList<IMultiItem> data;
    private RecyclerView rv_detail;
    private MoudleAdapter moudleAdapter;
    String[] MoudleDetailName;
    private CityBean cityBean;
    private TextView tv_title;

    @Override
    int getLayoutId() {
        return R.layout.activity_mou_detail;
    }

    @Override
    void initView() {
        rv_detail = (RecyclerView) findViewById(R.id.rv_detail);
        tv_title = (TextView) findViewById(R.id.tv_title_moudle);
        rv_detail.setLayoutManager(new GridLayoutManager(this, 4));
    }

    @Override
    void initData() {
        int position = getIntent().getIntExtra("position", 0);
        switch (position) {
            case 0:
                tv_title.setText("庭院菜");
                break;
            case 1:
                tv_title.setText("大棚菜");
                break;
            case 2:
                tv_title.setText("粮油");
                break;
            case 3:
                tv_title.setText("水果");
                break;
            case 4:
                tv_title.setText("采摘");
                break;
            case 5:
                tv_title.setText("蛋类");
                break;
            case 6:
                tv_title.setText("菜地承包");
                break;
            case 7:
                tv_title.setText("农家院");
                break;
            default:
                break;
        }

        Gson gson = new Gson();
        LogUtils.e(CityData.getJson());
        cityBean = gson.fromJson(CityData.getJson(), CityBean.class);
        String[] provinceArray = new String[cityBean.getData().size()];
        List<CityBean.Data.City> cityList = cityBean.getData().get(position).getCity();
        MoudleDetailName = new String[cityList.size()];
        data = new ArrayList<>();
        for (int i = 0; i < MoudleDetailName.length; i++) {
            MoudleDetailName[i] = cityList.get(i).getName();
            data.add(new DetailMoudleItem(MoudleDetailName[i]));
        }
        moudleAdapter = new MoudleAdapter();
        moudleAdapter.setData(data);
    }

    @Override
    void initListener() {
        moudleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int adapterPosition) {
                // TODO: 2017/7/2 各个分类点击事件
                //携带不同信息，跳转详情页面
                Intent intent = new Intent(MouDetailActivity.this, FoodsActivity.class);
                intent.putExtra("select", "false");
                intent.putExtra("kind1", tv_title.getText().toString().trim());
                intent.putExtra("kind2", MoudleDetailName[adapterPosition]);
                startActivity(intent);
            }
        });
        rv_detail.setAdapter(moudleAdapter);
    }
}
