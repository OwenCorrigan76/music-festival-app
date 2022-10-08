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
    var image: Uri = Uri.EMPTY  // default value is empti Uri
) : Parcelable