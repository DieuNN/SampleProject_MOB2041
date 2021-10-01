package com.example.sampleproject_mob2041.database

import android.content.ContentValues
import com.example.sampleproject_mob2041.dao.ICustomer
import com.example.sampleproject_mob2041.model.Customer

class CustomerDB(db:Database) :ICustomer {
    private val database = db.writableDatabase
    override fun addCustomer(name: String, phoneNumber: String, address: String): Boolean {
        val values = ContentValues()
        values.put("NAME", name)
        values.put("PHONE_NUMBER", phoneNumber)
        values.put("ADDRESS", address)
        return database.insert(Database.TABLE_CUSTOMER, null, values) > 0
    }

    override fun editCustomer(name: String, newCustomerValue: Customer): Boolean {
        val values = ContentValues()
        values.put("NAME", newCustomerValue.name)
        values.put("PHONE_NUMBER", newCustomerValue.phoneNumber)
        values.put("ADDRESS", newCustomerValue.address)

        return database.update(Database.TABLE_CUSTOMER, values, "NAME = ?", arrayOf(name)) > 0
    }

    override fun removeCustomer(name: String): Boolean {
        return database.delete(Database.TABLE_CUSTOMER, "NAME = ?", arrayOf(name)) > 0
    }

    override fun getAllCustomer(): ArrayList<Customer> {
        val cursor = database.rawQuery("SELECT * FROM ${Database.TABLE_CUSTOMER}", null)
        val result = ArrayList<Customer>()
        if (cursor.count > 0) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val name = cursor.getString(0)
                val phoneNumber = cursor.getString(1)
                val address = cursor.getString(2)

                val customer = Customer(name, phoneNumber, address)

                result.add(customer)

                cursor.moveToNext()
            }
        }
        cursor.close()
        return result
    }
}