package com.example.bookscanner.Controller

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import com.example.bookscanner.R
import com.example.bookscanner.Services.OCR
import java.io.File
import java.io.FileOutputStream
import java.io.BufferedOutputStream
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSearchViewConfiguration()
    }

    private fun setSearchViewConfiguration() {
        val searchView = findViewById<SearchView>(R.id.searchView)
        val titleText = findViewById<TextView>(R.id.titleText)

        searchView.setOnSearchClickListener {
            titleText.visibility = View.INVISIBLE
            startSearchActivity("")
        }

        searchView.setOnCloseListener {
            titleText.visibility = View.VISIBLE
            false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query == "") return false

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

          try {
              val imageBitmap = data?.extras?.get("data") as Bitmap

              val file = File(this.getCacheDir(), "photo.jpg")
              file.createNewFile()
              val os = BufferedOutputStream(FileOutputStream(file))
              imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
              os.flush()
              os.close()

              val text = getTextFromPhoto(file)
              startSearchActivity(text)
          }catch (e:Exception) {
              e.printStackTrace()
          }
        }
    }

    private fun getTextFromPhoto(file: File): String {
        val ocr = OCR()
        val list = ocr.getOcr(file)
        if (list == null) return ""

        val text = StringBuilder("")
        val length = if (list.size > 4) 1 else list.size-1
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
