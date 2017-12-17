package com.bishe.nongcun;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

import com.bishe.nongcun.activity.AboutActivity;
import com.bishe.nongcun.utils.LogUtils;
import com.bishe.nongcun.utils.VibratorUtil;

import cn.bmob.push.PushConstants;

/**
 * @ 创建时间: 2017/11/5 on 17:06.
 * @ 描述：自定义的推送消息接收器
 * @ 作者: 郑卫超 QQ: 2318723605
 */

//TODO 集成：1.3、创建自定义的推送消息接收器，并在清单文件中注册
public class MyPushMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {

            LogUtils.e("bmob客户端收到推送内容：" + intent.getStringExtra("msg"));
            VibratorUtil.Vibrate(context, 800);
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setMessage(intent.getStringExtra("msg"))
                    .setTitle("您有新的消息")
                    .setPositiveButton("查看", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LogUtils.e("点击确定");
                        }
                    })
                    .setNegativeButton("稍后查看", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LogUtils.e("点击取消");
                            dialog.dismiss();
                        }
                    })
                    .create();
            //需要把对话框的类型设为TYPE_SYSTEM_ALERT，否则对话框无法在广播接收器里弹出
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialog.show();
        }
    }

}