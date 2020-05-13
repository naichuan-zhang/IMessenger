package com.naichuan.imessenger.util

import androidx.fragment.app.Fragment
import com.naichuan.imessenger.R
import com.naichuan.imessenger.ui.fragment.ContactFragment
import com.naichuan.imessenger.ui.fragment.ConversationFragment
import com.naichuan.imessenger.ui.fragment.DynamicFragment

class FragmentUtil private constructor() {

    private val conversationFragment by lazy { ConversationFragment() }
    private val contactFragment by lazy { ContactFragment() }
    private val dynamicFragment by lazy { DynamicFragment() }

    companion object {
        val instance by lazy { FragmentUtil() }
    }

    fun getFragment(tabId: Int): Fragment? {
        return when (tabId) {
            R.id.tabMessage -> conversationFragment
            R.id.tabContact -> contactFragment
            R.id.tabActivity -> dynamicFragment
            else -> null
        }
    }
}