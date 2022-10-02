package org.wit.placemark.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.placemark.databinding.CardPlacemarkBinding
import org.wit.placemark.models.PlacemarkModel

class PlacemarkAdapter constructor(private var placemarks: List<PlacemarkModel>) :
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
        holder.bind(placemark)
    }
        override fun getItemCount(): Int = placemarks.size

        class MainHolder(private val binding : CardPlacemarkBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(placemark: PlacemarkModel) {
                // bind title to placemark.title etc
                binding.placemarkTitle.text = placemark.title
                binding.description.text = placemark.description
                binding.location.text = placemark.location
            }
        }
    }
