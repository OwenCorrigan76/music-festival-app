// for application object

package org.wit.placemark.main

import android.app.Application
import org.wit.placemark.models.PlacemarkMemStore
import org.wit.placemark.models.PlacemarkModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    // val placemarks = ArrayList<PlacemarkModel>() // arrayList called placemarks of type PlacemarkModel
    //use below when introducing model
    val placemarks = PlacemarkMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Placemark started")
        placemarks.create(PlacemarkModel(0, "ATN", "Music Festival", "Waterford"))
        placemarks.create(PlacemarkModel(1, "Electric Picnic", "Music Festival", "Stradbally"))
        placemarks.create(PlacemarkModel(2, "Body and Soul", "Music Festival", "Westmeath"))
    }
}
