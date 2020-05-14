package com.naichuan.imessenger.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.util.DateUtils
import com.naichuan.imessenger.R
import kotlinx.android.synthetic.main.view_receive_message_item.view.*
import java.util.*

class ReceiveMessageItemView(context: Context?, attrs: AttributeSet?) :
    RelativeLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.view_receive_message_item, this)
    }

    fun bindView(emMessage: EMMessage, showTimestamp: Boolean) {
        updateTimestamp(emMessage, showTimestamp)
        updateMessageText(emMessage)
    }

    private fun updateMessageText(emMessage: EMMessage) {
        if (emMessage.type == EMMessage.Type.TXT) {
            receiveMessageTextView.text = (emMessage.body as EMTextMessageBody).message
        } else {
            receiveMessageTextView.text = "非文本消息"
        }
    }

    private fun updateTimestamp(
        emMessage: EMMessage,
        showTimestamp: Boolean
    ) {
        if (showTimestamp) {
            timestamp.text = DateUtils.getTimestampString(Date(emMessage.msgTime))
        } else {
            timestamp.visibility = View.GONE
        }
    }
}