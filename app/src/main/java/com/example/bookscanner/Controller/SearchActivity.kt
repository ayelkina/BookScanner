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

        createRecyclerView()
    }

    private fun createRecyclerView() {
        val bundle = intent.extras
        val request = bundle?.getString("request")

        val books = sendGetRequest(request)
        if (books == null || books.size == 0) {
            setNotFoundPicture()
            return
        } else println("Found ${books[0].author}")
        viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerAdapter(books)

        recyclerView = findViewById<RecyclerView>(R.id.searchList).apply {
            layoutManager = viewManager
            setHasFixedSize(true)
            adapter = viewAdapter

            (viewAdapter as RecyclerAdapter).activateButton(true)

        }
    }

    private fun setNotFoundPicture() {
        println("Not found")
        return
    }

    fun sendGetRequest(request: String?): List<Book>? {
        if(request == null) return null
        val searchRequest = mutableListOf<String>()

        //TODO dodać parsowanie stringu
        searchRequest.add(request)

        val connector = Connector()
        val listOfBooks = connector.getListOfBooks(searchRequest)

        return listOfBooks
    }
}
