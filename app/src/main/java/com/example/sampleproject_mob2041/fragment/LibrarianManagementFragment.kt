package com.example.sampleproject_mob2041.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.adapter.LibrarianAdapter
import com.example.sampleproject_mob2041.database.Database
import com.example.sampleproject_mob2041.database.LibrarianDB
import com.example.sampleproject_mob2041.databinding.FragmentLibrarianBinding
import com.example.sampleproject_mob2041.model.Librarian
import com.example.sampleproject_mob2041.viewhandler.SetupRecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LibrarianManagementFragment : Fragment() {
    private lateinit var binding: FragmentLibrarianBinding
    private lateinit var librarianDB: LibrarianDB
    private lateinit var adapter: LibrarianAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLibrarianBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        librarianDB = LibrarianDB(Database(requireContext()))
        adapter = LibrarianAdapter(requireContext(), librarianDB.getAllLibrarians())

        SetupRecyclerView().setupRecyclerView(
            requireContext(),
            binding.librarianList,
            adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>
        )

        onFABClick(binding.fabAddLibrarian)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            LibrarianAdapter.DELETE -> {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle(requireContext().getText(R.string.delete))
                    setMessage(requireContext().getText(R.string.confirm_delete))
                    setNegativeButton(requireContext().getText(R.string.cancel)) {dialog,_ ->
                        dialog.dismiss()
                    }
                    setPositiveButton(requireContext().getText(R.string.delete)) {_, _ ->
                        adapter.deleteItem(item.groupId)
                    }
                }.show()
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun onFABClick(view: FloatingActionButton) {
        view.setOnClickListener {
            val dialogView =
                LayoutInflater.from(requireContext()).inflate(R.layout.new_librarian_dialog, null)
            val edtLayoutLibrarianDisplayName: TextInputLayout =
                dialogView.findViewById(R.id.edt_layout_new_librarian_display_name)
            val edtLayoutLibrarianLoginName: TextInputLayout =
                dialogView.findViewById(R.id.edt_layout_new_librarian_login_name)
            val edtLibrarianDisplayName: TextInputEditText =
                dialogView.findViewById(R.id.edt_new_librarian_display_name)
            val edtLibrarianLoginName: TextInputEditText =
                dialogView.findViewById(R.id.edt_new_librarian_login_name)
            val edtLayoutLibrarianPassword: TextInputLayout =
                dialogView.findViewById(R.id.edt_layout_new_librarian_password)
            val edtLibrarianPassword: TextInputEditText =
                dialogView.findViewById(R.id.edt_new_librarian_password)

            val dialog = AlertDialog.Builder(requireContext()).apply {
                setView(dialogView)
                setTitle(requireContext().getText(R.string.add_librarian))
                setNegativeButton(requireContext().getText(R.string.cancel)) { _, _ ->

                }
                setPositiveButton(requireContext().getText(R.string.add), null)
            }.show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                if (edtLibrarianDisplayName.text.isNullOrBlank()) {
                    edtLayoutLibrarianDisplayName.error =
                        requireContext().getText(R.string.you_must_enter_all_information)
                } else if (edtLibrarianLoginName.text.isNullOrBlank()) {
                    edtLibrarianLoginName.error =
                        requireContext().getText(R.string.you_must_enter_all_information)
                } else if (edtLibrarianPassword.text.isNullOrBlank()) {
                    edtLayoutLibrarianPassword.error =
                        requireContext().getText(R.string.you_must_enter_all_information)
                } else {
                    adapter.addItem(
                        Librarian(
                            edtLibrarianLoginName.text.toString(),
                            edtLibrarianDisplayName.text.toString(),
                            edtLibrarianPassword.text.toString()
                        )
                    )
                    dialog.dismiss()
                }
                Handler(Looper.getMainLooper()).postDelayed({
                    edtLayoutLibrarianDisplayName.error = null
                    edtLibrarianLoginName.error = null
                    edtLayoutLibrarianPassword.error = null
                }, 2000)
            }
        }
    }

}