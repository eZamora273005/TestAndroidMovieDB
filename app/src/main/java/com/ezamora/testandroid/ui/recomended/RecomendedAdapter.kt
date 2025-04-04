package com.ezamora.testandroid.ui.recomended

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ezamora.testandroid.R
import com.ezamora.testandroid.data.db.recomended_movie.RecomendedMovie
import com.ezamora.testandroid.databinding.ItemProfileBinding
import com.ezamora.testandroid.utils.inflate
import com.squareup.picasso.Picasso

class RecomendedAdapter : RecyclerView.Adapter<RecomendedAdapter.RecomendedViewHolder>() {

    var recomendedList : List<RecomendedMovie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecomendedViewHolder {
        val view = parent.inflate(R.layout.item_profile,  false)
        return RecomendedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recomendedList.size
    }

    override fun onBindViewHolder(holder: RecomendedViewHolder, position: Int) {
        val item = recomendedList[position]
        holder.bind(item)
    }

    inner class RecomendedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemProfileBinding.bind(itemView)
        fun bind(movie : RecomendedMovie) = with(binding){
            tvTitle.text = movie.title
            tvOverview.text = movie.overview

            Picasso
                .get()
                .load("https://image.tmdb.org/t/p/w185/${movie.posterPath}")
                .into(ivProfile)
        }
    }
}