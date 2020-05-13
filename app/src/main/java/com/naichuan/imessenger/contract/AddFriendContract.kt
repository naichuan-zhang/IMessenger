package com.naichuan.imessenger.contract

interface AddFriendContract {

    interface Presenter: BasePresenter {
        fun search(keyword: String)
    }

    interface View {
        fun onSearchSuccess()
        fun onSearchFailed()
    }
}