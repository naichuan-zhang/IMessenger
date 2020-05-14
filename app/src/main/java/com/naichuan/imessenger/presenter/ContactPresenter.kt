package com.naichuan.imessenger.presenter

import android.util.Log
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import com.naichuan.imessenger.contract.ContactContract
import com.naichuan.imessenger.data.ContactListItem
import com.naichuan.imessenger.data.db.Contact
import com.naichuan.imessenger.data.db.IMDatabase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ContactPresenter(val view: ContactContract.View): ContactContract.Presenter {

    val contactListItems = mutableListOf<ContactListItem>()

    private var firstLetterGlobal: Char? = null

    override fun loadContacts() {
        doAsync {
            clearContacts()
            IMDatabase.instance.deleteAllContacts()

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

                usernames.forEachIndexed { index, s ->
                    val contact = Contact(mutableMapOf("name" to s))
                    IMDatabase.instance.saveContact(contact)
                }


                val allContacts = IMDatabase.instance.getAllContacts()
                Log.i("ContactPresenter: ", allContacts.toString())

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