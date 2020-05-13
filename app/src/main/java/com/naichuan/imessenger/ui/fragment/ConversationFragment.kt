package com.naichuan.imessenger.ui.fragment

import com.naichuan.imessenger.R
import kotlinx.android.synthetic.main.toolbar.*

class ConversationFragment: BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_conversation
    }

    override fun initData() {
        super.initData()
        toolbar.title = "消息"
    }
}