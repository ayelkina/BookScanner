package com.example.bookscanner.Services

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.bookscanner.Model.Book

class DataBaseHelper (context: Context, factory: SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
       val  CREATE_BOOKS_TABLE = ("CREATE TABLE " +
               TABLE_BOOK + " ( " +
               ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,  " +
               TITLE + " TEXT, " +
               AUTHOR + " TEXT, " +
               RATING + " TEXT, " +
               API_ID + " TEXT " + ")"
               )
        db?.execSQL(CREATE_BOOKS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       db?.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK)
    }

    fun insert(book: Book) {
        val values = ContentValues()
        values.put(TITLE, book.title)
        values.put(AUTHOR, book.author)
        values.put(RATING, book.rating)
        values.put(API_ID, book.apiId)

        val db = this.writableDatabase
        db.insert(TABLE_BOOK, null, values)
        db.close()
    }

    fun update(book: Book) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TITLE, book.title)
        values.put(AUTHOR, book.author)
        db.update(TABLE_BOOK, values, ID + "=?", arrayOf(book.dbId)).toLong()
        db.close()
    }

    fun delete(book: Book) {
        val db = this.writableDatabase
        val values = ContentValues()
        db.delete(TABLE_BOOK, ID + "=?", arrayOf(book.dbId)).toLong()
        db.close()
    }

    fun create() {
        val db = this.writableDatabase
        val  CREATE_BOOKS_TABLE = ("CREATE TABLE " +
                TABLE_BOOK + " ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT  ,  " +
                TITLE + " TEXT, " +
                AUTHOR + " TEXT, " +
                RATING + " TEXT, " +
                API_ID + " TEXT " + ")"
                )
        db?.execSQL(CREATE_BOOKS_TABLE)
    }

    fun drop() {
        val db = this.writableDatabase
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK)
    }

    fun getAllBooks(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_BOOK", null)
    }

    companion object {
        private val DATABASE_NAME = "LIBRARY.db"
        private val DATABASE_VERSION = 3
        val TABLE_BOOK = "book"
        val ID = "apiId"
        val TITLE = "title"
        val AUTHOR = "author"
        val RATING = "rating"
        val API_ID = "api_id"
    }
}