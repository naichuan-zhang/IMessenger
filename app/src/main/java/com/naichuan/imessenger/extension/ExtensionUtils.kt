package com.naichuan.imessenger.extension

fun String.isValidUsername(): Boolean
        = this.matches(Regex("^[a-zA-Z]\\w{2,19}$"))

fun String.isValidPassword(): Boolean
        = this.matches(Regex("^[a-zA-Z0-9]{3,20}$"))


fun <K, V> MutableMap<K, V>.toVarargArray(): Array<Pair<K, V>> =
    map {
        Pair(it.key, it.value)
    }.toTypedArray()
