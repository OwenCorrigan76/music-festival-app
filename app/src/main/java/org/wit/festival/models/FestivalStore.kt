package org.wit.festival.models

interface FestivalStore {
    fun findAll(): List<FestivalModel>
    fun create(placemark: FestivalModel)
    fun update(placemark: FestivalModel)

}