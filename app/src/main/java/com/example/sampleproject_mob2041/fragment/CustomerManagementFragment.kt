package com.example.sampleproject_mob2041.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.adapter.CustomerAdapter
import com.example.sampleproject_mob2041.databinding.FragmentCustomerManagementBinding
import com.example.sampleproject_mob2041.model.Customer
import com.example.sampleproject_mob2041.viewhandler.SetupRecyclerView


class CustomerManagementFragment : Fragment() {
    private lateinit var binding: FragmentCustomerManagementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(binding.customerList)
    }

    private fun setupView(view: RecyclerView) {
        val dataExample = ArrayList<Customer>()
        dataExample.apply {
            add(Customer("Customer", "0965343641", "yen Bai"))
            add(Customer("Customer", "0965343641", "yen Bai"))
            add(Customer("Customer", "0965343641", "yen Bai"))
            add(Customer("Customer", "0965343641", "yen Bai"))
            add(Customer("Customer", "0965343641", "yen Bai"))
            add(Customer("Customer", "0965343641", "yen Bai"))
            add(Customer("Customer", "0965343641", "yen Bai"))
        }
        SetupRecyclerView().setupRecyclerView(
            requireContext(),
            view,
            CustomerAdapter(requireContext(), dataExample) as RecyclerView.Adapter<RecyclerView.ViewHolder>
        )
    }


}