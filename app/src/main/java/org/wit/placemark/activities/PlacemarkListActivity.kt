package org.wit.placemark.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.placemark.R
import org.wit.placemark.adapters.PlacemarkAdapter
import org.wit.placemark.databinding.ActivityPlacemarkListBinding
import org.wit.placemark.main.MainApp

class  PlacemarkListActivity : AppCompatActivity() {

    lateinit var app: MainApp // declare main app, initialised below
    private lateinit var binding: ActivityPlacemarkListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inflate the layout page
        binding = ActivityPlacemarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp // initialise application as app

        // set a linear layout
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        // app.placemarks is where we get the count. Adapter
        // is set to PlacemarkAdapter (below). This line generates the list
        binding.recyclerView.adapter = PlacemarkAdapter(app.placemarks)
    }
    // R relates to the res folder. Below menuInflater inflates the xml file res/menu/menu_main/menu
    // inflate basically means it will be rendered by creating a view object
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // launch the activity PlacemarkActivity. It expects an item id
    override fun onOptionsItemSelected(item: MenuItem): Boolean { // this changes to the add page when clicked
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, PlacemarkActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

