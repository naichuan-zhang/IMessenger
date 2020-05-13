package com.naichuan.imessenger.ui.activity

import com.naichuan.imessenger.R
import com.naichuan.imessenger.base.BaseActivity
import com.naichuan.imessenger.contract.RegisterContract
import com.naichuan.imessenger.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity: BaseActivity(), RegisterContract.View {

    private val presenter by lazy {
        RegisterPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initData() {
        super.initData()
        registerButton.setOnClickListener { register() }
        confirmPasswordEditText.setOnEditorActionListener { v, actionId, event ->
            register()
            true
        }
    }

    private fun register() {
        hideSoftKeyboard()
        val usernameStr = usernameEditText.text.trim().toString()
        val passwordStr = passwordEditText.text.trim().toString()
        val confirmPasswordStr = confirmPasswordEditText.text.trim().toString()
        presenter.register(usernameStr, passwordStr, confirmPasswordStr)
    }

    override fun onUsernameError() {
        usernameEditText.error = "用户名错误！"
    }

    override fun onPasswordError() {
        passwordEditText.error = "密码错误！"
    }

    override fun onConfirmPasswordError() {
        confirmPasswordEditText.error = "密码不匹配！"
    }

    override fun onStartRegister() {
        showProgressDialog("正在注册...")
    }

    override fun onRegisterSuccess() {
        dismissProgressDialog()
        finish()
    }

    override fun onRegisterFailed() {
        dismissProgressDialog()
        toast("注册失败！")
    }
}