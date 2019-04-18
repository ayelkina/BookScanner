package com.example.bookscanner.Controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.bookscanner.R
import kotlinx.android.synthetic.main.library_activity.*
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.bookscanner.Model.Book
import com.example.bookscanner.Services.LibraryDataBase

class LibraryActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    var adapter: ArrayAdapter<Book>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.library_activity)
        setList()
//        initializeUI()

        setSupportActionBar(toolbar)
    }

    fun setList() {
        listView = findViewById<ListView>(R.id.booksList)

        val booksList = LibraryDataBase.library
        val listItems = arrayOfNulls<String>(booksList.size)
        for (i in 0 until booksList.size) {
            val book = booksList[i]
            listItems[i] = book.title

        }

        println(listItems[0])

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        listView.adapter = adapter
    }

    override fun onResume() {
        Log.d("Library", "${javaClass.simpleName} OnResume")
        super.onResume()
        setContentView(R.layout.library_activity)
    }

    override fun onRestart() {
        Log.d("Library", "${javaClass.simpleName} OnRestart")
        super.onRestart()
        setContentView(R.layout.library_activity)
    }


    //NIE USUWAC! DI
    /*private fun initializeUI() {
        val factory = InjectorUtils.provideLibraryViewModelFactory()
        val viewModel = ViewModelProviders.of(this, factory)
            .get(LibraryViewModel::class.java)

        viewModel.getBooks().observe(this, Observer { books ->
            val stringBuilder = StringBuilder()
            books?.forEach { book ->
                stringBuilder.append("$book\n\n")
            }
//            textView_quotes.text = stringBuilder.toString()
        })
    }*/

    fun backBtnClicked(view: View) {
        val mainIntent = Intent(this, MainActivity::class.java)
        finish()
    }

}
