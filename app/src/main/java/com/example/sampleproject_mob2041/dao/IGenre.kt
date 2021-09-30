package com.example.sampleproject_mob2041.dao

import com.example.sampleproject_mob2041.model.Genre

interface IGenre {
    fun addGenre(name:String):Boolean

    fun editGenre(name:String, newGenreValue:Genre):Boolean

    fun removeGenre(name:String):Boolean

    fun getAllGenres():ArrayList<Genre>

}