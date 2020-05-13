package com.naichuan.imessenger.presenter

import com.naichuan.imessenger.contract.LoginContract
import com.naichuan.imessenger.extension.isValidPassword
import com.naichuan.imessenger.extension.isValidUsername

class LoginPresenter(val view: LoginContract.View): LoginContract.Presenter {
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

    }
}