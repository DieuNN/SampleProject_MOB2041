package com.example.sampleproject_mob2041.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.model.Book

class BookAdapter(private val mContext: Context, val mList: ArrayList<Book>) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.book_item, null))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name:TextView = itemView.findViewById(R.id.book_item_name)
        val genre:TextView = itemView.findViewById(R.id.book_item_genre)
        val price:TextView = itemView.findViewById(R.id.book_item_price)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = "${mContext.resources.getString(R.string.name)}: ${mList[position].name}"
        holder.genre.text = "${mContext.resources.getString(R.string.genre)}: ${mList[position].genre}"
        holder.price.text = "${mContext.resources.getString(R.string.price)}: ${mList[position].price}"
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}