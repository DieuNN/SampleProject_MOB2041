package com.example.sampleproject_mob2041.database

import android.content.ContentValues
import com.example.sampleproject_mob2041.dao.ICallCard
import com.example.sampleproject_mob2041.model.CallCard

class CallCardDB(db: Database) : ICallCard {
    private val database =db.writableDatabase
    override fun addCallCard(
        ID: Int,
        customerName: String,
        bookName: String,
        genre: String,
        librarianName: String,
        borrowTime: String,
        isReturned: String
    ): Boolean {
        val values = ContentValues()
        values.apply {
            put("CUSTOMER_NAME", customerName)
            put("BOOK_NAME", bookName)
            put("GENRE", genre)
            put("LIBRARIAN_NAME", librarianName)
            put("BORROW_TIME", borrowTime)
            put("IS_RETURNED", isReturned)
        }
        return database.insert(Database.TABLE_CALL_CARD, null, values) > 0
    }

    override fun removeCard(ID: Int): Boolean {
        return database.delete(Database.TABLE_CALL_CARD, "ID = ?", arrayOf(ID.toString())) > 0
    }

    override fun editCard(ID: Int, newCallCardValue: CallCard): Boolean {
        val values = ContentValues()
        values.apply {
            put("CUSTOMER_NAME", newCallCardValue.customerName)
            put("BOOK_NAME", newCallCardValue.bookName)
            put("GENRE", newCallCardValue.genre)
            put("LIBRARIAN_NAME", newCallCardValue.librarianName)
            put("BORROW_TIME", newCallCardValue.borrowTime)
            put("IS_RETURNED", newCallCardValue.isReturned)
        }
        return database.update(Database.TABLE_CALL_CARD, values, "ID = ?", arrayOf(ID.toString())) > 0
    }

    override fun getAllCallCards(): ArrayList<CallCard> {
        val cursor = database.rawQuery("SELECT * FROM ${Database.TABLE_CALL_CARD}", null)
        val result = ArrayList<CallCard>()
        if (cursor.count > 0) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val ID = cursor.getInt(0)
                val customerName = cursor.getString(1)
                val bookName = cursor.getString(2)
                val genre = cursor.getString(3)
                val librarianName = cursor.getString(4)
                val borrowTime = cursor.getString(5)
                val isReturned = cursor.getString(6)

                val callCard = CallCard(ID, customerName, bookName, genre, librarianName, borrowTime, isReturned)

                result.add(callCard)

                cursor.moveToNext()
            }
        }
        cursor.close()
        return result
    }
}