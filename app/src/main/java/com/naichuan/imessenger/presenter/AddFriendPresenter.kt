package com.naichuan.imessenger.presenter

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.hyphenate.chat.EMClient
import com.naichuan.imessenger.contract.AddFriendContract
import com.naichuan.imessenger.data.AddFriendItem
import com.naichuan.imessenger.data.db.Contact
import com.naichuan.imessenger.data.db.IMDatabase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AddFriendPresenter(val view: AddFriendContract.View): AddFriendContract.Presenter {

    var addFriendItems = mutableListOf<AddFriendItem>()

    override fun search(keyword: String) {
        val query = BmobQuery<BmobUser>()
        query.addWhereContains("username", keyword)
            .addWhereNotEqualTo("username", EMClient.getInstance().currentUser)
        query.findObjects(object : FindListener<BmobUser>() {
            override fun done(p0: MutableList<BmobUser>?, p1: BmobException?) {
                if (p1 == null) {
                    val allContacts = IMDatabase.instance.getAllContacts()
                    doAsync {
                        p0?.forEach {
                            val hasAdded: Boolean = checkIfAdded(it, allContacts)
                            val addFriendItem = AddFriendItem(it.username, it.createdAt, hasAdded)
                            addFriendItems.add(addFriendItem)
                        }
                        uiThread {
                            view.onSearchSuccess()
                        }
                    }
                } else {
                    view.onSearchFailed()
                }
            }
        })
    }

    private fun checkIfAdded(user: BmobUser, allContacts: List<Contact>): Boolean {
        for (contact in allContacts) {
            if (contact.name == user.username)
                return true
        }
        return false
    }
}