package com.kotlin.mall.message.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cn.jpush.android.api.JPushInterface
import android.support.v4.app.NotificationCompat.getExtras
import android.util.Log
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.provider.event.MessageBadgeEvent
import com.kotlin.provider.router.RouterPath
import org.json.JSONObject


class MessageReceiver: BroadcastReceiver() {
    val TAG = "MessageReceiver"

    override fun onReceive(context: Context, intent: Intent) {


        val bundle = intent.extras
        Log.d(TAG, "onReceive - " + intent.action + ", extras: " + bundle )
        // 保存 JPushId, 注册成功之后会生成id
        Log.d("jPush","Application id = ${JPushInterface.getRegistrationID(context)}")
        AppPrefsUtils.putString(BaseConstant.KEY_SP_JPUSH, JPushInterface.getRegistrationID(context))
        when {
            JPushInterface.ACTION_REGISTRATION_ID == intent.action -> Log.d(TAG, "JPush 用户注册成功")
            JPushInterface.ACTION_MESSAGE_RECEIVED == intent.action -> {
                Log.d(TAG, "接受到推送下来的自定义消息")
                Bus.send(MessageBadgeEvent(true))
                Toast.makeText(context, bundle.getString(JPushInterface.EXTRA_MESSAGE), Toast.LENGTH_LONG).show()
            }
            JPushInterface.ACTION_NOTIFICATION_RECEIVED == intent.action -> Log.d(TAG, "接受到推送下来的通知")
            JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action -> {
                Log.d(TAG, "用户点击打开了通知")

                val extra = bundle.getString(JPushInterface.EXTRA_EXTRA)
                val json = JSONObject(extra)
                val orderId = json.getString("orderId")
                // 跳转到 orderActivity
                ARouter.getInstance().build(RouterPath.MessageCenter.PATH_MESSAGE_ORDER)
                        .withInt(ProviderConstant.KEY_ORDER_ID, orderId.toInt())
                        .navigation()
            }
            else -> Log.d(TAG, "Unhandled intent - " + intent.action)
        }
    }

}