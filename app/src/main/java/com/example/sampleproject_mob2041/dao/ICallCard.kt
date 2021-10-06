package com.example.sampleproject_mob2041.dao

import com.example.sampleproject_mob2041.model.CallCard

interface ICallCard {
    fun addCallCard(
        ID: Int?,
        customerName: String,
        bookName: String,
        genre: String,
        librarianName: String,
        borrowTime: String,
        isReturned: String,
        price:Double
    ): Boolean

    fun removeCard(ID: Int):Boolean

    fun editCard(ID: Int, newCallCardValue:CallCard):Boolean

    fun getAllCallCards():ArrayList<CallCard>
}