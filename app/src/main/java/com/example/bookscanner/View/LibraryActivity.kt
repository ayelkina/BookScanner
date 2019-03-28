package com.example.bookscanner.View

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.bookscanner.MainActivity
import com.example.bookscanner.R
import com.example.bookscanner.utilities.InjectorUtils
import com.example.bookscanner.viewModel.LibraryViewModel
import kotlinx.android.synthetic.main.library_activity.*

class LibraryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.library_activity)
        initializeUI()

        setSupportActionBar(toolbar)
    }

    private fun initializeUI() {
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
    }

    fun backBtnClicked() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }

}
