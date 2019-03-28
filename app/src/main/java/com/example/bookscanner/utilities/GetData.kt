package com.example.bookscanner.utilities
import com.example.bookscanner.data.Book
import io.reactivex.Observable
import retrofit2.http.GET

interface GetData {

    @GET("prices?key=YOUR-API-KEY-HERE")
    fun getData() : Observable<List<Book>>
}