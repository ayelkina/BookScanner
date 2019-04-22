package com.example.bookscanner.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import com.example.bookscanner.Adapters.RecyclerAdapter
import com.example.bookscanner.Model.Book
import com.example.bookscanner.R
import com.example.bookscanner.Services.Connector

class SearchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

        val books = getBooksFromApi()
        if (books == null || books.size == 0) {
            setNotFoundPicture()
            return
        }
        createRecyclerView(books)
    }

    private fun createRecyclerView(bookList: List<Book>) {
        viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerAdapter(bookList)

        recyclerView = findViewById<RecyclerView>(R.id.searchList).apply {
            layoutManager = viewManager
            setHasFixedSize(true)
            adapter = viewAdapter

            (viewAdapter as RecyclerAdapter).activateButton(false)
        }
    }

    private fun getBooksFromApi(): List<Book>? {
        val bundle = intent.extras
        val request = bundle?.getString("request")
        val books = sendGetRequest(request)

        return books
    }

    private fun setNotFoundPicture() {
        //TODO
        return
    }

    fun sendGetRequest(request: String?): List<Book>? {
        if(request == null) return null
        val searchRequest = mutableListOf<String>()
        searchRequest.add(request)

        val connector = Connector()
        val listOfBooks = connector.getListOfBooks(searchRequest)

        return listOfBooks
    }

    fun backBtnClicked(view: View) {
        finish()
    }
}
