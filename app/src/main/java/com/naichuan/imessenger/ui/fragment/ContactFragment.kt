package com.naichuan.imessenger.ui.fragment

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyphenate.EMContactListener
import com.hyphenate.chat.EMClient
import com.naichuan.imessenger.R
import com.naichuan.imessenger.adapter.ContactListAdapter
import com.naichuan.imessenger.adapter.EMContactListenerAdapter
import com.naichuan.imessenger.contract.ContactContract
import com.naichuan.imessenger.presenter.ContactPresenter
import com.naichuan.imessenger.ui.activity.AddFriendActivity
import com.naichuan.imessenger.widget.SlideBar
import kotlinx.android.synthetic.main.fragment_contact.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class ContactFragment: BaseFragment(), ContactContract.View {

    val presenter by lazy { ContactPresenter(this) }

    override fun getLayoutId(): Int {
        return R.layout.fragment_contact
    }

    override fun initData() {
        super.initData()
        toolbarTitle.text  = "联系人"

        addContact.visibility = View.VISIBLE

        addContact.setOnClickListener {
            startActivity<AddFriendActivity>()
        }

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

        slideBar.onSectionChangeListener = object : SlideBar.OnSectionChangeListener {
            override fun onSectionChanged(firstLetter: String) {
                sectionTextView.visibility = View.VISIBLE
                sectionTextView.text = firstLetter
                recyclerView.smoothScrollToPosition(getPosition(firstLetter))
            }

            override fun onSlideFinished() {
                sectionTextView.visibility = View.GONE
            }
        }

        presenter.loadContacts()
    }

    private fun getPosition(firstLetter: String): Int =
        presenter.contactListItems.binarySearch { contactListItem ->
            contactListItem.firstLetter.minus(firstLetter[0])
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