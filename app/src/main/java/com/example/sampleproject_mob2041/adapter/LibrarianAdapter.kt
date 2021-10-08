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
import com.example.sampleproject_mob2041.database.Database
import com.example.sampleproject_mob2041.database.LibrarianDB
import com.example.sampleproject_mob2041.model.Librarian

class LibrarianAdapter(private val mContext: Context, var mList: ArrayList<Librarian>) :
    RecyclerView.Adapter<LibrarianAdapter.ViewHolder>() {
    private lateinit var librarianDB: LibrarianDB

    companion object {
        const val DELETE = 161
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {
        var displayName: TextView = itemView.findViewById(R.id.txt_display_name_librarian_item)
        var loginName: TextView = itemView.findViewById(R.id.txt_login_name_librarian_item)
        val librarianItem: RelativeLayout = itemView.findViewById(R.id.librarian_item)

        init {
            librarianItem.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu!!.add(adapterPosition, DELETE, 0, "Xóa")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.librarian_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.displayName.text = "${mContext.getText(R.string.name)}: ${mList[position].displayName}"
        holder.loginName.text = "${mContext.getText(R.string.login_name)}: ${mList[position].loginName}"


    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun addItem(librarian: Librarian) {
        librarianDB = LibrarianDB(Database(mContext))

        if (librarianDB.addLibrarian(librarian.loginName, librarian.displayName, librarian.password)) {
            Toast.makeText(mContext, mContext.getText(R.string.add_successful), Toast.LENGTH_SHORT).show()
            mList.clear()
            mList = librarianDB.getAllLibrarians()
            this.notifyItemInserted(mList.size)
            this.notifyItemRangeChanged(0, mList.size)
        } else {
            Toast.makeText(mContext, mContext.getText(R.string.add_failed), Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteItem(position: Int) {
        librarianDB = LibrarianDB(Database(mContext))

        if (librarianDB.removeLibrarian(mList[position].loginName)) {
            Toast.makeText(mContext, mContext.getText(R.string.remove_successful), Toast.LENGTH_SHORT).show()
            mList.clear()
            mList = librarianDB.getAllLibrarians()
            this.notifyItemRemoved(position)
            this.notifyItemRangeChanged(0, mList.size)
        } else {
            Toast.makeText(mContext, mContext.getText(R.string.remove_successful), Toast.LENGTH_SHORT).show()
        }
    }
}