package com.naichuan.imessenger.contract

interface SplashContract {

    interface Presenter: BasePresenter {
        fun checkLogin()
    }

    interface View {
        fun onNotLoggedIn()
        fun onLoggedIn()
    }
}