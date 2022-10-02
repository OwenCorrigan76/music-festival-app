package org.wit.placemark.adapters
// gets the data from the model and shows it to the user
// adapters are a good way of connecting a view to a data source
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.placemark.databinding.CardPlacemarkBinding
import org.wit.placemark.models.PlacemarkModel

// this will represent click events on placemark card
interface PlacemarkListener {
    fun onPlacemarkClick(placemark: PlacemarkModel)
}

class PlacemarkAdapter constructor(private var placemarks: List<PlacemarkModel>,
                                   private val listener: PlacemarkListener) :
    RecyclerView.Adapter<PlacemarkAdapter.MainHolder>() {
    // when this is called, based on the amount in getItemCount() below,
    // a new card layout is inflated
    // and the data is binded - see bind function below


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPlacemarkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = placemarks[holder.adapterPosition]
        holder.bind(placemark, listener)
    }
    override fun getItemCount(): Int = placemarks.size

    class MainHolder(private val binding: CardPlacemarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(placemark: PlacemarkModel, listener: PlacemarkListener) {
            // bind title to placemark.title etc
            binding.placemarkTitle.text = placemark.title
            binding.description.text = placemark.description
            binding.location.text = placemark.location
            binding.root.setOnClickListener { listener.onPlacemarkClick(placemark) }

        }

    }
}
