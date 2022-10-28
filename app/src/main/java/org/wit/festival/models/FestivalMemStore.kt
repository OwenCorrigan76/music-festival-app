package org.wit.festival.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class FestivalMemStore : FestivalStore {

    val festivals = ArrayList<FestivalModel>()

    override fun findAll(): List<FestivalModel> {
        logAll()
        return festivals
    }

    override fun create(festival: FestivalModel) {
        festival.id = getId()
        festivals.add(festival)
        logAll()
    }

    override fun update(festival: FestivalModel) {
        var foundFestival: FestivalModel? = festivals.find { p -> p.id == festival.id }
        if (foundFestival != null) {
            foundFestival.title = festival.title
            foundFestival.description = festival.description
            foundFestival.location = festival.location
            foundFestival.date = festival.date
            foundFestival.image = festival.image
            foundFestival.lat = festival.lat
            foundFestival.lng = festival.lng
            foundFestival.zoom = festival.zoom
            logAll()
        }
    }

    private fun logAll() {
        festivals.forEach { i("$it") }
    }

    override fun delete(festival: FestivalModel) {
        festivals.remove(festival)
    }
}
