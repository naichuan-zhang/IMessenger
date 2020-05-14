package com.naichuan.imessenger.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hyphenate.chat.EMClient
import com.naichuan.imessenger.R
import com.naichuan.imessenger.adapter.EMCallBackAdapter
import com.naichuan.imessenger.data.AddFriendItem
import kotlinx.android.synthetic.main.view_add_friend_item.view.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast

class AddFriendListItemView(context: Context, attrs: AttributeSet?):
                        RelativeLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.view_add_friend_item, this)
    }

    fun bindView(addFriendItem: AddFriendItem) {
        username.text = addFriendItem.username
        date.text = addFriendItem.date
        if (addFriendItem.hasAdded) {
            addFriendButton.text = "已添加"
            addFriendButton.isEnabled = false
        } else {
            addFriendButton.text = "添加"
            addFriendButton.isEnabled = true
        }

        addFriendButton.setOnClickListener {
            addFriend(addFriendItem.username)
        }
    }

    private fun addFriend(username: String) {
        EMClient.getInstance().contactManager().aysncAddContact(username, null,
            object : EMCallBackAdapter() {
                override fun onSuccess() {
                    super.onSuccess()
                    context.runOnUiThread {
                        toast("成功发送添加好友请求")
                    }
                }

                override fun onError(p0: Int, p1: String?) {
                    super.onError(p0, p1)
                    context.runOnUiThread {
                        toast("发送添加好友请求失败")
                    }
                }
            })
    }
}