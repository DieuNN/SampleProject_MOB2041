package com.example.sampleproject_mob2041.adapter

import android.content.Context
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

class GenreAdapter(private val mContext: Context, var mList:ArrayList<Genre>) : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    private lateinit var genreDB: GenreDB
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val genre: TextView = itemView.findViewById(R.id.genre_item_name)
        val genreItem:LinearLayout = itemView.findViewById(R.id.genre_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.genre_item, parent, false))
    }

    override fun onBindViewHolder(holder: GenreAdapter.ViewHolder, position: Int) {
        holder.genre.text = mList[position].name
        
        holder.genreItem.setOnLongClickListener {
            deleteItem(mList[position])
            this.notifyItemRemoved(position)
            mList.clear()
            mList = genreDB.getAllGenres()
            this.notifyItemRangeChanged(position, genreDB.getAllGenres().size)
            true
        }
    }

    override fun getItemCount(): Int {
       return mList.size
    }
    
    private fun deleteItem(genre: Genre) {
        genreDB = GenreDB(Database(mContext))
        
        if (genreDB.removeGenre(genre.name)) {
            Toast.makeText(mContext, mContext.getText(R.string.remove_successful), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.remove_failed), Toast.LENGTH_SHORT).show()
        }
    }
}