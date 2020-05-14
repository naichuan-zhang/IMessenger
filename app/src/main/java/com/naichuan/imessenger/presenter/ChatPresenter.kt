package com.naichuan.imessenger.presenter

import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.naichuan.imessenger.adapter.EMCallBackAdapter
import com.naichuan.imessenger.contract.ChatContract
import org.jetbrains.anko.doAsync

class ChatPresenter(val view: ChatContract.View): ChatContract.Presenter {

    var messages = mutableListOf<EMMessage>()

    companion object {
        const val PAGE_SIZE = 10
    }

    override fun sendMessage(to: String, message: String) {
        val emMessage = EMMessage.createTxtSendMessage(message, to)
        messages.add(emMessage)
        view.onStartSendMessage()
        EMClient.getInstance().chatManager().sendMessage(emMessage)
        emMessage.setMessageStatusCallback(object : EMCallBackAdapter() {
            override fun onSuccess() {
                super.onSuccess()
                runOnMainThread {
                    view.onSendMessageSuccess()
                }
            }

            override fun onError(p0: Int, p1: String?) {
                super.onError(p0, p1)
                runOnMainThread {
                    view.onSendMessageFailed()
                }
            }
        })
    }

    override fun addMessage(username: String, p0: MutableList<EMMessage>?) {
        p0?.let { messages.addAll(it) }
        val conversations = EMClient.getInstance().chatManager().getConversation(username)
        conversations.markAllMessagesAsRead()
    }

    /**
     * init message records
     */
    override fun loadMessages(username: String) {
        doAsync {
            val conversation = EMClient.getInstance().chatManager().getConversation(username)
            conversation.markAllMessagesAsRead()
            messages.addAll(conversation.allMessages)
            runOnMainThread {
                view.onMessageLoaded()
            }
        }
    }

    override fun loadMoreMessages(username: String?) {
        doAsync {
            val conversation = EMClient.getInstance().chatManager().getConversation(username)
            val startMsgId = messages[0].msgId
            val moreMessages = conversation.loadMoreMsgFromDB(startMsgId, PAGE_SIZE)
            messages.addAll(0, moreMessages)
            runOnMainThread {
                view.onMoreMessagesLoaded(moreMessages.size)
            }
        }
    }
}