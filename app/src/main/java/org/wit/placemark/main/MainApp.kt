// for application object

package org.wit.placemark.main

import android.app.Application
import org.wit.placemark.models.PlacemarkModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val placemarks = ArrayList<PlacemarkModel>() // arrayList called placemarks of type PlacemarkModel


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Placemark started")
        placemarks.add(PlacemarkModel("ATN", "Music Festival","Waterford"))
        placemarks.add(PlacemarkModel("Electric Picnic", "Music Festival", "Stradbally"))
        placemarks.add(PlacemarkModel("Body and Soul", "Music Festival", "Westmeath"))
    }
}
