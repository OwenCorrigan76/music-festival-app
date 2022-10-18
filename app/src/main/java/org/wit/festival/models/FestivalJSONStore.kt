package org.wit.festival.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.festival.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "festivals.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<FestivalModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class FestivalJSONStore(private val context: Context) : FestivalStore {

    var festivals = mutableListOf<FestivalModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<FestivalModel> {
        logAll()
        return festivals
    }

   /* override fun findOne(id: Long) : FestivalModel? {
        var foundFestval: FestivalModel? = festivals.find { p -> p.id == id }
        return foundFestval
    }*/
    override fun create(festival: FestivalModel) {
        festival.id = generateRandomId()
        festivals.add(festival)
        serialize()
    }

    override fun update(festival: FestivalModel) {
        val festivalsList = findAll() as ArrayList<FestivalModel>
        val foundFestival: FestivalModel? = festivalsList.find { p -> p.id == festival.id }
        if (foundFestival != null) {
            foundFestival.title = festival.title
            foundFestival.description = festival.description
            foundFestival.county = festival.county
            foundFestival.date = festival.date
            foundFestival.image = festival.image
            foundFestival.lat = festival.lat
            foundFestival.lng = festival.lng
            foundFestival.zoom = festival.zoom
        }
        serialize()
    }

    override fun delete(festival: FestivalModel) {
        festivals.remove(festival)
        serialize()
    }
    private fun serialize() {
        val jsonString = gsonBuilder.toJson(festivals, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        festivals = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        festivals.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}
