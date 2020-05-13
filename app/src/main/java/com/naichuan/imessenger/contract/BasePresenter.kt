package com.naichuan.imessenger.contract

import android.os.Handler
import android.os.Looper

interface BasePresenter {

    companion object {
        val handler by lazy {
            Handler(Looper.getMainLooper())
        }
    }

    fun runOnMainThread(f: () -> Unit) {
        handler.post { f() }
    }
}