package com.example.bookscanner.View

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.bookscanner.R
import com.example.bookscanner.utilities.InjectorUtils
import com.example.bookscanner.viewModel.LibraryViewModel

class LibraryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.library_activity)
        initializeUI()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LibraryFragment.newInstance())
                .commitNow()
        }
    }

    private fun initializeUI() {
        val factory = InjectorUtils.provideLibraryViewModelFactory()
        val viewModel = ViewModelProviders.of(this, factory)
            .get(LibraryViewModel::class.java)

//        viewModel.getBooks().observe
    }

}
