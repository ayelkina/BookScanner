package com.example.bookscanner.data

class DataBase private constructor() {

    var bookDao = BookDao()
        private set

    companion object {
        @Volatile private var instance: DataBase? = null

        fun getInstance () =
            instance ?: synchronized(this) {
                instance ?: DataBase().also { instance = it }
        }
    }

}