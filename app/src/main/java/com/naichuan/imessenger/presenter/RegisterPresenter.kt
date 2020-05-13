package com.naichuan.imessenger.presenter

import android.util.Log
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import com.naichuan.imessenger.contract.RegisterContract
import com.naichuan.imessenger.extension.isValidPassword
import com.naichuan.imessenger.extension.isValidUsername
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

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
        registerBmob(username, password)
    }

    private fun registerBmob(username: String, password: String) {
        val bmobUser = BmobUser()
        bmobUser.username = username
        bmobUser.setPassword(password)
        bmobUser.signUp(object: SaveListener<BmobUser>() {
            override fun done(p0: BmobUser?, p1: BmobException?) {
                Log.i("RegisterPresenter: ", p1?.message ?: "nothing ...")
                if (p1 == null) {
                    view.onRegisterSuccess()
                    // register to 环信
                    // 注册失败会抛出HyphenateException
                    registerEaseMob(username, password)
                }
                else {
                    if (p1.errorCode == 202)
                        view.onUserAlreadyExists()
                    else
                        view.onRegisterFailed()
                }
            }
        })
    }

    private fun registerEaseMob(username: String, password: String) {
        doAsync {
            try {
                EMClient.getInstance().createAccount(username, password)
                uiThread {
                    view.onRegisterSuccess()
                }
            } catch (e: HyphenateException) {
                uiThread {
                    view.onRegisterFailed()
                }
            }
        }
    }
}