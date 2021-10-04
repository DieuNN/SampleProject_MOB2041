package com.example.sampleproject_mob2041.database

import android.content.ContentValues
import com.example.sampleproject_mob2041.dao.IGenre
import com.example.sampleproject_mob2041.model.Genre

class GenreDB(private val db:Database):IGenre {
    private val database = db.writableDatabase
    override fun addGenre(name: String): Boolean {
        val values = ContentValues()
        values.put("NAME", name)
        return database.insert(Database.TABLE_GENRE, null, values) > 0
    }

    override fun editGenre(name: String, newGenreValue: Genre): Boolean {
        val values = ContentValues()
        values.put("NAME", newGenreValue.name)

        return database.update(Database.TABLE_GENRE, values , "NAME = ?", arrayOf(name)) > 0
    }

    override fun removeGenre(name: String): Boolean {
        return database.delete(Database.TABLE_GENRE, "NAME = ? ", arrayOf(name)) > 0
    }

    override fun getAllGenres(): ArrayList<Genre> {
        val cursor = database.rawQuery("SELECT * FROM ${Database.TABLE_GENRE}", null)
        val result = ArrayList<Genre>()
        if (cursor.count > 0) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val name = cursor.getString(0)

                val genre = Genre(name = name)
                result.add(genre)
                cursor.moveToNext()
            }
        }
        cursor.close()
        return result
    }
}