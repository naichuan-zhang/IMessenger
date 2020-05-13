package com.naichuan.imessenger.ui.fragment

import android.view.View
import com.hyphenate.chat.EMClient
import com.naichuan.imessenger.R
import com.naichuan.imessenger.adapter.EMCallBackAdapter
import com.naichuan.imessenger.ui.activity.LoginActivity
import kotlinx.android.synthetic.main.fragment_dynamic.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.toast

class DynamicFragment: BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_dynamic
    }

    override fun initData() {
        super.initData()
        toolbarTitle.text  = "动态"
        logoutButton.setOnClickListener {
            logout()
        }

        addContact.visibility = View.GONE
    }

    private fun logout() {
        EMClient.getInstance().logout(true, object : EMCallBackAdapter() {
            override fun onSuccess() {
                super.onSuccess()
                context?.runOnUiThread {
                    toast("退出登录成功！")
                }
                startActivity<LoginActivity>()
                activity?.finish()
            }

            override fun onError(p0: Int, p1: String?) {
                super.onError(p0, p1)
                context?.runOnUiThread {
                    toast("退出登陆失败！")
                }
            }
        })
    }
}