package com.example.sampleproject_mob2041.database

import android.content.ContentValues
import com.example.sampleproject_mob2041.dao.ILibrarian
import com.example.sampleproject_mob2041.model.Librarian

class LibrarianDB(db: Database) : ILibrarian {
    private val database = db.writableDatabase
    override fun addLibrarian(loginName: String, displayName: String, password: String): Boolean {
        val values = ContentValues()
        values.apply {
            put("LOGIN_NAME", loginName)
            put("DISPLAY_NAME", displayName)
            put("PASSWORD", password)
        }
        return database.insert(Database.TABLE_LIBRARIAN, null, values) > 0
    }

    override fun editLibrarian(loginName: String, newLibrarianValue: Librarian): Boolean {
        val values = ContentValues()
        values.apply {
            put("LOGIN_NAME", newLibrarianValue.loginName)
            put("DISPLAY_NAME", newLibrarianValue.displayName)
            put("PASSWORD", newLibrarianValue.password)
        }
        return database.update(
            Database.TABLE_LIBRARIAN,
            values,
            "LOGIN_NAME = ?",
            arrayOf(loginName)
        ) > 0
    }

    override fun removeLibrarian(loginName: String): Boolean {
        return database.delete(Database.TABLE_LIBRARIAN, "LOGIN_NAME = ?", arrayOf(loginName)) > 0
    }

    override fun getAllLibrarians(): ArrayList<Librarian> {
        val cursor = database.rawQuery("SELECT * FROM ${Database.TABLE_LIBRARIAN}", null)
        val result = ArrayList<Librarian>()

        if (cursor.count > 0) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val loginName = cursor.getString(0)
                val displayName = cursor.getString(1)
                val password = cursor.getString(2)

                val librarian = Librarian(loginName, displayName, password)
                result.add(librarian)

                cursor.moveToNext()
            }
        }
        cursor.close()
        return result
    }
}