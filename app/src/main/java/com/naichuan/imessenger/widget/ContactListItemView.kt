package com.naichuan.imessenger.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.naichuan.imessenger.R
import com.naichuan.imessenger.data.ContactListItem
import kotlinx.android.synthetic.main.view_contact_item.view.*

class ContactListItemView(context: Context?) :
                    RelativeLayout(context) {

    init {
        View.inflate(context, R.layout.view_contact_item, this)
    }

    fun bindView(contactListItem: ContactListItem) {
        if (contactListItem.showFirstLetter) {
            firstLetter.text = contactListItem.firstLetter.toString()
            usernameTextView.text = contactListItem.username
        } else {
            firstLetter.visibility = View.INVISIBLE
            usernameTextView.text = contactListItem.username
        }
    }
}