package com.bishe.nongcun.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.nongcun.R;
import com.bishe.nongcun.activity.OKActivity;
import com.bishe.nongcun.bean.MyUser;
import com.bishe.nongcun.bean.WantBuyItem;
import com.bishe.nongcun.utils.ChooseCityInterface;
import com.bishe.nongcun.utils.ChooseCityUtil;
import com.bishe.nongcun.utils.LogUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 郑卫超 on 2017/5/3.
 * 发布求购
 */

public class Fragment3_buy extends BaseFragment {
    private TextView tvCity;
    private EditText et_content;
    private EditText et_count;
    private EditText et_title;
    private Button bt_push_enter;
    private String kind1;
    private String kind2;
    private String kind3;

    @Override
    protected View initView() {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_3_buy, null);
        tvCity = (TextView) inflate.findViewById(R.id.tv_select_mou_buy);
        et_content = (EditText) inflate.findViewById(R.id.et_push_wantbuy_content);
        et_count = (EditText) inflate.findViewById(R.id.et_push_wantbuy_count);
        et_title = (EditText) inflate.findViewById(R.id.et_push_wantbuy_title);
        bt_push_enter = (Button) inflate.findViewById(R.id.bt_push_enter);
        return inflate;
    }

    @Override
    public void initData() {
        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseCityUtil cityUtil = new ChooseCityUtil();
                String[] oldCityArray = {"水果", "苹果", "苹果"};
                cityUtil.createDialog(mActivity, oldCityArray, new ChooseCityInterface() {
                    @Override
                    public void sure(String[] newCityArray) {
                        //oldCityArray为传入的默认值 newCityArray为返回的结果
                        tvCity.setText(newCityArray[0] + "-" + newCityArray[1] + "-" + newCityArray[2]);

                        kind1 = newCityArray[0];
                        kind2 = newCityArray[1];
                        kind3 = newCityArray[2];

                    }
                });
            }
        });
    }

    private void showOkDialog() {
        AlertDialog dialog = new AlertDialog.Builder(mActivity)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        initView();
                    }
                })
                .setMessage("您已经添加成功")
                .create();
        dialog.show();
    }

    @Override
    public void initListener() {
        bt_push_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: 2017/8/24 发布
                String content = et_content.getText().toString().trim();
                String count = et_count.getText().toString().trim();
                String title = et_title.getText().toString().trim();

                if (TextUtils.isEmpty(content) || TextUtils.isEmpty(count) || TextUtils.isEmpty(title)) {
                    Toast.makeText(mActivity, "请您完整地填写信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(kind1)) {
                    Toast.makeText(mActivity, "请选择分类", Toast.LENGTH_SHORT).show();
                    return;
                }

                LogUtils.e("马上就要发布了……");
                WantBuyItem wantBuyItem = new WantBuyItem();
                //注意：不能调用.setObjectId("")方法
                MyUser user = BmobUser.getCurrentUser(MyUser.class);
                wantBuyItem.setContent(content);
                wantBuyItem.setCount(count);
                wantBuyItem.setKind1(kind1);
                wantBuyItem.setKind2(kind2);
                wantBuyItem.setKind3(kind3);
                wantBuyItem.setTitle(title);
                wantBuyItem.setAuthor(user);
                wantBuyItem.save(new SaveListener<String>() {

                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {
                            Toast.makeText(mActivity, "创建数据成功", Toast.LENGTH_SHORT).show();
                            LogUtils.e("创建数据成功：" + objectId);
//                            showOkDialog();
                            Intent intent = new Intent(mActivity, OKActivity.class);
                            startActivity(intent);
                        } else {
                            LogUtils.e("失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });


            }
        });

    }
}