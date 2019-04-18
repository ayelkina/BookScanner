package com.example.bookscanner.Services

import com.example.bookscanner.Model.Book
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class Connector {

    private val searchBookUrl = "https://www.goodreads.com/search/index.xml"
    private val bookInfoUrl = "https://www.goodreads.com/book/show/"
    private val key = "?key=35Arndk48sAisvbD0kXcQ"


    fun getListOfBooks(request: List<String>): MutableList<Book>? {
        val url = StringBuilder(searchBookUrl)
        url.append(key).append("&q=")
        val listLength = request.size - 1

        url.append(request[0])
        for (i in 1..listLength) {
            url.append("-")
            url.append(request[i])
        }

        val urlString = url.toString()
        return getBooksFromUrl(urlString)
    }

    private fun getBooksFromUrl(urlString: String): MutableList<Book>? {
        val stream = downloadUrl(urlString)
        return if (stream != null) parseBooksListStream(stream) else null
    }

    private fun downloadUrl(urlString: String): InputStream? {
        val url = URL(urlString)
        return (url.openConnection() as? HttpURLConnection)?.run {
            readTimeout = 10000
            connectTimeout = 15000
            requestMethod = "GET"
            doInput = true
            try {
                connect()
                inputStream
            } catch (e: IOException) {
                null
            }
        }
    }

    private fun parseBooksListStream(inputStream: InputStream?): MutableList<Book> {
        var text = ""
        var book: Book? = null
        val foundBooks = mutableListOf<Book>()
        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            parser.setInput(inputStream, null)

            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagname = parser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> if (tagname.equals("work")) {
                        book = Book("","","","")
                        foundBooks.add(book)
                    }

                    XmlPullParser.TEXT -> text = parser.text

                    XmlPullParser.END_TAG -> if (tagname.equals("title")) {
                        book?.title = text
                    } else if (tagname.equals("name")) {
                        book?.author = text
                    } else if (tagname.equals("id")) {
                        if (book?.id.equals(""))
                            book?.id = text
                    } else if (tagname.equals("average_rating")) {
                        book?.rating = text
                    }
                }
                eventType = parser.next()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return foundBooks
    }

    fun getBookInfo(id: String): String? {
        val url = StringBuilder(bookInfoUrl)
        val urlString = url.append(id).append(".xml").append(key).toString()

        val stream = downloadUrl(urlString)
        return parseBookInfo(stream)

    }

    private fun parseBookInfo(inputStream: InputStream?): String? {
        var text = ""
        var description = ""
        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()

            parser.setInput(inputStream, null)
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagname = parser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> if (tagname.equals("description")) {
                        while (eventType != XmlPullParser.TEXT)
                            eventType = parser.next()

                        description = parser.text
                        return description
                    }
                }
                eventType = parser.next()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return description
    }


}

