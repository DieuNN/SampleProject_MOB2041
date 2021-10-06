package com.example.sampleproject_mob2041.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(private val mContext: Context) :
    SQLiteOpenHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "LIB_MANA_DB"
        const val DATABASE_VERSION = 1
        const val TABLE_BOOK = "BOOK"
        const val TABLE_CALL_CARD = "CALL_CARD"
        const val TABLE_CUSTOMER = "CUSTOMER"
        const val TABLE_GENRE = "GENRE"
        const val TABLE_LIBRARIAN = "LIBRARIAN"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Enable foreign key
        db.execSQL("PRAGMA foreign_keys = ON")

        // Create table genre
        db.execSQL("CREATE TABLE $TABLE_GENRE (NAME TEXT PRIMARY KEY)")

        // Create table book, foreign key GENRE references to TABLE_GENRE, on delete cascade (when data in references table deleted, data in child table will be deleted too
        db.execSQL("CREATE TABLE $TABLE_BOOK (NAME TEXT PRIMARY KEY, GENRE TEXT, PRICE REAL, FOREIGN KEY (GENRE) REFERENCES $TABLE_GENRE ON DELETE CASCADE)")

        // Create table customer
        db.execSQL("CREATE TABLE $TABLE_CUSTOMER (NAME TEXT PRIMARY KEY, PHONE_NUMBER TEXT, ADDRESS TEXT)")

        // Create table librarian
        db.execSQL("CREATE TABLE $TABLE_LIBRARIAN (LOGIN_NAME TEXT PRIMARY KEY, DISPLAY_NAME TEXT, PASSWORD TEXT)")

        // Create table call card (Phiếu mượn), foreign key BOOK_NAME references TABLE_BOOK, CUSTOMER_NAME references TABLE CUSTOMER
        // LIBRARIAN_NAME references TABLE_LIBRARIAN
        db.execSQL("CREATE TABLE $TABLE_CALL_CARD (ID INTEGER PRIMARY KEY AUTOINCREMENT, CUSTOMER_NAME TEXT, BOOK_NAME TEXT, GENRE TEXT, LIBRARIAN_NAME TEXT, BORROW_TIME TEXT, IS_RETURNED NOT NULL, PRICE REAL, FOREIGN KEY (CUSTOMER_NAME) REFERENCES $TABLE_CUSTOMER ON DELETE CASCADE, FOREIGN KEY (BOOK_NAME) REFERENCES $TABLE_BOOK ON DELETE CASCADE, FOREIGN KEY (GENRE) REFERENCES $TABLE_GENRE ON DELETE CASCADE, FOREIGN KEY (LIBRARIAN_NAME) REFERENCES $TABLE_LIBRARIAN ON DELETE CASCADE)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion > newVersion) {
            db.execSQL("DROP TABLE $TABLE_BOOK")
            db.execSQL("DROP TABLE $TABLE_GENRE")
            db.execSQL("DROP TABLE $TABLE_CUSTOMER")
            db.execSQL("DROP TABLE $TABLE_LIBRARIAN")
            db.execSQL("DROP TABLE $TABLE_CALL_CARD")
        }
    }


}