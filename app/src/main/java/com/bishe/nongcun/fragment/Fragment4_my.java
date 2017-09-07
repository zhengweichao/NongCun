package com.bishe.nongcun.fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bishe.nongcun.R;
import com.bishe.nongcun.activity.AboutActivity;
import com.bishe.nongcun.activity.ConversationActivity;
import com.bishe.nongcun.activity.LoginActivity;
import com.bishe.nongcun.activity.MessageActivity;
import com.bishe.nongcun.activity.MyPushActivity;
import com.bishe.nongcun.activity.MySaleActivity;
import com.bishe.nongcun.activity.TestActivity;
import com.bishe.nongcun.activity.UserInfoActivity;
import com.bishe.nongcun.adapter.KeyValueAdapter;
import com.bishe.nongcun.bean.KeyValue;
import com.bishe.nongcun.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.v3.BmobUser;

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
    private Button btn_my_logout;

    @Nullable
    @Override
    protected View initView() {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_4_my, null);
        lvScreen = (ListView) inflate.findViewById(R.id.lvScreen);
        lvOther = (ListView) inflate.findViewById(R.id.lvOther);
        btn_my_logout = (Button) inflate.findViewById(R.id.btn_my_logout);
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
        KeyValue c = new KeyValue("我的消息", "");
        kvScreenList.add(c);
    }

    private void initOther() {
        kvOtherList.clear();
//        kvOtherList.add(new KeyValue("供应消息", ""));
//        kvOtherList.add(new KeyValue("求购消息", ""));
        kvOtherList.add(new KeyValue("供应配置", ""));
        kvOtherList.add(new KeyValue("求购配置", ""));
        kvOtherList.add(new KeyValue("检查更新", ""));
        kvOtherList.add(new KeyValue("关于", ""));

        adapterOther.notifyDataSetChanged();
    }

    @Override
    public void initListener() {
        btn_my_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();   //清除缓存用户对象

                //TODO 连接：3.2、退出登录需要断开与IM服务器的连接
                BmobIM.getInstance().disConnect();
                getActivity().finish();
                startActivity(new Intent(mActivity, LoginActivity.class));

            }
        });

        lvScreen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        // TODO: 2017/8/3 个人信息
                        intent = new Intent(mActivity, UserInfoActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //TODO 跳转到我的消息页面
                        intent = new Intent(mActivity, ConversationActivity.class);
                        startActivity(intent);
//                        intent = new Intent(mActivity, MessageActivity.class);
//                        startActivity(intent);
                        break;
                    default:
                        break;
                }


            }
        });

        lvOther.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = null;
                switch (position) {
                    case 0:
//                        供应配置
                        intent = new Intent(mActivity, MySaleActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
//                        求购配置
                        intent = new Intent(mActivity, MyPushActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
//                        检查更新
                        Toast.makeText(mActivity, "当前已是最新版本", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
//                        跳转到关于页面
                        intent = new Intent(mActivity, AboutActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }

            }
        });
    }
}
