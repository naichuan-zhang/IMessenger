package com.naichuan.imessenger.presenter

import com.hyphenate.chat.EMClient
import com.naichuan.imessenger.adapter.EMCallBackAdapter
import com.naichuan.imessenger.contract.LoginContract
import com.naichuan.imessenger.extension.isValidPassword
import com.naichuan.imessenger.extension.isValidUsername

class LoginPresenter(val view: LoginContract.View): LoginContract.Presenter {
    companion object {
        const val TAG = "LoginPresenter: "
    }

    override fun login(username: String, password: String) {
        if (!username.isValidUsername()) {
            view.onUsernameError()
            return
        }
        if (!username.isValidPassword()) {
            view.onPasswordError()
            return
        }
        view.onStartLogin()
        loginEaseWeb(username, password)
    }

    private fun loginEaseWeb(username: String, password: String) {
        EMClient.getInstance().login(username, password, object : EMCallBackAdapter() {
            override fun onSuccess() {
                super.onSuccess()
                EMClient.getInstance().groupManager().loadAllGroups()
                EMClient.getInstance().chatManager().loadAllConversations()
                runOnMainThread {
                    view.onLoginSuccess()
                }
            }

            override fun onError(p0: Int, p1: String?) {
                super.onError(p0, p1)
                runOnMainThread {
                    view.onLoginFailed()
                }
            }
        })
    }
}