package com.example.sampleproject_mob2041.viewhandler

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.adapter.GenreAdapter
import com.example.sampleproject_mob2041.model.Genre

class SetupRecyclerView {
    fun setupRecyclerView(mContext:Context,view:RecyclerView, mAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))


        }
    }



}