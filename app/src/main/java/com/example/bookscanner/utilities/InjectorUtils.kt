package com.example.bookscanner.utilities

import com.example.bookscanner.data.BookRepository
import com.example.bookscanner.data.DataBase
import com.example.bookscanner.viewModel.LibraryViewModelFactory

object InjectorUtils {

    fun provideLibraryViewModelFactory(): LibraryViewModelFactory {
        val bookRepository = BookRepository.getInstance(DataBase.getInstance().bookDao)
        return LibraryViewModelFactory(bookRepository)
    }
}