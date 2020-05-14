package com.naichuan.imessenger.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMMessage
import com.naichuan.imessenger.R
import com.naichuan.imessenger.adapter.ConversationListAdapter
import com.naichuan.imessenger.adapter.EMMessageListenerAdapter
import kotlinx.android.synthetic.main.fragment_conversation.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.runOnUiThread

class ConversationFragment: BaseFragment() {

    private val conversations = mutableListOf<EMConversation>()

    private val messageListener = object : EMMessageListenerAdapter() {
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            super.onMessageReceived(p0)
            loadConversations()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_conversation
    }

    override fun initData() {
        super.initData()
        toolbarTitle.text  = "消息"
        addContact.visibility = View.GONE

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ConversationListAdapter(context, conversations)
        }

        loadConversations()

        EMClient.getInstance().chatManager().addMessageListener(messageListener)
    }

    private fun loadConversations() {
        doAsync {
            conversations.clear()
            val allConversations = EMClient.getInstance().chatManager().allConversations
            conversations.addAll(allConversations.values)
            runOnUiThread {
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadConversations()
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messageListener)
    }
}