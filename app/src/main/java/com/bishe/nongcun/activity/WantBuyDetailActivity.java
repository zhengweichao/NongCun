package com.bishe.nongcun.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.nongcun.R;
import com.bishe.nongcun.bean.MyUser;
import com.bishe.nongcun.bean.WantBuyItem;
import com.bishe.nongcun.ui.ChatActivity;
import com.bishe.nongcun.utils.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;

public class WantBuyDetailActivity extends BaseActivity {

    @Bind(R.id.tv_wantbuy_title)
    TextView tvWantbuyTitle;
    @Bind(R.id.tv_wantbuy_count)
    TextView tvWantbuyCount;
    @Bind(R.id.tv_wantbuy_time)
    TextView tvWantbuyTime;
    @Bind(R.id.tv_wantbuy_anthor)
    TextView tvWantbuyAnthor;
    @Bind(R.id.tv_wantbuy_author_tel)
    TextView tvWantbuyAuthorTel;
    @Bind(R.id.tv_wantbuy_author_address)
    TextView tvWantbuyAuthorAddress;
    @Bind(R.id.tv_wantbuy_detail_content)
    TextView tvWantbuyDetailContent;
    @Bind(R.id.tv_wantbuy_kind)
    TextView tvWantbuyKind;
    @Bind(R.id.bt_want_buy_tel)
    Button btWantBuyTel;
    @Bind(R.id.bt_want_buy_im)
    Button btWantBuyIm;

    private WantBuyItem wantbuy;

    @Override
    int getLayoutId() {
        return R.layout.activity_want_buy_detail;
    }

    @Override
    void initView() {
        wantbuy = (WantBuyItem) getIntent().getExtras().get("wantbuy");
        LogUtils.e(wantbuy.getCreatedAt().toString());
        tvWantbuyTitle.setText("求购：" + wantbuy.getTitle());
        tvWantbuyKind.setText("求购品种：" + wantbuy.getKind1() + "-" + wantbuy.getKind2() + "-" + wantbuy.getKind3() + "-");
        tvWantbuyCount.setText("求购数量：" + wantbuy.getCount());
        tvWantbuyTime.setText("求购时间：" + wantbuy.getCreatedAt().substring(5, 7) + "月" +
                wantbuy.getCreatedAt().substring(8, 10) + "日");
        tvWantbuyAnthor.setText("采购人：" + wantbuy.getAuthor().getUsername());
        tvWantbuyAuthorTel.setText("联系方式：" + wantbuy.getAuthor().getMobilePhoneNumber());
        tvWantbuyAuthorAddress.setText("所在地区：" + wantbuy.getAuthor().getAddress());
        tvWantbuyDetailContent.setText(wantbuy.getContent());
    }

    @Override
    void initData() {

    }


    @OnClick({R.id.bt_want_buy_tel, R.id.bt_want_buy_im})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_want_buy_tel:
                String mobilePhoneNumber = wantbuy.getAuthor().getMobilePhoneNumber();
                if (!TextUtils.isEmpty(mobilePhoneNumber)) {
                    LogUtils.e("" + mobilePhoneNumber);
                    //跳到拨号页面
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobilePhoneNumber));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "该用户没有留下电话信息，请私信尝试", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_want_buy_im:
                MyUser user = wantbuy.getAuthor();
                LogUtils.e("开始im:"+user.getObjectId() );
                BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(), user.getUsername(),null);
                BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, null);
                Bundle bundle = new Bundle();
                bundle.putSerializable("c", conversationEntrance);
                Intent intent = new Intent(WantBuyDetailActivity.this,ChatActivity.class);
                intent.putExtra(WantBuyDetailActivity.this.getPackageName(),bundle);
                startActivity(intent);
                break;
        }
    }
}
