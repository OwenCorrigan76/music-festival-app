package org.wit.placemark.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.placemark.R
import org.wit.placemark.databinding.ActivityPlacemarkListBinding
import org.wit.placemark.databinding.CardPlacemarkBinding
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.PlacemarkModel

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


    // this is the custom adapter
    class PlacemarkAdapter constructor(private var placemarks: List<PlacemarkModel>) :
        RecyclerView.Adapter<PlacemarkAdapter.MainHolder>() {

        // when this is called, based on the amount in getItemCount() below,
        // a new card layout is inflated
        // and the data is binded - see bind function below

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
            val binding = CardPlacemarkBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return MainHolder(binding)
        }

        override fun onBindViewHolder(holder: MainHolder, position: Int) {
            val placemark = placemarks[holder.adapterPosition]
            holder.bind(placemark)
        }

        override fun getItemCount(): Int = placemarks.size

        // another class

        class MainHolder(private val binding : CardPlacemarkBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(placemark: PlacemarkModel) {
                binding.placemarkTitle.text = placemark.title
                binding.description.text = placemark.description
            }
        }
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

