package com.example.sampleproject_mob2041.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.adapter.BookAdapter
import com.example.sampleproject_mob2041.databinding.FragmentBookManagementBinding
import com.example.sampleproject_mob2041.model.Book
import com.example.sampleproject_mob2041.viewhandler.SetupRecyclerView


class BookManagementFragment : Fragment() {
    private lateinit var binding: FragmentBookManagementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(binding.bookList)
    }

    private fun setupRecyclerView(view: RecyclerView) {
        val dataExample = ArrayList<Book>()
        dataExample.apply {
            add(Book("Book1", "Gen1", 100.3))
            add(Book("Book1", "Gen1", 1000.0))
            add(Book("Book1", "Gen1", 1000.0))
            add(Book("Book1", "Gen1", 1000.0))
            add(Book("Book1", "Gen1", 1000.0))

        }

        SetupRecyclerView().setupRecyclerView(
            requireContext(),
            view,
            BookAdapter(requireContext(), dataExample) as RecyclerView.Adapter<RecyclerView.ViewHolder>
        )
    }


}