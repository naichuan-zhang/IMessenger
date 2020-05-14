package com.naichuan.imessenger.data.db

data class Contact(
    val map: MutableMap<String, Any?>
) {
    val _id by map
    val name by map
}