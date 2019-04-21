package com.example.bookscanner.Model

class Book {
    var title: String = ""
    var author: String = ""
    var rating: String = ""
    var id: String = ""

    constructor(title: String, author: String)  {
        this.title = title
        this.author = author
    }

    constructor()

    constructor(title: String, author: String, rating: String, id: String) {
        this.title = title
        this.author = author
        this.rating = rating
        this.id = id
    }
}