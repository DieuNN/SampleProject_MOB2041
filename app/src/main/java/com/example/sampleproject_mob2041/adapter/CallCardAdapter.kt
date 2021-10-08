package com.example.sampleproject_mob2041.adapter

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.database.CallCardDB
import com.example.sampleproject_mob2041.database.Database
import com.example.sampleproject_mob2041.model.CallCard

class CallCardAdapter(val mContext: Context, var mList: ArrayList<CallCard>) :
    RecyclerView.Adapter<CallCardAdapter.ViewHolder>() {
    private lateinit var callCardDB: CallCardDB
    companion object {
        const val EDIT = 151
        const val DELETE = 152
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
        val ID: TextView = itemView.findViewById(R.id.call_card_item_ID)
        val customerName: TextView = itemView.findViewById(R.id.call_card_item_customer_name)
        val bookName: TextView = itemView.findViewById(R.id.call_card_item_book_name)
        val librarianName: TextView = itemView.findViewById(R.id.call_card_item_librarian)
        val returnStatus: TextView = itemView.findViewById(R.id.call_card_item_return_status)
        val callCardItem: RelativeLayout = itemView.findViewById(R.id.call_card_item)

        init {
            callCardItem.setOnCreateContextMenuListener(this)
        }
        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu!!.add(adapterPosition, EDIT, 0, "Sửa")
            menu.add(adapterPosition, DELETE, 1, "Xóa")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallCardAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.call_card_item, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: CallCardAdapter.ViewHolder, position: Int) {
        holder.ID.text = "${mContext.resources.getString(R.string.ID)}: ${mList[position].ID}"
        holder.customerName.text =
            "${mContext.resources.getString(R.string.customer_name)}: ${mList[position].customerName}"
        holder.bookName.text =
            "${mContext.resources.getString(R.string.book_name)}: ${mList[position].bookName}"
        holder.librarianName.text =
            "Cho mượn/trả bởi: ${mList[position].librarianName}"
        holder.returnStatus.text = getReturnStatusText(mList[position].isReturned)

        holder.callCardItem.setOnClickListener {
            showInfo(callCard = mList[position])
        }


        // Set color for return status
        if (isReturned(mList[position].isReturned)) {
            holder.returnStatus.setTextColor(mContext.getColor(R.color.blue))
        } else {
            holder.returnStatus.setTextColor(mContext.getColor(R.color.red))
        }
    }

    private fun showInfo(callCard: CallCard) {
        val returnStatusText = getReturnStatusText(callCard.isReturned)
        AlertDialog.Builder(mContext).apply {
            setTitle(mContext.getText(R.string.information))
            setMessage(
                "${mContext.getText(R.string.ID)}: ${callCard.ID} \n" +
                        "${mContext.getText(R.string.customer_name)}: ${callCard.customerName} \n" +
                        "${mContext.getText(R.string.book_name)}: ${callCard.bookName} \n" +
                        "${mContext.getText(R.string.genre)}: ${callCard.genre} \n" +
                        "${mContext.getText(R.string.time)}: ${callCard.borrowTime} \n" +
                        "${mContext.getText(R.string.returned)}: $returnStatusText \n" +
                        "${mContext.getText(R.string.price)}: ${callCard.price} \n"
            )
        }.show()
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    // Check if returned
    private fun isReturned(strFromDB: String): Boolean = strFromDB != "0"

    // If not returned, set text for return status
    private fun getReturnStatusText(strFromDB: String): String =
        if (!isReturned(strFromDB)) "${mContext.resources.getString(R.string.not_returned)}" else "${
            mContext.resources.getString(
                R.string.returned
            )
        }"

    fun addItem(callCard: CallCard) {
        callCardDB = CallCardDB(Database(mContext))

        if (callCardDB.addCallCard(
                null,
                callCard.customerName,
                callCard.bookName,
                callCard.genre,
                callCard.librarianName,
                callCard.borrowTime,
                callCard.isReturned,
                callCard.price
            )
        ) {
            Toast.makeText(mContext, mContext.getText(R.string.add_successful), Toast.LENGTH_SHORT)
                .show()
            mList.clear()
            mList = callCardDB.getAllCallCards()
            this.notifyItemInserted(mList.size)
            this.notifyItemRangeChanged(0, mList.size)
        } else {
            Toast.makeText(mContext, mContext.getText(R.string.add_failed), Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun editItem(ID: Int, callCard: CallCard, position: Int) {
        callCardDB = CallCardDB(Database(mContext))

        if (callCardDB.editCard(
               ID,
                CallCard(
                    null,
                    callCard.customerName,
                    callCard.bookName,
                    callCard.genre,
                    callCard.librarianName,
                    callCard.borrowTime,
                    callCard.isReturned,
                    callCard.price
                )
            )
        ) {
            Toast.makeText(mContext, mContext.getText(R.string.edit_successfull), Toast.LENGTH_SHORT)
                .show()
            mList.clear()
            mList = callCardDB.getAllCallCards()
            this.notifyItemChanged(position)
            this.notifyItemRangeChanged(0, mList.size)
        } else {
            Toast.makeText(mContext, mContext.getText(R.string.edit_failed), Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun removeItem(ID: Int, position: Int) {
        callCardDB = CallCardDB(Database(mContext))

        if (callCardDB.removeCard(ID)) {
            Toast.makeText(mContext, mContext.getText(R.string.remove_successful), Toast.LENGTH_SHORT).show()
            mList.clear()
            mList = callCardDB.getAllCallCards()
            this.notifyItemRemoved(position)
            this.notifyItemRangeChanged(0, mList.size)
        } else {
            Toast.makeText(mContext, mContext.getText(R.string.remove_failed), Toast.LENGTH_SHORT).show()
        }
    }



}