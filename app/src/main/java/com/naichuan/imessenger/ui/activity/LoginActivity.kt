package com.naichuan.imessenger.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.naichuan.imessenger.R
import com.naichuan.imessenger.contract.LoginContract
import com.naichuan.imessenger.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity: BaseActivity(), LoginContract.View {

    private val presenter by lazy {
        LoginPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initData() {
        super.initData()
        loginButton.setOnClickListener { login() }
        passwordEditText.setOnEditorActionListener { v, actionId, event ->
            login()
            true
        }
        newUser.setOnClickListener {
            startActivity<RegisterActivity>()
        }
    }

    private fun login() {
        hideSoftKeyboard()
        if (hasWriteExternalStoragePermission()) {
            val usernameStr = usernameEditText.text.trim().toString()
            val passwordStr = passwordEditText.text.trim().toString()
            presenter.login(usernameStr, passwordStr)
        } else {
            applyWriteExternalStoragePermission()
        }
    }

    private fun hasWriteExternalStoragePermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
    }

    private fun applyWriteExternalStoragePermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            login()
        } else {
            toast("This permission has been denied")
        }
    }

    override fun onUsernameError() {
        usernameEditText.error = "用户名错误！"
    }

    override fun onPasswordError() {
        passwordEditText.error = "密码错误！"
    }

    override fun onStartLogin() {
        showProgressDialog("正在登录...")
    }

    override fun onLoginSuccess() {
        dismissProgressDialog()
        startActivity<MainActivity>()
        finish()
    }

    override fun onLoginFailed() {
        dismissProgressDialog()
        toast("登录失败！")
    }
}