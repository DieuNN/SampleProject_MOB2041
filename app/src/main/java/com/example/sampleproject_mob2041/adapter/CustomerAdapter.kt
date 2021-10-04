package com.example.sampleproject_mob2041.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.model.Customer

class CustomerAdapter(private val mContext: Context, var mList:ArrayList<Customer>):RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerName: TextView = itemView.findViewById(R.id.customer_item_name)
        val customerPhoneNumber:TextView = itemView.findViewById(R.id.customer_item_phone_number)
        val customerAddress:TextView = itemView.findViewById(R.id.customer_item_address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.customer_item, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.customerName.text = "${mContext.resources.getString(R.string.customer_name)}: ${mList[position].name}"
        holder.customerPhoneNumber.text = "${mContext.resources.getString(R.string.phone_number)}: ${mList[position].phoneNumber}"
        holder.customerAddress.text = "${mContext.resources.getString(R.string.address)}: ${mList[position].address}"
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}