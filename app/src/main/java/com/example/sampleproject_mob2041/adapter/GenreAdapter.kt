package com.example.sampleproject_mob2041.adapter

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.database.Database
import com.example.sampleproject_mob2041.database.GenreDB
import com.example.sampleproject_mob2041.model.Genre

class GenreAdapter( val mContext: Context, var mList:ArrayList<Genre>) : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    private lateinit var genreDB: GenreDB

    companion object {
        const val DELETE = 121
        const val EDIT = 122
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
        val genre: TextView = itemView.findViewById(R.id.genre_item_name)
        val genreItem:LinearLayout = itemView.findViewById(R.id.genre_item)

        init {
            genreItem.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.add(adapterPosition, EDIT, 0, "Sửa")
            menu?.add(adapterPosition, DELETE, 1, "Xoá")
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.genre_item, parent, false))
    }

    override fun onBindViewHolder(holder: GenreAdapter.ViewHolder, position: Int) {
        holder.genre.text = mList[position].name

        

    }

    override fun getItemCount(): Int {
       return mList.size
    }
    
    fun deleteItem(position: Int) {
        genreDB = GenreDB(Database(mContext))
        
        if (genreDB.removeGenre(mList[position].name)) {
            Toast.makeText(mContext, mContext.getText(R.string.remove_successful), Toast.LENGTH_SHORT).show()
            mList.clear()
            mList = genreDB.getAllGenres()
            this.notifyItemRangeChanged(position, mList.size)
            this.notifyItemRemoved(position)
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.remove_failed), Toast.LENGTH_SHORT).show()
        }
    }


    fun editItem(position: Int, newValue:Genre) {
        genreDB = GenreDB(Database(mContext))

        if (genreDB.editGenre(mList[position].name, newValue)) {
            Toast.makeText(mContext, mContext.getText(R.string.edit_successfull), Toast.LENGTH_SHORT).show()
            mList.clear()
            mList = genreDB.getAllGenres()
            this.notifyItemChanged(position)
            this.notifyItemRangeChanged(0, mList.size)
        } else{
            Toast.makeText(mContext, mContext.getText(R.string.edit_failed), Toast.LENGTH_SHORT).show()
        }
    }
}