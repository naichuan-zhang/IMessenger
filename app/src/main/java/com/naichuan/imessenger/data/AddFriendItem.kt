package com.naichuan.imessenger.data

data class AddFriendItem(
    var username: String,
    var date: String,
    var hasAdded: Boolean = false
) {
}