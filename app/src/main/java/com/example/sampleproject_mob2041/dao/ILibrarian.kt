package com.example.sampleproject_mob2041.dao

import com.example.sampleproject_mob2041.model.Librarian

interface ILibrarian {
    fun addLibrarian(loginName:String, displayName:String, password:String)

    fun editLibrarian(loginName:String, newLibrarianValue:Librarian)

    fun removeLibrarian(loginName: String)

    fun getAllLibrarians():ArrayList<Librarian>
}