package com.naichuan.imessenger.contract

interface ContactContract {

    interface Presenter: BasePresenter {
        fun loadContacts()
        fun clearContacts()
    }

    interface View {
        fun onLoadContactSuccess()
        fun onLoadContactFailed()
    }
}