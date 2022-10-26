package org.wit.festival.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.festival.R
import org.wit.festival.adapters.FestivalAdapter
import org.wit.festival.adapters.FestivalListener
import org.wit.festival.databinding.ActivityFestivalListBinding
import org.wit.festival.main.MainApp
import org.wit.festival.models.FestivalModel


class FestivalListActivity : AppCompatActivity(), FestivalListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityFestivalListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFestivalListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        loadFestivals()
        registerRefreshCallback()
        registerMapCallback()

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, FestivalActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent) // refresh page with new content
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, FestivalMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onFestivalClick(festival: FestivalModel) {
        val launcherIntent = Intent(this, FestivalActivity::class.java)
        launcherIntent.putExtra("festival_edit", festival)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadFestivals() }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
    private fun loadFestivals() {
        showFestivals(app.festivals.findAll())
    }

    fun showFestivals (festivals: List<FestivalModel>) {
        binding.recyclerView.adapter = FestivalAdapter(festivals, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}