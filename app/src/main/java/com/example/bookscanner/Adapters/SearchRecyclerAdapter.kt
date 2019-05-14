package com.example.bookscanner.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.bookscanner.Model.Item
import com.example.bookscanner.R
import com.example.bookscanner.Services.Connector

class SearchRecyclerAdapter (val books: List<Item>) : RecyclerView.Adapter<SearchRecyclerAdapter.Holder>() {
    var onAddBtnClick: ((Item) -> Unit)? = null

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val bookTitle = itemView.findViewById<TextView>(R.id.bookTitle)
        val bookAuthor = itemView.findViewById<TextView>(R.id.bookAuthor)
        val bookRanking = itemView.findViewById<TextView>(R.id.bookRating)
        val labelDdescription = itemView.findViewById<TextView>(R.id.labelDescription)
        var bookDdescription = itemView.findViewById<TextView>(R.id.descriptionInfo)

        fun bindCategory(item: Item) {
            bookTitle?.text = item.book.title
            bookAuthor?.text = item.book.author
            bookRanking?.text = item.book.rating
            bookDdescription.text = item.book.description

            labelDdescription.visibility = View.INVISIBLE
            bookDdescription.visibility = View.INVISIBLE

            itemView.setOnClickListener {
                item.itemClicked = !item.itemClicked
                if(item.itemClicked) {
                    labelDdescription.visibility = View.VISIBLE
                    bookDdescription.visibility = View.VISIBLE
                    if(!item.foundDescription) {
                        val description = getDescription(item.book.apiId)
                        item.description = description
                    }


                    item.foundDescription = true
                    bookDdescription.text = item.description

                }
                else {
                    labelDdescription.visibility = View.INVISIBLE
                    bookDdescription.visibility = View.INVISIBLE
                    bookDdescription.text = ""
                }
            }

            val addBtn = itemView.findViewById<ImageButton>(R.id.addBookButton)
            addBtn.setOnClickListener {
                item.buttonClicked = !item.buttonClicked
                if(item.buttonClicked) {
                    addBtn.setBackgroundResource(R.drawable.ic_action_star_active)
                }
                else {
                    addBtn.setBackgroundResource(R.drawable.ic_action_star_border)
                }
                onAddBtnClick?.invoke(item)
            }
        }
    }

    private fun getDescription(id: String): String {
        val connector = Connector()
        val desc = connector.getBookInfo(id)
        return if(desc!=null) desc else ""
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindCategory(books[position])
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_layout_item, parent, false)
        return Holder(view)
    }
}
