package com.example.sampleproject_mob2041.dao

import com.example.sampleproject_mob2041.model.Librarian

interface ILibrarian {
    fun addLibrarian(loginName:String, displayName:String, password:String):Boolean

    fun editLibrarian(loginName:String, newLibrarianValue:Librarian):Boolean

    fun removeLibrarian(loginName: String):Boolean

    fun isLibrarianExist(userName:String, password: String):Boolean

    fun getAllLibrarians():ArrayList<Librarian>


}