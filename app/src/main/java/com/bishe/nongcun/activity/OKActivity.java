package com.bishe.nongcun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bishe.nongcun.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OKActivity extends BaseActivity {
    @Bind(R.id.btn_my_wantbuy)
    Button btnMyWantbuy;
    @Bind(R.id.btn_btn_my_price)
    Button btnBtnMyPrice;

    @Override
    int getLayoutId() {
        return R.layout.activity_ok;
    }


    @OnClick({R.id.btn_my_wantbuy, R.id.btn_btn_my_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_my_wantbuy:
                Intent intent = new Intent(OKActivity.this, OKActivity.class);
                startActivity(intent);

                break;
            case R.id.btn_btn_my_price:
                Intent intent0 = new Intent(OKActivity.this, OKActivity.class);
                startActivity(intent0);
                break;
        }
    }
}
