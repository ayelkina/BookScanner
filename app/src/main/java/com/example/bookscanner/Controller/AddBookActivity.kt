package com.example.bookscanner.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.bookscanner.Model.Book
import com.example.bookscanner.R
import com.example.bookscanner.Services.DataBaseHelper

class AddBookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_book_activity)
    }

    fun addButtonClicked(view: View) {
        val title = findViewById<EditText>(R.id.titleText).text.toString()
        val author = findViewById<EditText>(R.id.authorText).text.toString()

        val book = Book(title, author)
        val dbHandler = DataBaseHelper(this, null)
        dbHandler.addBook(book)

        Toast.makeText(this,  "Added to your shelf", Toast.LENGTH_LONG).show()
        finish()
    }
}
