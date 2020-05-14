package com.naichuan.imessenger.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.naichuan.imessenger.R
import com.naichuan.imessenger.adapter.EMMessageListenerAdapter
import com.naichuan.imessenger.adapter.MessageListAdapter
import com.naichuan.imessenger.contract.ChatContract
import com.naichuan.imessenger.presenter.ChatPresenter
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.toast

class ChatActivity: BaseActivity(), ChatContract.View {

    val presenter by lazy { ChatPresenter(this) }

    lateinit var username: String

    override fun getLayoutId(): Int {
        return R.layout.activity_chat
    }

    private val messageListener = object : EMMessageListenerAdapter() {
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            presenter.addMessage(username, p0)
            runOnUiThread {
                recyclerView.adapter?.notifyDataSetChanged()
                scrollToBottom()
            }
        }
    }

    override fun initData() {
        super.initData()
        username = intent.getStringExtra("username")

        toolbarTitle.text = "与${username}聊天中"

        backButton.visibility = View.VISIBLE
        backButton.setOnClickListener {
            finish()
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                sendButton.isEnabled = !s.isNullOrEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        editText.setOnEditorActionListener { v, actionId, event ->
            send()
            true
        }

        sendButton.setOnClickListener {
            send()
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = MessageListAdapter(context, presenter.messages)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    // scroll up to load more messages
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        var linearLayoutManager = layoutManager as LinearLayoutManager
                        if (linearLayoutManager.findFirstVisibleItemPosition() == 0) {
                            presenter.loadMoreMessages(username)
                        }
                    }
                }
            })
        }

        EMClient.getInstance().chatManager().addMessageListener(messageListener)
        presenter.loadMessages(username)
    }

    private fun send() {
        hideSoftKeyboard()
        presenter.sendMessage(username, editText.text.trim().toString())
    }

    override fun onStartSendMessage() {
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onSendMessageSuccess() {
        recyclerView.adapter?.notifyDataSetChanged()
        toast("消息发送成功")
        editText.text.clear()
        scrollToBottom()
    }

    private fun scrollToBottom() {
        recyclerView.scrollToPosition(presenter.messages.size - 1)
    }

    override fun onSendMessageFailed() {
        toast("消息发送失败！")
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onMessageLoaded() {
        recyclerView.adapter?.notifyDataSetChanged()
        scrollToBottom()
    }

    override fun onMoreMessagesLoaded(size: Int) {
        recyclerView.adapter?.notifyDataSetChanged()
        recyclerView.scrollToPosition(size)
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messageListener)
    }
}