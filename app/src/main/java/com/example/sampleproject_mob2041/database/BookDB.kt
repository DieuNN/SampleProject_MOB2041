package com.example.sampleproject_mob2041.database

import android.content.ContentValues
import com.example.sampleproject_mob2041.dao.IBook
import com.example.sampleproject_mob2041.model.Book

class BookDB(db:Database) : IBook {
    private val database = db.writableDatabase
    override fun addBook(name: String, genre: String, price: Double): Boolean {
        val values = ContentValues()
        values.apply {
            put("NAME", name)
            put("GENRE", genre)
            put("PRICE", price)
        }
        return database.insert(Database.TABLE_BOOK, null, values) > 0
    }

    override fun editBook(name: String, newBookValue: Book):Boolean {
        val values = ContentValues()
        values.apply {
            put("NAME", newBookValue.name)
            put("GENRE", newBookValue.genre)
            put("PRICE", newBookValue.price)
        }
        return database.update(Database.TABLE_BOOK, values, "NAME = ?", arrayOf(name)) > 0
    }

    override fun removeBook(name: String): Boolean {
        return database.delete(Database.TABLE_BOOK, "NAME = ?", arrayOf(name))  > 0
    }

    override fun getAllBooks(): ArrayList<Book> {
        val cursor = database.rawQuery("SELECT * FROM ${Database.TABLE_BOOK}", null)
        val result = ArrayList<Book>()

        if (cursor.count > 0) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val name = cursor.getString(0)
                val genre = cursor.getString(1)
                val price = cursor.getDouble(2)

                val book = Book(name, genre, price)
                result.add(book)

                cursor.moveToNext()
            }
        }
        cursor.close()
        return result
    }
}