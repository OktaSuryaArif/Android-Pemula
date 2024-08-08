package com.example.graosabook

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : ComponentActivity() {
    private lateinit var rvBooks: RecyclerView
    private val list = ArrayList<BookDatas>()
    private val title = "GraOSABook"

    private fun setActionBarTitle(title: String) {
        title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBarTitle(title)

        rvBooks = findViewById(R.id.rv_heroes)
        rvBooks.setHasFixedSize(true)

        list.addAll(getListBooks())
        showRecyclerCardView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        when (item.itemId) {
            R.id.action_list -> {
                rvBooks.layoutManager = LinearLayoutManager(this)
            }
            R.id.action_grid -> {
                rvBooks.layoutManager = GridLayoutManager(this, 2)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getListBooks(): ArrayList<BookDatas> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataPrice = resources.getStringArray(R.array.data_price)
        val dataStore = resources.getStringArray(R.array.data_store)
        val dataStorePhoto = resources.obtainTypedArray(R.array.data_store_photo)
        val dataLink = resources.getStringArray(R.array.data_link)
        val listBook = ArrayList<BookDatas>()
        for (i in dataName.indices) {
            val book = BookDatas(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1), dataPrice[i], dataStore[i], dataStorePhoto.getResourceId(i, -1), dataLink[i])
            listBook.add(book)
        }
        return listBook
    }


    private fun showRecyclerCardView() {
        rvBooks.layoutManager = LinearLayoutManager(this)
        val cardViewArticleAdapter = ListBookAdapter(list)
        rvBooks.adapter = cardViewArticleAdapter

        cardViewArticleAdapter.setOnItemClickCallback(object : ListBookAdapter.OnItemClickCallback {
            override fun onItemClicked(data: BookDatas) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.EXTRA_BOOK_DATA, data)
                startActivity(intentToDetail)
            }
        })
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {

            R.id.about_page -> {
                val aboutIntent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(aboutIntent)
            }
        }
    }

    private fun showSelectedBook(book: BookDatas) {
        Toast.makeText(this, "Kamu memilih " + book.name, Toast.LENGTH_SHORT).show()
    }
}
