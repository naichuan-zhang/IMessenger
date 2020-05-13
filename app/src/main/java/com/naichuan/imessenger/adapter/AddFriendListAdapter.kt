package com.naichuan.imessenger.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naichuan.imessenger.widget.AddFriendListItemView

class AddFriendListAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AddFriendListItemViewHolder(AddFriendListItemView(context, null))
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    class AddFriendListItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}