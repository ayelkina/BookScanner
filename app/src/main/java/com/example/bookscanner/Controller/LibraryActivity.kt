package com.example.bookscanner.Controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.bookscanner.Adapters.RecyclerAdapter
import com.example.bookscanner.Model.Book
import com.example.bookscanner.R
import com.example.bookscanner.Services.DataBaseHelper

class LibraryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.library_activity)
        val allBooks = getBooksFromDB()

        createRecyclerView(allBooks)
    }

    override fun onRestart() {
        super.onRestart()
        val allBooks = getBooksFromDB()

        createRecyclerView(allBooks)
    }

    private fun getBooksFromDB(): MutableList<Book> {
        val dbHandler = DataBaseHelper(this, null)
        val cursor = dbHandler.getAllBooks()
        cursor!!.moveToFirst()
        val allBooks = mutableListOf<Book>()
        while (cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_TITLE))
            val author = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_AUTHOR))
            allBooks.add(Book(title, author))
        }
        return allBooks
    }

    private fun createRecyclerView(booksList: MutableList<Book>) {
        viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerAdapter(booksList)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = viewManager
            setHasFixedSize(true)
            adapter = viewAdapter
            (viewAdapter as RecyclerAdapter).activateButton(false)
        }
    }

    fun backBtnClicked(view: View) {
        finish()
    }

    fun addButtonClicked(view: View) {
        val searchIntent = Intent(this, AddBookActivity::class.java)
        startActivity(searchIntent)
    }

}
