package com.naichuan.imessenger.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naichuan.imessenger.data.AddFriendItem
import com.naichuan.imessenger.widget.AddFriendListItemView

class AddFriendListAdapter(
    val context: Context,
    val addFriendItems: MutableList<AddFriendItem>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AddFriendListItemViewHolder(AddFriendListItemView(context, null))
    }

    override fun getItemCount(): Int {
        return addFriendItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val addFriendListItemView = holder.itemView as AddFriendListItemView
        addFriendListItemView.bindView(addFriendItems[position])
    }

    class AddFriendListItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}