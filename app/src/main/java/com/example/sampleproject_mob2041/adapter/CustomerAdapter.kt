package com.example.sampleproject_mob2041.adapter

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.database.CustomerDB
import com.example.sampleproject_mob2041.database.Database
import com.example.sampleproject_mob2041.model.Customer

class CustomerAdapter(private val mContext: Context, var mList: ArrayList<Customer>) :
    RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {
    private lateinit var customerDB: CustomerDB

    companion object {
        const val DELETE_CUSTOMER = 131
        const val EDIT_CUSTOMER = 132
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {
        val customerName: TextView = itemView.findViewById(R.id.customer_item_name)
        val customerPhoneNumber: TextView = itemView.findViewById(R.id.customer_item_phone_number)
        val customerAddress: TextView = itemView.findViewById(R.id.customer_item_address)
        val customerItem: RelativeLayout = itemView.findViewById(R.id.customer_item)

        init {
            customerItem.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu!!.add(adapterPosition, EDIT_CUSTOMER, 0, "Sửa")
            menu.add(adapterPosition, DELETE_CUSTOMER, 1, "Xóa")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.customer_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.customerName.text =
            "${mContext.resources.getString(R.string.customer_name)}: ${mList[position].name}"
        holder.customerPhoneNumber.text =
            "${mContext.resources.getString(R.string.phone_number)}: ${mList[position].phoneNumber}"
        holder.customerAddress.text =
            "${mContext.resources.getString(R.string.address)}: ${mList[position].address}"
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun deleteItem(position: Int) {
        customerDB = CustomerDB(Database(mContext))

        if (customerDB.removeCustomer(mList[position].name)) {
            Toast.makeText(
                mContext,
                mContext.getString(R.string.remove_successful),
                Toast.LENGTH_SHORT
            ).show()
            mList.clear()
            mList = customerDB.getAllCustomer()
            this.notifyItemRemoved(position)
            this.notifyItemRangeChanged(position, mList.size)
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.remove_failed), Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun addItem(customer: Customer) {
        customerDB = CustomerDB(Database(mContext))
        if (customerDB.addCustomer(customer.name, customer.phoneNumber, customer.address)) {
            Toast.makeText(mContext, mContext.getText(R.string.add_successful), Toast.LENGTH_SHORT)
                .show()
            mList.clear()
            mList = customerDB.getAllCustomer()
            this.notifyItemInserted(mList.size)
        } else {
            Toast.makeText(mContext, mContext.getText(R.string.add_failed), Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun editItem(position: Int, newCustomerValue:Customer) {
        customerDB = CustomerDB(Database(mContext))

        if (customerDB.editCustomer(mList[position].name, newCustomerValue)) {
            Toast.makeText(mContext, mContext.getString(R.string.edit_successfull), Toast.LENGTH_SHORT).show()
            mList.clear()
            mList = customerDB.getAllCustomer()
            this.notifyItemChanged(position)
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.remove_failed), Toast.LENGTH_SHORT).show()
        }
    }
}