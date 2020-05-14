package com.naichuan.imessenger.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyphenate.chat.EMMessage
import com.hyphenate.util.DateUtils
import com.naichuan.imessenger.widget.ReceiveMessageItemView
import com.naichuan.imessenger.widget.SendMessageItemView

class MessageListAdapter(val context: Context, private val messages: List<EMMessage>):
                    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_TYPE_SEND = 0
        const val ITEM_TYPE_RECEIVE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_SEND) SendMessageViewHolder(SendMessageItemView(context, null))
            else ReceiveMessageViewHolder(ReceiveMessageItemView(context, null))
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].direct() == EMMessage.Direct.SEND)
            ITEM_TYPE_SEND else ITEM_TYPE_RECEIVE
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    private fun ifShowTimestamp(position: Int): Boolean {
        var showTimestamp = true
        if (position > 0) {
            showTimestamp = !DateUtils.isCloseEnough(messages[position].msgTime, messages[position - 1].msgTime)
        }
        return showTimestamp
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var showTimestamp = ifShowTimestamp(position)
        if (getItemViewType(position) == ITEM_TYPE_SEND) {
            val sendMessageItemView = holder.itemView as SendMessageItemView
            sendMessageItemView.bindView(messages[position], showTimestamp)
        } else {
            val receiveMessageItemView = holder.itemView as ReceiveMessageItemView
            receiveMessageItemView.bindView(messages[position], showTimestamp)
        }
    }

    class SendMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class ReceiveMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}