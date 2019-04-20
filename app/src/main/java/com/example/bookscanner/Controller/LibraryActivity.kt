package com.example.bookscanner.Controller

import android.content.Intent
import android.net.sip.SipSession
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import com.example.bookscanner.Adapters.RecyclerAdapter
import com.example.bookscanner.R
import kotlinx.android.synthetic.main.library_activity.*
import com.example.bookscanner.Services.LibraryDataBase

class LibraryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.library_activity)

//        initializeUI()

        viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerAdapter(LibraryDataBase.library)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = viewManager
            setHasFixedSize(true)
            adapter = viewAdapter
            setSupportActionBar(toolbar)

//            addOnItemTouchListener()

            (viewAdapter as RecyclerAdapter).activateButton(false)

        }
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
        finish()
    }

    fun addButtonClicked(view: View) {
        val searchIntent = Intent(this, AddBookActivity::class.java)
        startActivity(searchIntent)
    }

}
