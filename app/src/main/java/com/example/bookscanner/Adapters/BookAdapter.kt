/*
package com.example.bookscanner.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.bookscanner.Model.Book
import com.example.bookscanner.R

class BookAdapter (val context: Context, val books: List<Book>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val bookView: View
        val holder: ViewHolder

        if (convertView == null) {
            bookView = LayoutInflater.from(context).inflate(R.layout.library_activity, null)
            holder = ViewHolder()
            holder.bookTitle = bookView.findViewById(R.id.bookTitle)

        }
    }

    override fun getItem(position: Int): Any {
        return books[position]
    }


    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return books.count()
   }

    private class ViewHolder {
        var bookTitle: TextView? = null
    }

}
*/
