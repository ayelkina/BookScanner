package com.example.bookscanner.data

class BookRepository private constructor(private val bookDao: BookDao) {

    fun addBook(book: Book) {
        bookDao.addBook(book)
    }

    fun getBooks() {
        bookDao.getBooks()
    }

    companion object {
        @Volatile private var instance: BookRepository? = null

        fun getInstance (bookDao: BookDao) =
            instance ?: synchronized(this) {
                instance ?: BookRepository(bookDao).also { instance = it }
        }
    }
}