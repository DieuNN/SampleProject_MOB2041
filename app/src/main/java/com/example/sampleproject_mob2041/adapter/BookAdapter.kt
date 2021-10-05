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
import com.example.sampleproject_mob2041.database.BookDB
import com.example.sampleproject_mob2041.database.Database
import com.example.sampleproject_mob2041.model.Book

class BookAdapter(private val mContext: Context, var mList: ArrayList<Book>) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    private lateinit var bookDB:BookDB

    companion object {
        const val EDIT = 141
        const val DELETE = 142
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.book_item, parent, false))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
        val name:TextView = itemView.findViewById(R.id.book_item_name)
        val genre:TextView = itemView.findViewById(R.id.book_item_genre)
        val price:TextView = itemView.findViewById(R.id.book_item_price)
        val bookItem:RelativeLayout = itemView.findViewById(R.id.book_item)

        init {
            bookItem.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu.add(adapterPosition, EDIT, 0, "Sửa")
            menu.add(adapterPosition, DELETE, 1, "Xóa")
        }


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = "${mContext.resources.getString(R.string.name)}: ${mList[position].name}"
        holder.genre.text = "${mContext.resources.getString(R.string.genre)}: ${mList[position].genre}"
        holder.price.text = "${mContext.resources.getString(R.string.price)}: ${mList[position].price}"
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun deleteItem(position: Int) {
        bookDB = BookDB(Database(mContext))

        if (bookDB.removeBook(mList[position].name)) {
            Toast.makeText(mContext, mContext.getString(R.string.remove_successful), Toast.LENGTH_SHORT).show()
            mList.clear()
            mList = bookDB.getAllBooks()
            this.notifyItemRemoved(position)
            this.notifyItemRangeChanged(0, mList.size)
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.remove_failed), Toast.LENGTH_SHORT).show()
        }
    }

    fun addItem(book: Book) {
        bookDB = BookDB(Database(mContext))

        if(bookDB.addBook(book.name, book.genre, book.price)) {
            Toast.makeText(mContext, mContext.getString(R.string.add_successful), Toast.LENGTH_SHORT).show()
            mList.clear()
            mList = bookDB.getAllBooks()
            this.notifyItemInserted(mList.size)
            this.notifyItemRangeChanged(0, mList.size)
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.add_failed), Toast.LENGTH_SHORT).show()
        }
    }

    fun editItem(position: Int, newBookValue:Book) {
        bookDB = BookDB(Database(mContext))

        if (bookDB.editBook(mList[position].name, newBookValue)) {
            Toast.makeText(mContext, mContext.getString(R.string.edit_successfull), Toast.LENGTH_SHORT).show()
            mList.clear()
            mList = bookDB.getAllBooks()
            this.notifyItemChanged(position)
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.edit_failed), Toast.LENGTH_SHORT).show()
        }
    }
}