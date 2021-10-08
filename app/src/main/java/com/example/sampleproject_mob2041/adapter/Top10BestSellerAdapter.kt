package com.example.sampleproject_mob2041.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.database.AnalyticsDB
import com.example.sampleproject_mob2041.database.Database
import com.example.sampleproject_mob2041.model.Book
import com.example.sampleproject_mob2041.model.BookAnalytics
import java.util.stream.Collector
import java.util.stream.Collectors

class Top10BestSellerAdapter(private val mContext: Context, var bookList: ArrayList<Book>) :
    RecyclerView.Adapter<Top10BestSellerAdapter.ViewHolder>() {
    private lateinit var analyticsDB: AnalyticsDB
    private lateinit var bookAnalyticsList: ArrayList<BookAnalytics>

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtPosition: TextView = itemView.findViewById(R.id.top10_item_position)
        val txtBookName: TextView = itemView.findViewById(R.id.top10_item_name)
        val txtEarning: TextView = itemView.findViewById(R.id.top10_item_income)
        val txtBorrowedTime:TextView = itemView.findViewById(R.id.top10_item_borrowed_time)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Top10BestSellerAdapter.ViewHolder {
        analyticsDB = AnalyticsDB(Database(mContext))
        bookAnalyticsList = getBookAnalytics()
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.top10_item, parent, false))
    }

    override fun onBindViewHolder(holder: Top10BestSellerAdapter.ViewHolder, position: Int) {
        holder.txtPosition.text = "${position + 1}"
        holder.txtBookName.text = bookAnalyticsList[position].name
        holder.txtBorrowedTime.text = "Số lần mượn: ${bookAnalyticsList[position].borrowedTime.toString()}"
        holder.txtEarning.text = "Tổng doanh thu: ${ bookAnalyticsList[position].totalEarning.toInt().toString()}"
    }

    override fun getItemCount(): Int {
        analyticsDB = AnalyticsDB(Database(mContext))
        bookAnalyticsList = getBookAnalytics()
        return bookAnalyticsList.size
    }

    // using stream api to get list
    private fun getBookAnalytics(): ArrayList<BookAnalytics> {
        val result = ArrayList<BookAnalytics>()
        for (element in bookList) {
            result.add(
                BookAnalytics(
                    element.name,
                    analyticsDB.getBookBorrowedTime(element.name),
                    analyticsDB.getTotalIncomeOfBook(element.name)
                )
            )
        }
        return result.stream().sorted { o1, o2 -> -o1.totalEarning.compareTo(o2.totalEarning) }.filter { element -> element.borrowedTime != 0 }
            .collect(Collectors.toList()) as ArrayList<BookAnalytics>
    }
}