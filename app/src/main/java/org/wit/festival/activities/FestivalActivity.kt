package org.wit.festival.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.Resources
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import org.wit.festival.R
import org.wit.festival.databinding.ActivityFestivalBinding
import org.wit.festival.helpers.showImagePicker
import org.wit.festival.main.MainApp
import org.wit.festival.models.FestivalModel
import org.wit.festival.models.Location
import timber.log.Timber.i

class FestivalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFestivalBinding
    var festival = FestivalModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent> // initialise
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent> // initialise

    var edit = false

    // variables for datePicker
    val today = Calendar.getInstance()
    val year = today.get(Calendar.YEAR)
    val month = today.get(Calendar.MONTH)
    val day = today.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inflate activity_festival.xml
        binding = ActivityFestivalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        val spinner = findViewById<Spinner>(R.id.countyspinner)
        val county = findViewById<TextView>(R.id.county)
        val res: Resources = resources
        if (spinner != null) {
            val counties = res.getStringArray(R.array.counties_list)
            val arrayAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, counties)
            spinner.adapter = arrayAdapter

            binding.countyspinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        county.text = " ${counties.get(position).toString()}"
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        county.text = "please select a county"
                    }
                }
        }
        // create a spinner for type
        val spinner2 = findViewById<Spinner>(R.id.countyspinner)


        // initialise main app
        app = application as MainApp
        i("Festival Activity has started...")

        // if there is already content - can edit it
        if (intent.hasExtra("festival_edit")) {
            edit = true
            festival = intent.extras?.getParcelable("festival_edit")!!
            binding.festivalTitle.setText(festival.title)
            binding.description.setText(festival.description)
            binding.county.setText(festival.county)
            binding.btnAdd.setText(R.string.save_festival)
            binding.festivalLocation.setOnClickListener {
                i("Set Location Pressed")
            }
            Picasso.get()
                .load(festival.image)
                .into(binding.festivalImage)
            if (festival.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_festival_image)
            }
            binding.dateView.setText(festival.date)
        }

        // add / save festival button
        binding.btnAdd.setOnClickListener() {
            festival.title = binding.festivalTitle.text.toString()
            festival.description = binding.description.text.toString()
            festival.county = binding.county.text.toString()
            festival.date = binding.dateView.text.toString()
            if (festival.title.isEmpty()) {
                Toast.makeText(this, R.string.enter_festival_title, Toast.LENGTH_LONG)
                    .show()
            } else {
                if (edit) { // save
                    app.festivals.update(festival.copy())
                } else { // add
                    app.festivals.create(festival.copy())
                }
            }

            i("add Button Pressed: $festival")
            setResult(RESULT_OK)
            finish()
        }

        // set date datePicker button
        binding.btnDatePicker.setOnClickListener {
            val dialogP = DatePickerDialog(
                this,
                { _, Year, Month, Day ->
                    val Month = Month + 1
                    binding.dateView.setText("$Day/$Month/$Year")
                }, year, month, day
            )
            dialogP.show()
        }
        // displays today's date
        val toast = "Today's Date Is : $day/$month/$year"
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()


        // add an image button
        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        // set location button
        binding.festivalLocation.setOnClickListener { // launch maps and pass location to MapActivity
            val location = Location(52.245696, -7.139102, 15f)
            if (festival.zoom != 0f) {
                location.lat = festival.lat
                location.lng = festival.lng
                location.zoom = festival.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    //Try below
    /*fun DatePicker.getDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        return calendar.time
    }*/

    //  main menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_festival, menu)
        if (edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    // cancel button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
            R.id.item_delete -> {
                app.festivals.delete(festival)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // image callback
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            festival.image = result.data!!.data!!
                            Picasso.get()
                                .load(festival.image)
                                .into(binding.festivalImage)
                            binding.chooseImage.setText(R.string.change_festival_image)
                        }
                    }
                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }

    // map callback
    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location =
                                result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            festival.lat = location.lat
                            festival.lng = location.lng
                            festival.zoom = location.zoom
                        }
                    }
                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }
}
