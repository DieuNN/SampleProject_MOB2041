package com.example.sampleproject_mob2041.database

import com.example.sampleproject_mob2041.dao.IAnalytics

class AnalyticsDB(private val db: Database) : IAnalytics {
    override fun getBookBorrowedTime(bookName: String): Int {
        val database = db.writableDatabase
        val cursor = database.rawQuery(
            "SELECT  COUNT(*) FROM CALL_CARD JOIN BOOK ON CALL_CARD.BOOK_NAME = BOOK.NAME WHERE BOOK_NAME = \"$bookName\" ",
            null
        )
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    override fun getTotalIncomeOfBook(bookName: String): Double {
        val database = db.writableDatabase
        val cursor = database.rawQuery("select  sum(BOOK.PRICE)  from CALL_CARD join BOOK on CALL_CARD.BOOK_NAME = BOOK.NAME WHERE BOOK_NAME = \"$bookName\"  ", null)
        cursor.moveToFirst()
        return cursor.getDouble(0)
    }
}