package com.example.bookscanner.Controller

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.SearchView
import com.example.bookscanner.R
import com.example.bookscanner.Services.OCR
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSearchViewConfiguration()
    }

    private fun setSearchViewConfiguration() {
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.queryHint = "Search for a book"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query == "") return false
                startSearchActivity(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText == "") return false
                startSearchActivity(newText)
                return false
            }
        })
    }

    override fun onRestart() {
        super.onRestart()
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setQuery("", false)
        searchView.setIconifiedByDefault(true)
        searchView.setFocusable(true);
        searchView.setIconified(true);
    }


    fun libraryClicked(view: View) {
        val libraryIntent = Intent(this, LibraryActivity::class.java)
        startActivity(libraryIntent)
    }

    fun cameraBtnClicked(view: View) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap

            val stream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            val text = getTextFromPhoto(byteArray)
            startSearchActivity(text)
        }
    }

    private fun getTextFromPhoto(imageBitmap: ByteArray): String {
        val ocr = OCR()
        val list = ocr.getOcr(imageBitmap)
        if (list == null) return ""

        val text = StringBuilder("")
        val length = if (list.size > 3) 1 else list.size
        for (i in 0..length)
            text.append(list[i]).append(" ")

        return text.toString()
    }

    private fun startSearchActivity(text: String) {
        val searchIntent = Intent(this, SearchActivity::class.java)
        searchIntent.putExtra("request", text)
        startActivity(searchIntent)
    }
}
