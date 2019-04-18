package com.example.bookscanner.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.bookscanner.Model.Book

class BookDao {
    private val bookList = mutableListOf<Book>()
    private val books = MutableLiveData<List<Book>>()

    init {
        books.value = bookList
    }

    fun addBook (book: Book) {
        bookList.add(book)
        books.value = bookList
    }

    fun getBooks() = books as LiveData<List<Book>>
}