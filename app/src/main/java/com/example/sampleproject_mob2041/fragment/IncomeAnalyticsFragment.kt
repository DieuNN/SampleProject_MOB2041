package com.example.sampleproject_mob2041.fragment

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.database.CallCardDB
import com.example.sampleproject_mob2041.database.Database
import com.example.sampleproject_mob2041.databinding.FragmentIncomeAnalyticsBinding
import com.example.sampleproject_mob2041.model.CallCard
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.streams.toList


class IncomeAnalyticsFragment : Fragment() {
    private lateinit var binding: FragmentIncomeAnalyticsBinding
    private lateinit var callCardDB: CallCardDB
    private lateinit var callCardInDateRange:ArrayList<CallCard>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIncomeAnalyticsBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callCardDB = CallCardDB(Database(requireContext()))

        setTextOnDateSet(binding.edtLayoutAnalyticsFromDay, binding.edtAnalyticsFromDay)
        setTextOnDateSet(binding.edtLayoutAnalyticsToDay, binding.edtAnalyticsToDay)

        binding.btnStartAnalytics.setOnClickListener {
            if (validate()) {
                startAnalytics()
            }
        }
    }

    // open date picker dialog view, set text for edittext
    private fun setTextOnDateSet(edtLayout: TextInputLayout, edt: TextInputEditText) {
        edtLayout.setEndIconOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                requireContext(),
                { view, year, month, dayOfMonth ->
                    edt.setText("$dayOfMonth/$month/$year")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
                .show()
        }
    }

    // parse input string to date
    private fun parseStringToDate(date: String): Date? {
        return try {
            SimpleDateFormat("dd/MM/yyyy").parse(date)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Lỗi!", Toast.LENGTH_SHORT).show()
            null
        }
    }

    // Analytics
    private fun startAnalytics() {
        // get amount in range and set text
        val callCardListInDateRange = getCallCardListInDateRange()
        binding.txtTotalIncome.text = "${requireContext().getText(R.string.total_income)}: ${getTotalIncomeAmount(callCardListInDateRange)}"
        binding.txtReturnedIncome.text = "${requireContext().getText(R.string.returned)}: ${getReturnedIncomeAmount(callCardListInDateRange).toString()}"
        binding.txtNotReturnedIncome.text = "${requireContext().getText(R.string.not_returned)}: ${getNotReturnedIncomeAmount(callCardListInDateRange).toString()}"

        setupChart()
    }

    // validate input value, if null or empty return false, if start day is after end day, return false
    private fun validate(): Boolean {
        if (binding.edtAnalyticsFromDay.text.isNullOrBlank() || binding.edtAnalyticsToDay.text.isNullOrBlank()) {
            Toast.makeText(
                requireContext(),
                requireContext().getText(R.string.you_must_enter_all_information),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        if (parseStringToDate(binding.edtAnalyticsFromDay.text.toString())!!.after(
                parseStringToDate(binding.edtAnalyticsToDay.text.toString())
            )
        ) {
            Toast.makeText(
                requireContext(),
                requireContext().getText(R.string.from_day_must_before_to_day),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }

    // get card list in entered date range
    private fun getCallCardListInDateRange(): ArrayList<CallCard> {
        val callCardDB = CallCardDB(Database(requireContext()))

        return callCardDB.getAllCallCards().stream().filter { element ->
            parseStringToDate(element.borrowTime)!!.before(parseStringToDate(binding.edtAnalyticsToDay.text.toString()))
                    &&
                    parseStringToDate(element.borrowTime)!!.after(parseStringToDate(binding.edtAnalyticsFromDay.text.toString()))

        }.toList() as ArrayList<CallCard>
    }

    // get amount with specific card amount
    private fun getAmount(callCardList:ArrayList<CallCard>, numberOfCard:Int):Int {
        var result = 0
        for(i in 0 until numberOfCard) {
            result+= callCardList[i].price.toInt()
        }
        return result
    }

    // get total income amount of all call cards
    private fun getNotReturnedIncomeAmount(callCardList: ArrayList<CallCard>):Int {
        return callCardList.stream().filter { it.isReturned == "0" }.mapToInt { it.price.toInt() }.sum()
    }

    // get total income amount of all call cards which is returned
    private fun getTotalIncomeAmount(callCardList: ArrayList<CallCard>) :Int {
        return callCardList.stream().mapToInt { it.price.toInt() }.sum()
    }

    // get income amount of all call cards which is not returned
    private fun getReturnedIncomeAmount(callCardList: ArrayList<CallCard>):Int {
        return callCardList.stream().filter { it.isReturned != "0" }.mapToInt { it.price.toInt() }.sum()
    }

    // chart drawer
    private fun setupChart() {
        var lineList = ArrayList<Entry>()
        val numberOfCard = getCallCardListInDateRange().size
        callCardInDateRange = getCallCardListInDateRange()

        for (i in 0..numberOfCard) {
            lineList.add(Entry(i.toFloat(), getAmount(callCardInDateRange, i).toFloat()))

        }

        val lineDataSet = LineDataSet(lineList, "Doanh thu")
        lineDataSet.apply {
            setDrawFilled(true)
            valueTextColor = Color.BLUE
            valueTextSize = 12f
            color = Color.BLACK
        }

        val dataSet:ArrayList<ILineDataSet> = ArrayList()
        dataSet.add(lineDataSet)
        val lineData = LineData(dataSet)
        binding.chartLineChart.apply {
            data = lineData
            invalidate()
        }



    }
}