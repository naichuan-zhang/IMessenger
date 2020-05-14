package com.naichuan.imessenger.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.naichuan.imessenger.R
import com.naichuan.imessenger.adapter.AddFriendListAdapter
import com.naichuan.imessenger.contract.AddFriendContract
import com.naichuan.imessenger.presenter.AddFriendPresenter
import kotlinx.android.synthetic.main.activity_add_friend.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.toast

class AddFriendActivity: BaseActivity(), AddFriendContract.View {

    val presenter by lazy {
        AddFriendPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_add_friend
    }

    override fun initData() {
        super.initData()
        toolbarTitle.text = "添加好友"

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = AddFriendListAdapter(context, presenter.addFriendItems)
        }

        search.setOnClickListener {
            search()
        }

        username.setOnEditorActionListener { v, actionId, event ->
            search()
            true
        }
    }

    private fun search() {
        hideSoftKeyboard()
        showProgressDialog("正在搜索...")
        val keyword = username.text.trim().toString()
        presenter.search(keyword)
    }

    override fun onSearchSuccess() {
        dismissProgressDialog()
        toast("搜索成功")
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onSearchFailed() {
        dismissProgressDialog()
        toast("搜索失败")
    }
}