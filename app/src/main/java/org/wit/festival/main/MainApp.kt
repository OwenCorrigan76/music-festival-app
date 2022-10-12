// for application object

package org.wit.festival.main

import android.app.Application
import org.wit.festival.models.FestivalModel
import org.wit.festival.models.FestivalMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    // val placemarks = ArrayList<PlacemarkModel>() // arrayList called placemarks of type PlacemarkModel
    //use below when introducing model
    val festivals = FestivalMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Festival started")
      //  festivals.create(FestivalModel(0, "ATN", "Music Festival", "Waterford"))
      //  festivals.create(FestivalModel(1, "Electric Picnic", "Music Festival", "Stradbally"))
      //  festivals.create(FestivalModel(2, "Body and Soul", "Music Festival", "Westmeath"))
    }
}
