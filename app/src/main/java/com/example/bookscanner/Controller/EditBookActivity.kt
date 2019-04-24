package com.example.bookscanner.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.bookscanner.Model.Book
import com.example.bookscanner.R
import com.example.bookscanner.Services.DataBaseHelper

class EditBookActivity : AppCompatActivity() {
    private var update: Boolean = false
    private var title: String? = null
    private var author: String? = null
    private var id:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_book_activity)

        getBookData()
    }

    private fun getBookData() {
        val bundle = intent.extras
        id = bundle?.getString("id")
        title = bundle?.getString("title")
        author = bundle?.getString("author")

        if (title != null) {
            findViewById<EditText>(R.id.titleText).setText(title)
            update = true
        }

        if (author != null)
            findViewById<EditText>(R.id.authorText).setText(author)
    }

    fun acceptButtonClicked(view: View) {
        val dbHandler = DataBaseHelper(this, null)

        if (update) updateBook(dbHandler) else
            insertBook(dbHandler)

        finish()
    }

    private fun insertBook(dbHandler: DataBaseHelper) {
        val title = findViewById<EditText>(R.id.titleText).text.toString()
        val author = findViewById<EditText>(R.id.authorText).text.toString()
        val book = Book(title, author)
        dbHandler.insert(book)
        Toast.makeText(this, "Added to your shelf", Toast.LENGTH_LONG).show()
    }

    private fun updateBook(dbHandler: DataBaseHelper) {
        val title = findViewById<EditText>(R.id.titleText).text.toString()
        val author = findViewById<EditText>(R.id.authorText).text.toString()
        val book = Book(id!!, title, author, null, null)
        dbHandler.update(book)
    }

    fun backBtnClicked(view: View) {
        finish()
    }
}
