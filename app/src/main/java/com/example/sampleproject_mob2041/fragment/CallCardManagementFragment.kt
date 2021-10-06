package com.example.sampleproject_mob2041.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.adapter.CallCardAdapter
import com.example.sampleproject_mob2041.database.BookDB
import com.example.sampleproject_mob2041.database.CallCardDB
import com.example.sampleproject_mob2041.database.CustomerDB
import com.example.sampleproject_mob2041.database.Database
import com.example.sampleproject_mob2041.databinding.FragmentCallCardManagementBinding
import com.example.sampleproject_mob2041.model.Book
import com.example.sampleproject_mob2041.model.CallCard
import com.example.sampleproject_mob2041.model.Customer
import com.example.sampleproject_mob2041.viewhandler.SetupRecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import java.util.stream.Collectors
import javax.xml.datatype.DatatypeConstants.MONTHS
import kotlin.collections.ArrayList


class CallCardManagementFragment : Fragment() {
    private lateinit var binding: FragmentCallCardManagementBinding
    private lateinit var callCardDB: CallCardDB
    private lateinit var bookDB: BookDB
    private lateinit var customerDB: CustomerDB
    private lateinit var database: Database
    private lateinit var adapter: CallCardAdapter
    private lateinit var callCardList: ArrayList<CallCard>
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
        database = Database(requireContext())
        callCardDB = CallCardDB(database)
        bookDB = BookDB(database)
        customerDB = CustomerDB(database)
        adapter = CallCardAdapter(requireContext(), callCardDB.getAllCallCards())

        setupView(binding.callCardList)

        onFABClick(binding.fabAddCallCard)

        Toast.makeText(requireContext(), getLibrarianName(), Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun onFABClick(view: FloatingActionButton) {
        view.setOnClickListener {
            val dialogLayout =
                LayoutInflater.from(requireContext()).inflate(R.layout.new_call_card_dialog, null)
            val spinnerCustomerName: Spinner =
                dialogLayout.findViewById(R.id.new_call_card_customer)
            val spinnerBookName: Spinner = dialogLayout.findViewById(R.id.new_call_card_book)
            val edtLayoutBorrowTime: TextInputLayout =
                dialogLayout.findViewById(R.id.edt_layout_new_call_card_time)
            val edtBorrowTime: TextInputEditText =
                dialogLayout.findViewById(R.id.edt_new_call_card_time)

            setElementForSpinner(
                spinnerBookName,
                getBookNameAsStringList(bookDB.getAllBooks())
            )
            setElementForSpinner(
                spinnerCustomerName,
                getCustomerListAsStringList(customerDB.getAllCustomer())
            )

            edtLayoutBorrowTime.setEndIconOnClickListener {
                val calendar = Calendar.getInstance()
                DatePickerDialog(
                    requireContext(),
                    { view, year, month, dayOfMonth ->
                        edtBorrowTime.setText("$dayOfMonth/$month/$year")
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                    .show()
            }


            val dialog = AlertDialog.Builder(requireContext()).apply {
                setView(dialogLayout)
                setTitle(requireContext().getString(R.string.add))
                setMessage(requireContext().getString(R.string.add_call_card))
                setNegativeButton(requireContext().getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                setPositiveButton(requireContext().getString(R.string.add), null)
            }.show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                if (edtBorrowTime.text.isNullOrBlank()) {
                    edtLayoutBorrowTime.error =
                        requireContext().getString(R.string.you_must_enter_all_information)

                    Handler(Looper.getMainLooper()).postDelayed({
                        edtLayoutBorrowTime.error = null
                    }, 2000)
                } else {
                    adapter.addItem(
                        CallCard(
                            ID = null,
                            customerName = spinnerCustomerName.selectedItem.toString(),
                            bookName = spinnerBookName.selectedItem.toString(),
                            genre = getBookGenre(spinnerBookName.selectedItem.toString())!!.genre,
                            librarianName = getLibrarianName(),
                            borrowTime = edtBorrowTime.text.toString(),
                            isReturned = "0",
                            price = getBookPrice(spinnerBookName.selectedItem.toString())!!.price
                        )
                    )
                    dialog.dismiss()
                }
            }
        }
    }

    private fun getBookGenre(bookName: String): Book? {
        val optional = bookDB.getAllBooks().stream().filter { it.name == bookName }.findFirst()
        return if (optional.isPresent) {
            optional.get()
        } else null
    }

    private fun getBookPrice(bookName: String): Book? {
        val optional = bookDB.getAllBooks().stream().filter { it.name == bookName }.findFirst()
        return if (optional.isPresent) {
            optional.get()
        } else null
    }

    private fun getLibrarianName(): String = requireActivity().intent.getStringExtra("user_type")!!

    private fun getBookPrice(book: Book): Double = book.price


    private fun setElementForSpinner(view: Spinner, list: ArrayList<String>) {
        view.adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            list
        )
    }

    private fun getCustomerListAsStringList(list: ArrayList<Customer>): ArrayList<String> {
        val result = ArrayList<String>()
        for (element in list) {
            result.add(element.name)
        }
        return result
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getDateFromDatePicker(view: TextInputLayout): String {
        var result: String = " "
        view.setEndIconOnClickListener {

        }
        return result
    }


    private fun getBookNameAsStringList(list: ArrayList<Book>): ArrayList<String> {
        val result = ArrayList<String>()
        for (element in list) {
            result.add(element.name)
        }
        return result
    }

    private fun setupView(view: RecyclerView) {


        SetupRecyclerView().setupRecyclerView(
            requireContext(),
            view,
            adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>
        )
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        callCardList = callCardDB.getAllCallCards()
        when (item.itemId) {
            CallCardAdapter.EDIT -> {
                val view = LayoutInflater.from(requireContext())
                    .inflate(R.layout.edit_call_card_dialog, null)
                val spinnerCustomerName: Spinner =
                    view.findViewById(R.id.spinner_edit_call_card_customer)
                val spinnerBookName: Spinner =
                    view.findViewById(R.id.spinner_edit_call_card_book_name)
                val edtLayoutBorrowTime: TextInputLayout =
                    view.findViewById(R.id.edt_layout_edit_call_card_time)
                val edtBorrowTime: TextInputEditText =
                    view.findViewById(R.id.edt_edit_call_card_time)
                val chkIsReturned: CheckBox =
                    view.findViewById(R.id.chk_is_book_returned)

                setElementForSpinner(
                    spinnerBookName,
                    getBookNameAsStringList(bookDB.getAllBooks())
                )
                setElementForSpinner(
                    spinnerCustomerName,
                    getCustomerListAsStringList(customerDB.getAllCustomer())
                )

                edtLayoutBorrowTime.setEndIconOnClickListener {
                    val calendar = Calendar.getInstance()
                    DatePickerDialog(
                        requireContext(),
                        { view, year, month, dayOfMonth ->
                            edtBorrowTime.setText("$dayOfMonth/$month/$year")
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                        .show()
                }


                val dialog = AlertDialog.Builder(requireContext()).apply {
                    setView(view)
                    setTitle(requireContext().getString(R.string.edit))
                    setNegativeButton(requireContext().getText(R.string.cancel)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    setPositiveButton(requireContext().getText(R.string.edit), null)
                }.show()

                val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                positiveButton.setOnClickListener {
                    if (edtBorrowTime.text.isNullOrBlank()) {
                        edtLayoutBorrowTime.error =
                            requireContext().getString(R.string.you_must_enter_all_information)

                        Handler(Looper.getMainLooper()).postDelayed({
                            edtLayoutBorrowTime.error = null
                        }, 2000)
                    } else {
                        var isReturned = if (chkIsReturned.isChecked) "1" else "0"

                        adapter.editItem(
                            callCardList[item.groupId].ID!!,
                            CallCard(
                                ID = null,
                                customerName = spinnerCustomerName.selectedItem.toString(),
                                bookName = spinnerBookName.selectedItem.toString(),
                                genre = getBookGenre(spinnerBookName.selectedItem.toString())!!.genre,
                                librarianName = getLibrarianName(),
                                borrowTime = edtBorrowTime.text.toString(),
                                isReturned = isReturned,
                                price = getBookPrice(spinnerBookName.selectedItem.toString())!!.price

                            ),
                            item.groupId
                        )
                        dialog.dismiss()
                    }
                }

            }
            CallCardAdapter.DELETE -> {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle(requireContext().getText(R.string.delete))
                    setMessage(requireContext().getText(R.string.confirm_delete))
                    setNegativeButton(requireContext().getText(R.string.cancel)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    setPositiveButton(requireContext().getText(R.string.delete)) { _, _ ->
                        adapter.removeItem(callCardList[item.groupId].ID!!, item.groupId)
                    }
                }.show()

            }
        }
        return super.onContextItemSelected(item)
    }


}