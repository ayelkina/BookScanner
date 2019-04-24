package com.example.bookscanner.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.bookscanner.Model.Book
import com.example.bookscanner.R

class LibraryRecyclerAdapter(val books: List<Book>) : RecyclerView.Adapter<LibraryRecyclerAdapter.Holder>() {

    var onDeleteBtnClick: ((Book) -> Unit)? = null
    var onClickListener: ((Book) -> Unit)? = null

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookTitle = itemView.findViewById<TextView>(R.id.bookTitle)
        val bookAuthor = itemView.findViewById<TextView>(R.id.bookAuthor)
        val bookRanking = itemView.findViewById<TextView>(R.id.bookRating)

        fun bindCategory(book: Book) {
            bookTitle?.text = book.title
            bookAuthor?.text = book.author
            bookRanking?.text = book.rating

            setClickListeners(book)
        }

        private fun setClickListeners(book: Book) {

            itemView.setOnClickListener {
                onClickListener?.invoke(book)
            }

            val deleteBtn = itemView.findViewById<ImageButton>(R.id.deleteButton)
            deleteBtn.setOnClickListener {
                onDeleteBtnClick?.invoke(book)
            }
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindCategory(books[position])
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.library_layout_item, parent, false)
        return Holder(view)
    }

}
