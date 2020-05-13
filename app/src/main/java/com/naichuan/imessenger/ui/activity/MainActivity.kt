package com.naichuan.imessenger.ui.activity

import com.naichuan.imessenger.R
import com.naichuan.imessenger.util.FragmentUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
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
    }
}
