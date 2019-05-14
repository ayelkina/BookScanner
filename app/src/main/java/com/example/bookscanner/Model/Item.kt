package com.example.bookscanner.Model

class Item (val book: Book) {
    var buttonClicked: Boolean = false
    var itemClicked: Boolean = false
    var foundDescription: Boolean = false
    var description: String = ""
}