package com.bishe.nongcun.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.bishe.nongcun.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FoodsActivity extends BaseActivity {

    @Bind(R.id.rbtn_pifa)
    RadioButton rbtnPifa;
    @Bind(R.id.rbtn_qiugou)
    RadioButton rbtnQiugou;
    @Bind(R.id.rbtn_price)
    RadioButton rbtnPrice;
    @Bind(R.id.rbtn_news)
    RadioButton rbtnNews;
    @Bind(R.id.rg_cando_list)
    RadioGroup rgCandoList;
    @Bind(R.id.spinner2)
    Spinner spinner2;
    @Bind(R.id.spinner3)
    Spinner spinner3;
    @Bind(R.id.rv_foods_all)
    RecyclerView rvFoodsAll;


    @Override
    int getLayoutId() {
        return R.layout.activity_foods;
    }

    @Override
    void initView() {
        rgCandoList.check(R.id.rbtn_pifa);
    }

    @Override
    void initData() {
        rvFoodsAll.setLayoutManager(new LinearLayoutManager(FoodsActivity.this));
    }

    @Override
    void initListener() {

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


}