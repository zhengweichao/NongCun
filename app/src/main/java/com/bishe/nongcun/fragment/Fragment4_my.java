package com.bishe.nongcun.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
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
import com.bishe.nongcun.utils.SPUtils;

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
    private Switch switch_state_sound;

    @Nullable
    @Override
    protected View initView() {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_4_my, null);
        lvScreen = (ListView) inflate.findViewById(R.id.lvScreen);
        lvOther = (ListView) inflate.findViewById(R.id.lvOther);
        btn_my_logout = (Button) inflate.findViewById(R.id.btn_my_logout);
//        根据id绑定控件
        switch_state_sound = (Switch) inflate.findViewById(R.id.switch_state_sound);
//        读取SharedPreferences中sound对应的值，当其为true时则判定打开了语音帮助。默认语音帮助为打开状态
        Boolean sound = (Boolean) SPUtils.get(mActivity, "sound", true);
//        设置语音帮助开关状态为设置状态
        switch_state_sound.setChecked(sound);

        return inflate;
    }

    @Override
    public void initData() {

        adapterScreen = new KeyValueAdapter(mActivity, kvScreenList);
        lvScreen.setAdapter(adapterScreen);
        adapterOther = new KeyValueAdapter(mActivity, kvOtherList);
        lvOther.setAdapter(adapterOther);

        initScreen();
        initOther();
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

        kvOtherList.add(new KeyValue("供应管理", ""));
        kvOtherList.add(new KeyValue("求购管理", ""));
        kvOtherList.add(new KeyValue("检查更新", ""));
        kvOtherList.add(new KeyValue("关于", ""));

        adapterOther.notifyDataSetChanged();
    }

    @Override
    public void initListener() {
        //        给开关设置状态监听事件，根据状态更改SharedPreferences中sound对应的值
        switch_state_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    SPUtils.put(mActivity, "sound", false);
                } else {
                    SPUtils.put(mActivity, "sound", true);
                }
            }
        });

/**
 * 退出登录
 *
 */
        btn_my_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(mActivity)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })//设置取消按钮
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BmobUser.logOut();   //清除缓存用户对象
                                //TODO 连接：3.2、退出登录需要断开与IM服务器的连接
                                BmobIM.getInstance().disConnect();//断开与IM服务器的连接
                                startActivity(new Intent(mActivity, LoginActivity.class));//跳转至登录页面
                                getActivity().finish();//结束当前页面
                            }
                        })//设置确定按钮
                        .setTitle("退出登录")//设置标题
                        .setMessage("确定要退出登录么？")//设置内容文本信息
                        .setIcon(R.mipmap.ic_launcher)//设置应用图标
                        .create();//创建对话框
                dialog.show();//展示对话框
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
