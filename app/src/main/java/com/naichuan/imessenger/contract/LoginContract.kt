package com.naichuan.imessenger.contract

interface LoginContract {

    interface Presenter : BasePresenter {
        fun login(username: String, password: String)
    }

    interface View {
        fun onUsernameError()
        fun onPasswordError()
        fun onStartLogin()
        fun onLoginSuccess()
        fun onLoginFailed()
    }
}