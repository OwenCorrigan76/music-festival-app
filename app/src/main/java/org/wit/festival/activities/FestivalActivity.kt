package org.wit.festival.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import org.wit.festival.R


import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
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
    var location = Location(52.245696, -7.139102, 15f)
    val IMAGE_REQUEST = 1
  //  val spinner: Spinner = findViewById(R.id.spinner_counties)

// Create an ArrayAdapter using the string array and a default spinner layout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      /*  ArrayAdapter.createFromResource(
            this,
            R.array.counties,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }*/
        var edit = false
        binding = ActivityFestivalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("Festival Activity has started...")

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
        }

        binding.btnAdd.setOnClickListener() {
            festival.title = binding.festivalTitle.text.toString()
            festival.description = binding.description.text.toString()
            festival.county = binding.county.text.toString()
            if (festival.title.isEmpty()) {
                Snackbar.make(it, R.string.enter_festival_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.festivals.update(festival.copy())
                } else {
                    app.festivals.create(festival.copy())
                }
            }
            i("add Button Pressed: $festival")
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        binding.festivalLocation.setOnClickListener { // launch maps and pass location to MapActivity
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_festival, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

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

                        } // end of if
                    }
                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            location = result.data!!.extras?.getParcelable("location")!!
                            i("Location == $location")
                        }
                    }
                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }
}
