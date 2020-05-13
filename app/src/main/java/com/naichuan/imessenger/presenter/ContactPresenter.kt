package com.naichuan.imessenger.presenter

import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import com.naichuan.imessenger.contract.ContactContract
import com.naichuan.imessenger.data.ContactListItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ContactPresenter(val view: ContactContract.View): ContactContract.Presenter {

    val contactListItems = mutableListOf<ContactListItem>()

    private var firstLetterGlobal: Char? = null

    override fun loadContacts() {
        doAsync {
            clearContacts()
            try {
                val usernames = EMClient.getInstance()
                    .contactManager().allContactsFromServer

                usernames.sortBy {
                    it[0]
                }

                usernames.forEach {
                    val ifShow = checkIfShow(it)
                    val contactListItem = ContactListItem(it, it[0].toUpperCase(), ifShow)
                    contactListItems.add(contactListItem)
                }

                uiThread {
                    view.onLoadContactSuccess()
                }
            } catch (e: HyphenateException) {
                uiThread {
                    view.onLoadContactFailed()
                }
            }
        }
    }

    override fun clearContacts() {
        contactListItems.clear()
    }

    private fun checkIfShow(username: String): Boolean {
        val firstLetter = username[0]
        return if (firstLetter != firstLetterGlobal) {
            firstLetterGlobal = firstLetter
            true
        } else {
            false
        }
    }
}