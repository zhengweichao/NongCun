package com.bishe.nongcun.fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bishe.nongcun.R;
import com.bishe.nongcun.activity.AboutActivity;
import com.bishe.nongcun.activity.MessageActivity;
import com.bishe.nongcun.adapter.KeyValueAdapter;
import com.bishe.nongcun.bean.KeyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @ 创建时间: 2017/5/26 on 10:39.
 * @ 描述：设置页面
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class Fragment4_my extends BaseFragment {

    private List<KeyValue> kvScreenList = new ArrayList<KeyValue>();
    private KeyValueAdapter adapterScreen;
    private ListView lvScreen;
    private List<KeyValue> kvOtherList = new ArrayList<KeyValue>();
    private KeyValueAdapter adapterOther;
    private ListView lvOther;

    @Nullable
    @Override
    protected View initView() {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_4_my, null);
        lvScreen = (ListView) inflate.findViewById(R.id.lvScreen);
        lvOther = (ListView) inflate.findViewById(R.id.lvOther);
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
        kvOtherList.add(new KeyValue("供应消息", ""));
        kvOtherList.add(new KeyValue("求购消息", ""));
        kvOtherList.add(new KeyValue("供应配置", ""));
        kvOtherList.add(new KeyValue("求购配置", ""));
        kvOtherList.add(new KeyValue("检查更新", ""));
        kvOtherList.add(new KeyValue("关于", ""));

        adapterOther.notifyDataSetChanged();
    }

    @Override
    public void initListener() {
        lvScreen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                switch (position) {
                    case 0:
                        // TODO: 2017/8/3 个人信息
                        break;
                    case 1:
                        // TODO: 2017/8/3 实名认证
                        break;
                    case 2:
                        //TODO 跳转到我的消息页面
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
//                        供应消息

                        break;
                    case 1:
//                        求购消息

                        break;
                    case 2:
//                        供应配置

                        break;
                    case 3:
//                        求购配置

                        break;
                    case 4:
//                        检查更新
                        Toast.makeText(mActivity, "当前已是最新版本", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
//                        跳转到关于页面
                        startActivity(new Intent(mActivity, AboutActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
