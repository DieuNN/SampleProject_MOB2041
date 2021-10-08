package com.example.sampleproject_mob2041.dao

interface IAnalytics {
    fun getBookBorrowedTime(bookName:String):Int

    fun getTotalIncomeOfBook(bookName:String):Double
}