package org.wit.festival.models

interface FestivalStore {
    fun findAll(): List<FestivalModel>
   // fun findOne(): List<FestivalModel>
    fun create(festival: FestivalModel)
    fun update(festival: FestivalModel)
    fun delete(festival: FestivalModel)

}