package com.example.sampleproject_mob2041.dao

import com.example.sampleproject_mob2041.model.Customer

interface ICustomer {
    fun addCustomer(name:String, phoneNumber:String, address:String):Boolean

    fun editCustomer(name:String, newCustomerValue:Customer):Boolean

    fun removeCustomer(name:String):Boolean

    fun getAllCustomer():ArrayList<Customer>
}