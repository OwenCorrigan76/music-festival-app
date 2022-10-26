package org.wit.festival.adapters
// gets the data from the model and shows it to the user
// adapters are a good way of connecting a view to a data source
// this adapter turns one card into three (or whatever the amount is)
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.festival.models.FestivalModel
import org.wit.festival.databinding.CardFestivalBinding

// this will represent click events on festival card
interface FestivalListener {
    fun onFestivalClick(festival: FestivalModel)
}

class FestivalAdapter constructor(
    private var festivals: List<FestivalModel>,
    private val listener: FestivalListener
) :
    RecyclerView.Adapter<FestivalAdapter.MainHolder>() {
    // when this is called, based on the amount in getItemCount() below,
    // a new card layout is inflated
    // and the data is binded - see bind function below
    //recycler view allows us to scroll


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardFestivalBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val festival = festivals[holder.adapterPosition]
        holder.bind(festival, listener)
    }

    override fun getItemCount(): Int = festivals.size

    class MainHolder(private val binding: CardFestivalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(festival: FestivalModel, listener: FestivalListener) {
            // bind to the card
            binding.festivalTitle.text = festival.title
            binding.description.text = festival.description
            binding.county.text = festival.county
            binding.dateView.text = festival.date
            Picasso.get().load(festival.image).resize(200, 200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onFestivalClick(festival) }
        }
    }
}

