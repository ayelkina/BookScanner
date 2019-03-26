package com.example.bookscanner.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.bookscanner.data.BookRepository

class LibraryViewModelFactory(private val bookRepository: BookRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LibraryViewModel(bookRepository) as T
    }
}