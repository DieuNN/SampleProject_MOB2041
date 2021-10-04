package com.example.sampleproject_mob2041.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.model.CallCard

class CallCardAdapter( val mContext: Context,var mList:ArrayList<CallCard>) :
    RecyclerView.Adapter<CallCardAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ID:TextView = itemView.findViewById(R.id.call_card_item_ID)
        val customerName:TextView = itemView.findViewById(R.id.call_card_item_customer_name)
        val bookName:TextView = itemView.findViewById(R.id.call_card_item_book_name)
        val librarianName:TextView = itemView.findViewById(R.id.call_card_item_librarian)
        val returnStatus:TextView = itemView.findViewById(R.id.call_card_item_return_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallCardAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.call_card_item, null))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: CallCardAdapter.ViewHolder, position: Int) {
        holder.ID.text = "${mContext.resources.getString(R.string.ID)}: ${mList[position].ID}"
        holder.customerName.text = "${mContext.resources.getString(R.string.customer_name)}: ${mList[position].customerName}"
        holder.bookName.text = "${mContext.resources.getString(R.string.book_name)}: ${mList[position].bookName}"
        holder.librarianName.text = "${mContext.resources.getString(R.string.librarian_name)}: ${mList[position].librarianName}"
        holder.returnStatus.text = setReturnStatusText(mList[position].isReturned)

        // Set color for return status
        if (isReturned(mList[position].isReturned)) {
            holder.returnStatus.setTextColor(mContext.getColor(R.color.blue))
        } else {
            holder.returnStatus.setTextColor(mContext.getColor(R.color.red))
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    // Check if returned
    private fun isReturned(strFromDB:String):Boolean = strFromDB != "0"

    // If not returned, set text for return status
    private fun setReturnStatusText(strFromDB:String):String =
         if (!isReturned(strFromDB)) "${mContext.resources.getString(R.string.not_returned)}" else "${mContext.resources.getString(R.string.returned)}"

}