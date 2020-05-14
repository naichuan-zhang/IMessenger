package com.naichuan.imessenger.data.db

import com.naichuan.imessenger.extension.toVarargArray
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class IMDatabase {
    companion object {
        val databaseHelper = DatabaseHelper()
        val instance = IMDatabase()
    }

    fun saveContact(contact: Contact) {
        databaseHelper.use {
            insert(ContactTable.NAME, *contact.map.toVarargArray())
        }
    }

    fun getAllContacts(): List<Contact> {
        return databaseHelper.use {
            select(ContactTable.NAME).parseList(object : MapRowParser<Contact> {
                override fun parseRow(columns: Map<String, Any?>): Contact {
                    return Contact(columns.toMutableMap())
                }
            })
        }
    }

    fun deleteAllContacts() {
        databaseHelper.use {
            delete(ContactTable.NAME, null, null)
        }
    }
}