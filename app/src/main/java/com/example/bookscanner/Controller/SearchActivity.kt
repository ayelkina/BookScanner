package com.example.bookscanner.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import com.example.bookscanner.Adapters.SearchRecyclerAdapter
import com.example.bookscanner.Model.Book
import com.example.bookscanner.R
import com.example.bookscanner.Services.Connector
import com.example.bookscanner.Services.DataBaseHelper
import com.example.bookscanner.Services.OCR

class SearchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

        val bundle = intent.extras
        val request = bundle?.getString("request")
        setSearchViewConfiguration(request)
    }

    private fun setSearchViewConfiguration(request: String?) {
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setIconified(false);
        searchView.setQuery(request, true)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val books = sendGetRequest(query)
                if (books == null || books.size == 0) {
                    setNotFoundPicture()
                    return false
                }

                createRecyclerView(books)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun createRecyclerView(bookList: List<Book>) {
        viewManager = LinearLayoutManager(this)
        viewAdapter = SearchRecyclerAdapter(bookList)

        recyclerView = findViewById<RecyclerView>(R.id.searchList).apply {
            layoutManager = viewManager
            setHasFixedSize(true)
            adapter = viewAdapter
        }

        val dbHandler = DataBaseHelper(this, null)
        (viewAdapter as SearchRecyclerAdapter).onAddBtnClick = { book ->
            if((viewAdapter as SearchRecyclerAdapter).buttonClicked) {
                dbHandler.insert(book)
                Toast.makeText(this, "Added to your shelf", Toast.LENGTH_LONG).show()
            }
            else {
                dbHandler.deleteByTitle(book)
            }
        }
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
