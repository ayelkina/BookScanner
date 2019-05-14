package com.example.bookscanner.Controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.bookscanner.Adapters.LibraryRecyclerAdapter
import com.example.bookscanner.Model.Book
import com.example.bookscanner.R
import com.example.bookscanner.Services.DataBaseHelper

class LibraryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var dbHandler: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.library_activity)
        dbHandler = DataBaseHelper(this, null)

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val addBookIntent = Intent(this, EditBookActivity::class.java)
            startActivity(addBookIntent)
        }

        createRecyclerView()
    }

    override fun onRestart() {
        super.onRestart()
        createRecyclerView()
    }

    private fun getBooksFromDB(): MutableList<Book> {
        val cursor = dbHandler.getAllBooks()
        cursor!!.moveToFirst()
        val allBooks = mutableListOf<Book>()
        while (cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndex(DataBaseHelper.ID))
            val title = cursor.getString(cursor.getColumnIndex(DataBaseHelper.TITLE))
            val author = cursor.getString(cursor.getColumnIndex(DataBaseHelper.AUTHOR))
            allBooks.add(Book(id, title, author, null, ""))
        }
        return allBooks
    }

    private fun createRecyclerView() {
        val booksList = getBooksFromDB()
        viewManager = LinearLayoutManager(this)
        viewAdapter = LibraryRecyclerAdapter(booksList)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = viewManager
            setHasFixedSize(true)
            adapter = viewAdapter
        }

        (viewAdapter as LibraryRecyclerAdapter).onClickListener = { book ->
            updateBook(book)
        }

        (viewAdapter as LibraryRecyclerAdapter).onDeleteBtnClick = { book ->
            dbHandler.delete(book)
            createRecyclerView()
        }
    }

    fun backBtnClicked(view: View) {
        finish()
    }

    fun updateBook(book: Book) {
        val addBookIntent = Intent(this, EditBookActivity::class.java)
        addBookIntent.putExtra("id", book.dbId)
        addBookIntent.putExtra("title", book.title)
        addBookIntent.putExtra("author", book.author)
        addBookIntent.putExtra("edit", true)

        startActivity(addBookIntent)
    }

}
