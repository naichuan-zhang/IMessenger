package com.naichuan.imessenger.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.naichuan.imessenger.R

class AddFriendListItemView(context: Context, attrs: AttributeSet?):
                        RelativeLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.view_add_friend_item, this)
    }
}