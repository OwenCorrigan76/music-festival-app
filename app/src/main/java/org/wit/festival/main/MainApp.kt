package org.wit.festival.main

import android.app.Application
import org.wit.festival.models.FestivalJSONStore
import org.wit.festival.models.FestivalMemStore
import org.wit.festival.models.FestivalStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    // val festivals = ArrayList<FestivalModel>() // arrayList called festivals of type FestivalModel
    //use below when introducing model

    lateinit var festivals: FestivalStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        festivals = FestivalJSONStore(applicationContext)
        i("Festival App Started")

        //  festivals.create(FestivalModel(0, "ATN", "Music Festival", "Waterford"))
        //  festivals.create(FestivalModel(1, "Electric Picnic", "Music Festival", "Stradbally"))
        //  festivals.create(FestivalModel(2, "Body and Soul", "Music Festival", "Westmeath"))
    }
}
