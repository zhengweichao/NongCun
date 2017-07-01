package com.bishe.nongcun.fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bishe.nongcun.R;
import com.bishe.nongcun.activity.MessageActivity;
import com.bishe.nongcun.adapter.KeyValueAdapter;
import com.bishe.nongcun.bean.KeyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @ 创建时间: 2017/5/26 on 10:39.
 * @ 描述：我的消息页面
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class TabFragment4 extends BaseFragment {

    private List<KeyValue> kvScreenList = new ArrayList<KeyValue>();
    private KeyValueAdapter adapterScreen;
    private ListView lvScreen;
    private List<KeyValue> kvOtherList = new ArrayList<KeyValue>();
    private KeyValueAdapter adapterOther;
    private ListView lvOther;

    @Nullable
    @Override
    protected View initView() {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.aty_setting, null);
        lvScreen = (ListView)inflate.findViewById(R.id.lvScreen);
        lvOther = (ListView)inflate.findViewById(R.id.lvOther);
        return inflate;
    }

    @Override
    public void initData() {
        adapterScreen = new KeyValueAdapter(mActivity, kvScreenList);
        lvScreen.setAdapter(adapterScreen);
        adapterOther = new KeyValueAdapter(mActivity, kvOtherList);
        lvOther.setAdapter(adapterOther);

        initScreen();//加载屏幕显示
        initOther();//加载其他设置
    }

    private void initScreen() {
        kvScreenList.clear();
        KeyValue f = new KeyValue("个人信息", "");
        kvScreenList.add(f);
        KeyValue s = new KeyValue("实名认证", "");
        kvScreenList.add(s);
        KeyValue c = new KeyValue("我的消息", "");
        kvScreenList.add(c);
    }
    private void initOther() {
        kvOtherList.clear();
        kvOtherList.add(new KeyValue("供应消息",""));
        kvOtherList.add(new KeyValue("求购消息",""));
        kvOtherList.add(new KeyValue("供应配置",""));
        KeyValue kv0 = new KeyValue("求购配置", "");
        kvOtherList.add(kv0);
        KeyValue kv1 = new KeyValue("检查更新", "");
        kvOtherList.add(kv1);
        KeyValue kv2 = new KeyValue("关于", "");
        kvOtherList.add(kv2);

        adapterOther.notifyDataSetChanged();
    }

    @Override
    public void initListener() {
        lvScreen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        //跳转到我的消息页面
                        Intent intent = new Intent(mActivity, MessageActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });

        lvOther.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
