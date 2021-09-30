package com.example.sampleproject_mob2041.dao

import com.example.sampleproject_mob2041.model.Book

interface IBook {
    fun addBook(name:String, genre:String, price:Double):Boolean

    fun editBook(name:String, newBookValue:Book)

    fun removeBook(name:String):Boolean

    fun getAllBooks():ArrayList<Book>
}