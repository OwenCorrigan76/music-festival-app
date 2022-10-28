package org.wit.festival.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FestivalModel(
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var location: String = "",
    var date: String = "",
    var image: Uri = Uri.EMPTY,  // default value is empty Uri
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
) : Parcelable

@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
) : Parcelable


