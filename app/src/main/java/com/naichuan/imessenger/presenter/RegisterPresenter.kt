package com.naichuan.imessenger.presenter

import com.naichuan.imessenger.contract.RegisterContract
import com.naichuan.imessenger.extension.isValidPassword
import com.naichuan.imessenger.extension.isValidUsername

class RegisterPresenter(val view: RegisterContract.View): RegisterContract.Presenter {
    override fun register(username: String, password: String, confirmPassword: String) {
        if (!username.isValidUsername()) {
            view.onUsernameError()
            return
        }
        if (!password.isValidPassword()) {
            view.onPasswordError()
            return
        }
        if (confirmPassword != password) {
            view.onConfirmPasswordError()
            return
        }
        view.onStartRegister()
        registerEaseWeb()
    }

    private fun registerEaseWeb() {
        
    }
}