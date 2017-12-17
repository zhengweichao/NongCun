package com.bishe.nongcun.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewTreeObserver;

import com.bishe.nongcun.R;
import com.bishe.nongcun.adapter0.ConversationAdapter;
import com.bishe.nongcun.adapter0.OnRecyclerViewListener;
import com.bishe.nongcun.adapter0.base.IMutlipleItem;
import com.bishe.nongcun.bean0.Conversation;
import com.bishe.nongcun.bean0.NewFriendConversation;
import com.bishe.nongcun.bean0.PrivateConversation;
import com.bishe.nongcun.db.NewFriend;
import com.bishe.nongcun.db.NewFriendManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.v3.BmobUser;

public class ConversationActivity extends BaseActivity {

    @Bind(R.id.rc_view)
    RecyclerView rcView;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;

    ConversationAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    int getLayoutId() {
        return R.layout.fragment_conversation;
    }

    @Override
    void initView() {
        //单一布局
        IMutlipleItem<Conversation> mutlipleItem = new IMutlipleItem<Conversation>() {

            @Override
            public int getItemViewType(int postion, Conversation c) {
                return 0;
            }

            @Override
            public int getItemLayoutId(int viewtype) {
                return R.layout.item_conversation;
            }

            @Override
            public int getItemCount(List<Conversation> list) {
                return list.size();
            }
        };
        adapter = new ConversationAdapter(ConversationActivity.this, mutlipleItem, null);
        rcView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(ConversationActivity.this);
        rcView.setLayoutManager(layoutManager);
        swRefresh.setEnabled(true);
        setListener();

    }

    private void setListener() {
//        设置监听事件，获得视图宽度或者高度
        swRefresh.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                swRefresh.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                swRefresh.setRefreshing(true);
                query();
            }
        });
//        设置刷新监听事件
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query();
            }
        });
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                adapter.getItem(position).onClick(ConversationActivity.this);
            }

            @Override
            public boolean onItemLongClick(final int position) {
                adapter.getItem(position).onLongClick(ConversationActivity.this);
                AlertDialog dialog = new AlertDialog.Builder(ConversationActivity.this)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })//设置取消按钮
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.remove(position);
                            }
                        })//设置确定按钮
                        .setTitle("退出登录")//设置标题
                        .setMessage("确定要退出登录么？")//设置内容文本信息
                        .setIcon(R.mipmap.ic_launcher)//设置应用图标
                        .create();//创建对话框
                dialog.show();//展示对话框

                return true;
            }
        });

    }

    /**
     * 查询本地会话
     */
    private void query() {
        adapter.bindDatas(getConversations());
        adapter.notifyDataSetChanged();
        swRefresh.setRefreshing(false);
    }

    /**
     * 获取会话列表的数据：增加新朋友会话
     *
     * @return
     */
    private List<Conversation> getConversations() {
        //添加会话
        List<Conversation> conversationList = new ArrayList<>();
        conversationList.clear();
        //TODO 会话：4.2、查询全部会话
        List<BmobIMConversation> list = BmobIM.getInstance().loadAllConversation();
        if (list != null && list.size() > 0) {
            for (BmobIMConversation item : list) {
                switch (item.getConversationType()) {
                    case 1://私聊
                        conversationList.add(new PrivateConversation(item));
                        break;
                    default:
                        break;
                }
            }
        }
        //添加新朋友会话-获取好友请求表中最新一条记录
        List<NewFriend> friends = NewFriendManager.getInstance(ConversationActivity.this).getAllNewFriend();
        if (friends != null && friends.size() > 0) {
            conversationList.add(new NewFriendConversation(friends.get(0)));
        }
        //重新排序
        Collections.sort(conversationList);
        return conversationList;
    }

    @Override
    void initData() {
        super.initData();
    }

    @Override
    void initListener() {
        super.initListener();
    }


}
