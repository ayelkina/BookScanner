package com.example.bookscanner.Controller

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import com.example.bookscanner.R
import com.example.bookscanner.Services.OCR

class MainActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun searchBtnClicked(view: View) {
        val searchInputText = findViewById<EditText>(R.id.searchInputText)
        val text = searchInputText.text.toString()
        if (text == "") return

        startSearchActivity(text)
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
            val text = getTextFromPhoto(imageBitmap)

            startSearchActivity(text)
        }
    }

    private fun getTextFromPhoto(imageBitmap: Bitmap): String {
        val ocr = OCR()
        val list = ocr.getOcr(imageBitmap)
        if (list == null) return ""

        val text = StringBuilder("")
        val length = if (list.size > 3) 2 else list.size
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
