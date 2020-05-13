package com.naichuan.imessenger.ui.activity

import android.os.Handler
import com.naichuan.imessenger.R
import com.naichuan.imessenger.base.BaseActivity
import com.naichuan.imessenger.contract.SplashContract
import com.naichuan.imessenger.presenter.SplashPresenter
import org.jetbrains.anko.startActivity

class SplashActivity: BaseActivity(), SplashContract.View {

    companion object {
        const val DELAY_MILLIS = 2000L
    }

    val handler by lazy { Handler() }
    val presenter by lazy { SplashPresenter(this) }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
        presenter.checkLogin()
    }

    override fun onNotLoggedIn() {
        handler.postDelayed({
            startActivity<LoginActivity>()
            finish()
        }, DELAY_MILLIS)
    }

    override fun onLoggedIn() {
        startActivity<MainActivity>()
        finish()
    }
}