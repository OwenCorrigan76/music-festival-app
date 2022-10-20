package org.wit.festival.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import org.wit.festival.R
import org.wit.festival.databinding.ActivityDateBinding
import org.wit.festival.main.MainApp
import org.wit.festival.models.FestivalModel
import java.util.*

class FestivalDates : AppCompatActivity() {
    private lateinit var dateIntentLauncher: ActivityResultLauncher<Intent> // initialise
    private lateinit var binding: ActivityDateBinding
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        val picker = findViewById<DatePicker>(R.id.datePicker)
        val today = Calendar.getInstance()
        picker.init(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            val toast = "You have picked : $day/$month/$year"
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
        }
    }

    fun onDateClick(festival: FestivalModel) {
        val launcherIntent = Intent(this, FestivalActivity::class.java)
        launcherIntent.putExtra("date_edit", festival)
        refreshIntentLauncher.launch(launcherIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_festival, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                val launcherIntent = Intent(this, FestivalListActivity::class.java)
                startActivityForResult(launcherIntent, 0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}