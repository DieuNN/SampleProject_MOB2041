package com.example.sampleproject_mob2041.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.adapter.GenreAdapter
import com.example.sampleproject_mob2041.databinding.FragmentGenreBinding
import com.example.sampleproject_mob2041.model.Genre
import com.example.sampleproject_mob2041.viewhandler.SetupRecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class GenreManagementFragment : Fragment() {
    private lateinit var binding: FragmentGenreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(binding.genreList)

        setupFAB(binding.fabAddGenre)

    }

    private fun setupRecyclerView(view: RecyclerView) {
        val dataExample: ArrayList<Genre> = ArrayList()
        dataExample.apply {
            add(Genre("alo 123312421321"))
            add(Genre("du ma vcll fedffdq"))
            add(Genre("alo 123312421321"))
            add(Genre("du ma vcll fedffdq"))
            add(Genre("alo 123312421321"))
            add(Genre("du ma vcll fedffdq"))
        }
        val adapter = GenreAdapter(requireContext(), dataExample)
        SetupRecyclerView().setupRecyclerView(
                requireContext(),
                binding.genreList,
                adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>
            )
    }

    private fun setupFAB(view:FloatingActionButton) {
        view.setOnClickListener {
        }
    }


}