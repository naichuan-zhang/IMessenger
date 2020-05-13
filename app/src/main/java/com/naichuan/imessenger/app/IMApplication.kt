package com.naichuan.imessenger.app

import android.app.Application
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions
import com.naichuan.imessenger.BuildConfig

class IMApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        EMClient.getInstance().init(applicationContext, EMOptions())
        EMClient.getInstance().setDebugMode(BuildConfig.DEBUG)
    }
}