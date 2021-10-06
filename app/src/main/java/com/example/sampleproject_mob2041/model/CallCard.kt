package com.example.sampleproject_mob2041.model

data class CallCard(
    val ID: Int?,
    val customerName: String,
    val bookName: String,
    val genre: String,
    val librarianName: String,
    val borrowTime: String,
    val isReturned: String,
    val price:Double
)
