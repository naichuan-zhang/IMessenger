package com.naichuan.imessenger.contract

interface RegisterContract {

    interface Presenter: BasePresenter {
        fun register(username: String, password: String, confirmPassword: String)
    }

    interface View {
        fun onUsernameError()
        fun onPasswordError()
        fun onConfirmPasswordError()
        fun onStartRegister()
        fun onRegisterSuccess()
        fun onRegisterFailed()
    }
}