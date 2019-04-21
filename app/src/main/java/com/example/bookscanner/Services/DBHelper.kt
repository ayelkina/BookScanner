package com.example.bookscanner.Services

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.bookscanner.Model.Book

class DBHelper (context: Context, factory: SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        println("ON CREATE")

       val  CREATE_BOOKS_TABLE = ("CREATE TABLE " +
               TABLE_BOOK + " ( " +
               COLUMN_TITLE + " TEXT, " +
               COLUMN_AUTHOR + " TEXT, " +
               COLUMN_RATING + " TEXT, " +
               COLUMN_ID + " TEXT " + ")"
               )

        println(CREATE_BOOKS_TABLE)

        db?.execSQL(CREATE_BOOKS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        println("upgrade")
       db?.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK)
    }

    fun addBook(book: Book) {
        println("title ${book.title} author ${book.author} rating ${book.rating} id ${book.id}")
        val values = ContentValues()
        values.put(COLUMN_TITLE, book.title)
        values.put(COLUMN_AUTHOR, book.author)
        values.put(COLUMN_RATING, book.rating)
        values.put(COLUMN_ID, book.id)

        val db = this.writableDatabase


        db.insert(TABLE_BOOK, null, values)
        db.close()
    }

    fun drop() {
        println("drop")

    }

    fun getAllBooks(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_BOOK", null)
    }

    companion object {
        private val DATABASE_NAME = "LIBRARY.db"
        private val DATABASE_VERSION = 2
        val TABLE_BOOK = "book"
        val COLUMN_TITLE = "title"
        val COLUMN_AUTHOR = "author"
        val COLUMN_RATING = "rating"
        val  COLUMN_ID = "id"
    }

    fun create() {
        val  db = this.writableDatabase
        val  CREATE_BOOKS_TABLE = ("CREATE TABLE " +
                TABLE_BOOK + " ( " +
                COLUMN_TITLE + " TEXT " +
                COLUMN_AUTHOR + " TEXT " +
                COLUMN_RATING + " TEXT " +
                COLUMN_ID + " TEXT " + ")"
                )

        println(CREATE_BOOKS_TABLE)

        db?.execSQL(CREATE_BOOKS_TABLE)
    }
}