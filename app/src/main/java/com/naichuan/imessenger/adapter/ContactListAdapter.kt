package com.naichuan.imessenger.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyphenate.chat.EMClient
import com.naichuan.imessenger.R
import com.naichuan.imessenger.data.ContactListItem
import com.naichuan.imessenger.ui.activity.ChatActivity
import com.naichuan.imessenger.widget.ContactListItemView
import kotlinx.coroutines.NonCancellable.cancel
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ContactListAdapter(
    private val context: Context,
    private val contactListItems: MutableList<ContactListItem>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactListItemViewHolder(ContactListItemView(context))
    }

    override fun getItemCount(): Int {
        return contactListItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val contactListItemView = holder.itemView as ContactListItemView
        contactListItemView.bindView(contactListItems[position])
        val username = contactListItems[position].username
        contactListItemView.setOnClickListener {
            context.startActivity<ChatActivity>("username" to username)
        }
        contactListItemView.setOnLongClickListener {
            AlertDialog.Builder(context)
                .setTitle("删除好友")
                .setMessage("确定要删除${username}吗？")
                .setPositiveButton("删除") { _: DialogInterface, _: Int ->
                    deleteContact(username)
                }
                .setNegativeButton("取消", null)
                .show()
            true
        }
    }

    private fun deleteContact(username: String) {
        EMClient.getInstance().contactManager().aysncDeleteContact(username, object : EMCallBackAdapter() {
            override fun onSuccess() {
                super.onSuccess()
                context.runOnUiThread {
                    toast("删除成功！")
                }
            }

            override fun onError(p0: Int, p1: String?) {
                super.onError(p0, p1)
                context.runOnUiThread {
                    toast("删除失败！")
                }
            }
        })
    }

    class ContactListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}