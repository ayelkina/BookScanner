package com.example.bookscanner.viewModel

import android.arch.lifecycle.ViewModel
import com.example.bookscanner.data.Book
import com.example.bookscanner.data.BookRepository

class LibraryViewModel(private val bookRepository: BookRepository) : ViewModel() {

    fun getBooks() = bookRepository.getBooks()

    fun addBooks(book: Book) = bookRepository.addBook(book)
}
