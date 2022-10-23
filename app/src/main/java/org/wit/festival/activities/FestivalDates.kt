package org.wit.festival.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.wit.festival.R
import org.wit.festival.databinding.ActivityDateBinding
import org.wit.festival.main.MainApp
import org.wit.festival.models.FestivalModel
import java.util.*

class FestivalDates : AppCompatActivity() {
    private lateinit var binding: ActivityDateBinding
    lateinit var app: MainApp
    var festival = FestivalModel()
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

            /*     binding.datePicker.setOnClickListener {
                val dialog = DatePickerDialog(
                    this,
                    { _, mYear, mMonth, mDay ->
                        val mMonth = mMonth + 1

                        binding.dateView.text = "$mDay/$mMonth/$mYear"
                    },
                    year,
                    month,
                    day
                )
                dialog.show()
            }
            val intent = Intent(this, FestivalListActivity::class.java)
            startActivity(intent)
        }
    }
*/
        }}
    // part of thr navbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_festival, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // if cancel is pressed, return to FestivalListActivity
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