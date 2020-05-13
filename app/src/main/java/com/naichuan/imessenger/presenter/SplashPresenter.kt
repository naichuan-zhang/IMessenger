package com.naichuan.imessenger.presenter

import com.hyphenate.chat.EMClient
import com.naichuan.imessenger.contract.SplashContract

class SplashPresenter(val view: SplashContract.View): SplashContract.Presenter {
    override fun checkLogin() {
        if (isLoggedIn())
            view.onLoggedIn()
        else
            view.onNotLoggedIn()
    }

    private fun isLoggedIn(): Boolean =
        EMClient.getInstance().isConnected && EMClient.getInstance().isLoggedInBefore
}