package com.bishe.nongcun.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.bishe.nongcun.utils.CONFIG;
import com.bishe.nongcun.R;
import com.bishe.nongcun.adapter.MessageAdapter;
import com.bishe.nongcun.bean.Message;
import com.bishe.nongcun.net.MessageSend;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 创建日期:2016/12/9 on 23:04
 * 描述:消息页面
 * 作者:郭士超
 * QQ:1169380200
 */

public class MessageActivity extends Activity {

    private EditText edtSend;
    private String content_str;
    private MessageAdapter adapter;
    private String[] reply_array;
    private double currentTime = 0, oldTime = 0;
    private String id;
    private String token;
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ListView lvMessage = (ListView) findViewById(R.id.lvMessage);
        edtSend = (EditText) findViewById(R.id.edtSend);
        adapter = new MessageAdapter(CONFIG.messageList, this);
        lvMessage.setAdapter(adapter);

        reply_array = this.getResources().getStringArray(R.array.reply_tips);
        if (isFirst) {
            Message message = new Message(this.getResources().getString(R.string.welcome_tips), Message.RECEIVER, getTime());
            CONFIG.messageList.add(message);

            isFirst = false;
        }

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"".equals(edtSend.getText().toString().trim())) {
                    getTime();
                    content_str = edtSend.getText().toString().trim();
                    edtSend.setText("");
                    Message message = new Message(content_str, Message.SEND, getTime());
                    CONFIG.messageList.add(message);

                    JSONObject data;

                    data = new JSONObject();
                    try {
                        data.put(CONFIG.KEY_TEXT, message.getContent());
                        data.put(CONFIG.KEY_COLOR, Message.color);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    new MessageSend(id, token, data.toString(), new MessageSend.SuccessCallback() {
                        @Override
                        public void onSuccess(String errmsg, String msg) {
                            Message message = new Message(getRandomReplyTips(), Message.RECEIVER, getTime());
                            CONFIG.messageList.add(message);
                            adapter.notifyDataSetChanged();
                        }
                    }, new MessageSend.FailCallback() {
                        @Override
                        public void onFail(String errmsg, String msg) {
                            switch (errmsg) {
                                case CONFIG.ERRMSG_401:

                                    break;
                                default:
                                    Message message = new Message(msg, Message.RECEIVER, getTime());
                                    CONFIG.messageList.add(message);
                                    adapter.notifyDataSetChanged();
                                    break;
                            }
                        }
                    });

                    if (CONFIG.messageList.size() > 30) {
                        for (int i = 0; i < CONFIG.messageList.size() - 20; i++) {
                            CONFIG.messageList.remove(i);
                        }
                    }
                    adapter.notifyDataSetChanged();

                }
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    /**
     * 得到返回的内容
     *
     * @return
     */
    private String getRandomReplyTips() {
        String replyTip = null;
        int index = (int) (Math.random() * reply_array.length);
        replyTip = reply_array[index];
        return replyTip;
    }

    /**
     * 得到当前时间
     *
     * @return
     */
    private String getTime() {
        currentTime = System.currentTimeMillis();
        if (currentTime - oldTime >= 300) {
            oldTime = currentTime;
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            return format.format(new Date());
        } else {
            return "";
        }
    }
}

