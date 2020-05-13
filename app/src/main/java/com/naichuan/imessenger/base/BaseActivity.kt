package com.naichuan.imessenger.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initData()
    }

    protected open fun initData() {
        // init some common config
    }

    abstract fun getLayoutId(): Int
}