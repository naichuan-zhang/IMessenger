package com.naichuan.imessenger.contract

import com.hyphenate.chat.EMMessage

interface ChatContract {
    interface Presenter: BasePresenter {
        fun sendMessage(to: String, message: String)
        fun addMessage(username: String, p0: MutableList<EMMessage>?)
        fun loadMessages(username: String)
        fun loadMoreMessages(username: String?)
    }

    interface View {
        fun onStartSendMessage()
        fun onSendMessageSuccess()
        fun onSendMessageFailed()
        fun onMessageLoaded()
        fun onMoreMessagesLoaded(size: Int)
    }
}