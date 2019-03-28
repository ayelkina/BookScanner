package com.example.bookscanner

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.bookscanner.View.LibraryActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)

//        val toggle = ActionBarDrawerToggle(
//            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
//        )
//        drawer_layout.addDrawerListener(toggle)
//        toggle.syncState()

    }

//    override fun onBackPressed() {
//        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
//            drawer_layout.closeDrawer(GravityCompat.START)
//        } else {
//            super.onBackPressed()
//        }
//    }


    fun searchBtnClicked (view: View) {

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

//    private var myAdapter: MyAdapter? = null
//    private var myCompositeDisposable: CompositeDisposable? = null
//    private var myRetroCryptoArrayList: ArrayList<RetroCrypto>? = null
//    private val BASE_URL = "https://api.nomics.com/v1/"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        myCompositeDisposable = CompositeDisposable()
//        initRecyclerView()
//        loadData()
//
//    }
//
////Initialise the RecyclerView//
//
//    private fun initRecyclerView() {
//
////Use a layout manager to position your items to look like a standard ListView//
//
//        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
//        cryptocurrency_list.layoutManager = layoutManager
//
//    }
//
////Implement loadData//
//
//    private fun loadData() {
//
////Define the Retrofit request//
//
//        val requestInterface = Retrofit.Builder()
//
////Set the API’s base URL//
//
//            .baseUrl(BASE_URL)
//
////Specify the converter factory to use for serialization and deserialization//
//
//            .addConverterFactory(GsonConverterFactory.create())
//
////Add a call adapter factory to support RxJava return types//
//
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//
////Build the Retrofit instance//
//
//            .build().create(GetData::class.java)
//
////Add all RxJava disposables to a CompositeDisposable//
//
//        myCompositeDisposable?.add(requestInterface.getData()
//
////Send the Observable’s notifications to the main UI thread//
//
//            .observeOn(AndroidSchedulers.mainThread())
//
////Subscribe to the Observer away from the main UI thread//
//
//            .subscribeOn(Schedulers.io())
//            .subscribe(this::handleResponse))
//
//    }
//
//    private fun handleResponse(cryptoList: List<RetroCrypto>) {
//
//        myRetroCryptoArrayList = ArrayList(cryptoList)
//        myAdapter = MyAdapter(myRetroCryptoArrayList!!, this)
//
////Set the adapter//
//
//        cryptocurrency_list.adapter = myAdapter
//
//    }
//
//    override fun onItemClick(book: Book) {
//
////If the user clicks on an item, then display a Toast//
//
//        Toast.makeText(this, "You clicked: ${book.currency}", Toast.LENGTH_LONG).show()
//
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//
////Clear all your disposables//
//
//        myCompositeDisposable?.clear()
//
//    }


}
