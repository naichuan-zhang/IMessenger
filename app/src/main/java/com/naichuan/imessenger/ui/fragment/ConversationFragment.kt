package com.naichuan.imessenger.ui.fragment

import android.view.View
import com.naichuan.imessenger.R
import kotlinx.android.synthetic.main.toolbar.*

class ConversationFragment: BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_conversation
    }

    override fun initData() {
        super.initData()
        toolbarTitle.text  = "消息"
        addContact.visibility = View.GONE
    }
}