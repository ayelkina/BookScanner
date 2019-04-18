package com.example.bookscanner.Controller

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.bookscanner.R

import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.File
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        println("${javaClass.simpleName} onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun searchBtnClicked (view: View) {
        sendGetRequest()
    }

    fun sendGetRequest() :Document {
//            val xmlFile = File("book.xml")

        val xmlFile =  File("https:////www.goodreads.com/search/index.xml?key=35Arndk48sAisvbD0kXcQ&q=Harry&Potter")
//        URL("https://www.goodreads.com/search/index.xml?key=35Arndk48sAisvbD0kXcQ&q=Harry&Potter")

            val dbFactory = DocumentBuilderFactory.newInstance()
            val dBuilder = dbFactory.newDocumentBuilder()
            val xmlInput = InputSource(StringReader(xmlFile.readText()))
            val doc = dBuilder.parse(xmlInput)

            println("ok")
            return doc

    }

    fun cameraBtnClicked(view: View){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    fun libraryClicked (view: View) {
        val libraryIntent = Intent(this, LibraryActivity::class.java)
        startActivity(libraryIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            bookView.setImageBitmap(imageBitmap)
        }
    }

/*    override fun onResume() {
        println("${javaClass.simpleName} OnResume")
        super.onResume()
        setContentView(R.layout.activity_main)
    }

    override fun onRestart() {
        println("${javaClass.simpleName} OnRestart")
        super.onRestart()
        setContentView(R.layout.activity_main)
    }

    override fun onPause() {
        println("${javaClass.simpleName} OnPause")
        super.onPause()

    }

    override fun onStop() {
        println("${javaClass.simpleName} OnStop")
        super.onStop()
    }

    override fun onDestroy() {
        println("${javaClass.simpleName} OnDestroy")
        super.onDestroy()
    }*/

}
