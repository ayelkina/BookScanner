package com.example.bookscanner.Model

class Book {
    lateinit var dbId: String
    var title: String = ""
    var author: String = ""
    var rating: String? = null
    var apiId: String = ""
    var description: String =""

    constructor(title: String, author: String)  {
        this.title = title
        this.author = author
    }

    constructor()

    constructor(dbId: String, title: String, author: String, rating: String?, id: String) {
        this.dbId = dbId
        this.title = title
        this.author = author
        this.rating = rating
        this.apiId = id
    }
}