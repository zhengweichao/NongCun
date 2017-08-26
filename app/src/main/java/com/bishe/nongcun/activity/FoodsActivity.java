package com.bishe.nongcun.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.bishe.nongcun.R;

import java.util.ArrayList;

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
    @Bind(R.id.lv_foods)
    ListView lvFoods;

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