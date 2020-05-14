package com.naichuan.imessenger.app

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import cn.bmob.v3.Bmob
import com.hyphenate.chat.*
import com.naichuan.imessenger.BuildConfig
import com.naichuan.imessenger.R
import com.naichuan.imessenger.adapter.EMMessageListenerAdapter
import com.naichuan.imessenger.ui.activity.ChatActivity

class IMApplication: Application() {

    companion object {
        lateinit var instance: IMApplication
    }

    private val messageListener = object : EMMessageListenerAdapter() {
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            super.onMessageReceived(p0)
            if (isForeground()) {
                // Do nothing ...
            } else {
                showNotification(p0)
            }
        }
    }

    private fun showNotification(messages: MutableList<EMMessage>?) {
        messages?.forEach {
            var contentText = "非文本消息"
            if (it.type == EMMessage.Type.TXT) {
                contentText = (it.body as EMTextMessageBody).message
            }
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("username", it.conversationId())

            val taskStackBuilder = TaskStackBuilder.create(this).addParentStack(ChatActivity::class.java).addNextIntent(intent)
            val pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

            val notification = Notification.Builder(this)
                .setContentTitle("新消息")
                .setContentText(contentText)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_message_black_24dp))
                .setSmallIcon(R.drawable.ic_message_black_24dp)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .notification
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, notification)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        val emOptions = EMOptions()
        emOptions.acceptInvitationAlways = false
        EMClient.getInstance().init(applicationContext, emOptions)
        EMClient.getInstance().setDebugMode(BuildConfig.DEBUG)
        Bmob.initialize(applicationContext, "b976cc4c2898f83bea57a975a8244197")

        EMClient.getInstance().chatManager().addMessageListener(messageListener)
    }

    private fun isForeground(): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (process in manager.runningAppProcesses) {
            if (process.processName == packageName) {
                // 找到app进程
                return process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
            }
        }
        return false
    }
}