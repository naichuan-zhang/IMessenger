package com.naichuan.imessenger.base

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {

    private val progressDialog by lazy {
        ProgressDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initData()
    }

    protected open fun initData() {
        // init some common config
    }

    abstract fun getLayoutId(): Int

    fun showProgressDialog(msg: String) {
        progressDialog.setMessage(msg)
        progressDialog.show()
    }

    fun dismissProgressDialog() {
        progressDialog.dismiss()
    }
}