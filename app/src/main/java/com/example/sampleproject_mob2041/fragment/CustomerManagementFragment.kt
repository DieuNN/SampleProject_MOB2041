package com.example.sampleproject_mob2041.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.adapter.CustomerAdapter
import com.example.sampleproject_mob2041.database.CustomerDB
import com.example.sampleproject_mob2041.database.Database
import com.example.sampleproject_mob2041.databinding.FragmentCustomerManagementBinding
import com.example.sampleproject_mob2041.model.Customer
import com.example.sampleproject_mob2041.viewhandler.SetupRecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class CustomerManagementFragment : Fragment() {
    private lateinit var binding: FragmentCustomerManagementBinding
    private lateinit var adapter: CustomerAdapter
    private lateinit var customerDB: CustomerDB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customerDB = CustomerDB(Database(requireContext()))
        adapter = CustomerAdapter(requireContext(), customerDB.getAllCustomer())

        setupView(binding.customerList)

        onFabClick(binding.fabAddCustomer)
    }

    private fun onFabClick(view: FloatingActionButton) {
        view.setOnClickListener {
            val view =
                LayoutInflater.from(requireContext()).inflate(R.layout.new_customer_dialog, null)
            val edtLayoutCustomerName: TextInputLayout =
                view.findViewById(R.id.edt_layout_new_customer_name)
            val edtLayoutCustomerPhoneNumber: TextInputLayout =
                view.findViewById(R.id.edt_layout_new_customer_phone_number)
            val edtLayoutCustomerAddress: TextInputLayout =
                view.findViewById(R.id.edt_layout_new_customer_address)
            val edtCustomerName: TextInputEditText = view.findViewById(R.id.edt_new_customer_name)
            val edtCustomerPhoneNumber: TextInputEditText =
                view.findViewById(R.id.edt_new_customer_phone_number)
            val edtCustomerAddress: TextInputEditText =
                view.findViewById(R.id.edt_new_customer_address)


            val dialog = AlertDialog.Builder(requireContext())
                .setTitle(requireContext().getText(R.string.add_new_customer))
                .setView(view)
                .setNegativeButton(requireContext().getText(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(requireContext().getText(R.string.add), null)
                .show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                if (edtCustomerName.text.isNullOrBlank()) {
                    edtLayoutCustomerName.error =
                        requireContext().getText(R.string.you_must_enter_all_information)
                } else if (edtCustomerAddress.text.isNullOrBlank()) {
                    edtLayoutCustomerAddress.error =
                        requireContext().getText(R.string.you_must_enter_all_information)
                } else if (edtCustomerPhoneNumber.text.isNullOrBlank()) {
                    edtLayoutCustomerPhoneNumber.error =
                        requireContext().getText(R.string.you_must_enter_all_information)
                } else {
                    adapter.addItem(
                        Customer(
                            edtCustomerName.text.toString(),
                            edtCustomerPhoneNumber.text.toString(),
                            edtCustomerAddress.text.toString()
                            )
                    )
                    dialog.dismiss()
                }
            }
        }
    }

    private fun setupView(view: RecyclerView) {
        SetupRecyclerView().setupRecyclerView(
            requireContext(),
            view,
            adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>
        )
    }


}