
package org.wit.festival.models

/*
import android.system.Os.read
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.Streams.write
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.nio.file.Files.exists
import kotlin.random.Random

val JSON_FILE = "festivals.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<FestivalModel>>() {}.type

*/
/*fun generateRandomId(): Long {
    return Random().nextLong()
}*//*


class FestivalJSONStore : FestivalStore {

    var festivals = mutableListOf<FestivalModel>()

     init {
         if (exists(JSON_FILE)) {
             deserialize()
         }
     }
    override fun findAll(): List<FestivalModel> {
        return festivals
    }

    override fun create(festival: FestivalModel) {
        festival.id = getId()
        festivals.add(festival)
           serialize()
        logAll()
    }

    override fun update(festival: FestivalModel) {
        var foundFestival: FestivalModel? = festivals.find { p -> p.id == festival.id }
        if (foundFestival != null) {
            foundFestival.title = festival.title
            foundFestival.description = festival.description
            foundFestival.location = festival.location
            foundFestival.image = festival.image
            logAll()
        }
          serialize()
    }

    private fun logAll() {
        festivals.forEach { Timber.i("$it") }
    }
    private fun serialize() {
        val jsonString = gsonBuilder.toJson(festivals, listType)
        write(JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(JSON_FILE)
        festivals = Gson().fromJson(jsonString, listType)
    }
}
*/
