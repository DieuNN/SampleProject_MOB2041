package com.example.sampleproject_mob2041.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.adapter.Top10BestSellerAdapter
import com.example.sampleproject_mob2041.database.BookDB
import com.example.sampleproject_mob2041.database.Database
import com.example.sampleproject_mob2041.databinding.FragmentTop10Binding
import com.example.sampleproject_mob2041.viewhandler.SetupRecyclerView


class Top10Fragment : Fragment() {
    private lateinit var binding: FragmentTop10Binding
    private lateinit var adapter: Top10BestSellerAdapter
    private lateinit var bookDB: BookDB
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTop10Binding.inflate(layoutInflater, container, false)
        bookDB = BookDB(Database(requireContext()))
        adapter = Top10BestSellerAdapter(requireContext(), bookDB.getAllBooks())
        SetupRecyclerView().setupRecyclerView(
            requireContext(),
            binding.top10SellerList,
            adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}