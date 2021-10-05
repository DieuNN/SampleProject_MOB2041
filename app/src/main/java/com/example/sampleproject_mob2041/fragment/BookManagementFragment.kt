package com.example.sampleproject_mob2041.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.adapter.BookAdapter
import com.example.sampleproject_mob2041.database.BookDB
import com.example.sampleproject_mob2041.database.Database
import com.example.sampleproject_mob2041.database.GenreDB
import com.example.sampleproject_mob2041.databinding.FragmentBookManagementBinding
import com.example.sampleproject_mob2041.model.Book
import com.example.sampleproject_mob2041.viewhandler.SetupRecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class BookManagementFragment : Fragment() {
    private lateinit var binding: FragmentBookManagementBinding
    private lateinit var bookList: ArrayList<Book>
    private lateinit var bookDB: BookDB
    private lateinit var adapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookDB = BookDB(Database(requireContext()))
        bookList = bookDB.getAllBooks()
        adapter = BookAdapter(requireContext(), bookList)

        setupRecyclerView(binding.bookList)

        onFABClick(binding.fabAddBook)
    }

    private fun setupRecyclerView(view: RecyclerView) {

        SetupRecyclerView().setupRecyclerView(
            requireContext(),
            view,
            adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>
        )
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            BookAdapter.EDIT -> {
                val view =
                    LayoutInflater.from(requireContext()).inflate(R.layout.new_book_dialog, null)
                val edtLayoutBookName: TextInputLayout =
                    view.findViewById(R.id.edt_layout_new_book_name)
                val spinnerGenre: Spinner = view.findViewById(R.id.spinner_new_book_genre)
                val edtLayoutBookPrice: TextInputLayout =
                    view.findViewById(R.id.edt_layout_new_book_price)
                val edtBookName: TextInputEditText = view.findViewById(R.id.edt_new_book_name)
                val edtBookPrice: TextInputEditText = view.findViewById(R.id.edt_new_book_price)

                edtBookName.setText(bookList[item.groupId].name)
                edtBookPrice.setText(bookList[item.groupId].price.toString())

                val genreList = GenreDB(Database(mContext = requireContext())).getAllGenres()
                val genreStringArray = arrayListOf<String>()

                for (element in genreList) {
                    genreStringArray.add(element.name)
                }

                spinnerGenre.adapter = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    genreStringArray
                )

                val dialog = AlertDialog.Builder(requireContext())
                    .setTitle(requireContext().getString(R.string.edit))
                    .setView(view)
                    .setNegativeButton(requireContext().getString(R.string.cancel)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(requireContext().getString(R.string.edit), null)
                    .show()

                val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                positiveButton.setOnClickListener {
                    if (edtBookName.text.isNullOrBlank()) {
                        edtLayoutBookName.error =
                            requireContext().getString(R.string.you_must_enter_all_information)
                    } else if (edtBookPrice.text.isNullOrBlank()) {
                        edtLayoutBookPrice.error =
                            requireContext().getString(R.string.you_must_enter_all_information)
                    } else {
                        adapter.editItem(
                            item.groupId,
                            Book(
                                name = edtBookName.text.toString(),
                                genre = spinnerGenre.selectedItem.toString(),
                                price = edtBookPrice.text.toString().toDouble()
                            )
                        )
                        bookList = bookDB.getAllBooks()
                        dialog.dismiss()
                    }
                }
            }
            BookAdapter.DELETE -> {
                AlertDialog.Builder(requireContext())
                    .setTitle(requireContext().getString(R.string.confirm_delete))
                    .setNegativeButton(requireContext().getString(R.string.cancel)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(requireContext().getString(R.string.delete)) { _, _ ->
                        adapter.deleteItem(item.groupId)
                    }
                    .show()
            }
        }

        return super.onContextItemSelected(item)
    }

    private fun onFABClick(view: FloatingActionButton) {
        view.setOnClickListener {
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.new_book_dialog, null)
            val edtLayoutBookName: TextInputLayout =
                view.findViewById(R.id.edt_layout_new_book_name)
            val spinnerGenre: Spinner = view.findViewById(R.id.spinner_new_book_genre)
            val edtLayoutBookPrice: TextInputLayout =
                view.findViewById(R.id.edt_layout_new_book_price)
            val edtBookName: TextInputEditText = view.findViewById(R.id.edt_new_book_name)
            val edtBookPrice: TextInputEditText = view.findViewById(R.id.edt_new_book_price)

            val genreList = GenreDB(Database(mContext = requireContext())).getAllGenres()
            val genreStringArray = arrayListOf<String>()

            for (element in genreList) {
                genreStringArray.add(element.name)
            }

            spinnerGenre.adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                genreStringArray
            )

            val dialog = AlertDialog.Builder(requireContext())
                .setTitle(requireContext().getString(R.string.edit))
                .setView(view)
                .setNegativeButton(requireContext().getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(requireContext().getString(R.string.edit), null)
                .show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                if (edtBookName.text.isNullOrBlank()) {
                    edtLayoutBookName.error =
                        requireContext().getString(R.string.you_must_enter_all_information)
                } else if (edtBookPrice.text.isNullOrBlank()) {
                    edtLayoutBookPrice.error =
                        requireContext().getString(R.string.you_must_enter_all_information)
                } else {
                    adapter.addItem(
                        Book(
                            name = edtBookName.text.toString(),
                            genre = spinnerGenre.selectedItem.toString(),
                            price = edtBookPrice.text.toString().toDouble()
                        )
                    )
                    dialog.dismiss()
                }
            }
        }
    }


}