package com.naichuan.imessenger.ui.fragment

import android.graphics.Color
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyphenate.EMContactListener
import com.hyphenate.chat.EMClient
import com.naichuan.imessenger.R
import com.naichuan.imessenger.adapter.ContactListAdapter
import com.naichuan.imessenger.adapter.EMContactListenerAdapter
import com.naichuan.imessenger.contract.ContactContract
import com.naichuan.imessenger.presenter.ContactPresenter
import kotlinx.android.synthetic.main.fragment_contact.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.support.v4.toast

class ContactFragment: BaseFragment(), ContactContract.View {

    val presenter by lazy { ContactPresenter(this) }

    override fun getLayoutId(): Int {
        return R.layout.fragment_contact
    }

    override fun initData() {
        super.initData()
        toolbar.title = "联系人"

        swipeRefreshLayout.apply {
            setColorSchemeColors(Color.GREEN, Color.RED, Color.YELLOW)
            isRefreshing = true
            setOnRefreshListener {
                presenter.loadContacts()
            }
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ContactListAdapter(context, presenter.contactListItems)
        }

        EMClient.getInstance().contactManager().setContactListener(object : EMContactListenerAdapter() {
            override fun onContactDeleted(p0: String?) {
                super.onContactDeleted(p0)
                presenter.loadContacts()
            }
        })

        presenter.loadContacts()
    }

    override fun onLoadContactSuccess() {
        swipeRefreshLayout.isRefreshing = false
        recyclerView.adapter?.notifyDataSetChanged()
        toast("加载联系人数据成功！")
    }

    override fun onLoadContactFailed() {
        swipeRefreshLayout.isRefreshing = false
        toast("加载联系人数据失败！")
    }
}