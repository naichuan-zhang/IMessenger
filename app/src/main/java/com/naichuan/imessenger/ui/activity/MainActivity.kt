package com.naichuan.imessenger.ui.activity

import com.hyphenate.EMConnectionListener
import com.hyphenate.EMError
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.naichuan.imessenger.R
import com.naichuan.imessenger.adapter.EMMessageListenerAdapter
import com.naichuan.imessenger.util.FragmentUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity() {

    private val messageListener = object : EMMessageListenerAdapter() {
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            runOnUiThread {
                updateBottomBarUnreadCount()
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        super.initData()
        bottomBar.setOnTabSelectListener { tabId ->
            val fragment = FragmentUtil.instance.getFragment(tabId)
            if (fragment != null) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.contentContainer, fragment)
                transaction.commit()
            }
        }

        EMClient.getInstance().chatManager().addMessageListener(messageListener)
        EMClient.getInstance().addConnectionListener(object : EMConnectionListener {
            override fun onConnected() {

            }

            override fun onDisconnected(p0: Int) {
                if (p0 == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    startActivity<LoginActivity>()
                    finish()
                }
            }
        })

    }

    override fun onResume() {
        super.onResume()
        updateBottomBarUnreadCount()
    }

    private fun updateBottomBarUnreadCount() {
        val tab = bottomBar.getTabWithId(R.id.tabMessage)
        tab.setBadgeCount(EMClient.getInstance().chatManager().unreadMessageCount)
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messageListener)
    }
}
