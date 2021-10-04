package com.example.sampleproject_mob2041.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.adapter.CallCardAdapter
import com.example.sampleproject_mob2041.databinding.FragmentCallCardManagementBinding
import com.example.sampleproject_mob2041.model.CallCard
import com.example.sampleproject_mob2041.viewhandler.SetupRecyclerView


class CallCardManagementFragment : Fragment() {
   private lateinit var binding:FragmentCallCardManagementBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCallCardManagementBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView(binding.callCardList)
    }

    private fun setupView(view:RecyclerView) {
        val dataExample = ArrayList<CallCard>()
        dataExample.apply {
            add(CallCard(null, "Customer", "Book", "Genre", "Librarian", "20/12/2002", "0"))
            add(CallCard(null, "Customer", "Book", "Genre", "Librarian", "20/12/2002", "0"))
            add(CallCard(null, "Customer", "Book", "Genre", "Librarian", "20/12/2002", "1"))
            add(CallCard(null, "Customer", "Book", "Genre", "Librarian", "20/12/2002", "1"))

        }

        SetupRecyclerView().setupRecyclerView(
            requireContext(),
            view,
            CallCardAdapter(requireContext(), dataExample) as RecyclerView.Adapter<RecyclerView.ViewHolder>
        )
    }


}