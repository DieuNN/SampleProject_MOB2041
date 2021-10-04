package com.example.sampleproject_mob2041.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.model.Genre

class GenreAdapter(private val mContext: Context, val mList:ArrayList<Genre>) : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val genre: TextView = itemView.findViewById(R.id.genre_item_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.genre_item, null))
    }

    override fun onBindViewHolder(holder: GenreAdapter.ViewHolder, position: Int) {
        holder.genre.text = mList[position].name
    }

    override fun getItemCount(): Int {
       return mList.size
    }
}