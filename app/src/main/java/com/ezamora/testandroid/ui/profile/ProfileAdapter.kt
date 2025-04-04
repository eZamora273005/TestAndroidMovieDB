package com.ezamora.testandroid.ui.profile

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ezamora.testandroid.R
import com.ezamora.testandroid.data.db.popular_movie.PopularMovie
import com.ezamora.testandroid.databinding.ItemProfileBinding
import com.ezamora.testandroid.utils.inflate
import com.squareup.picasso.Picasso

class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    var moviesList : List<PopularMovie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = parent.inflate(R.layout.item_profile,  false)
        return ProfileViewHolder(view)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val item = moviesList[position]
        holder.bind(item)
    }

    inner class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemProfileBinding.bind(itemView)
        fun bind(movie : PopularMovie) = with(binding){
            tvTitle.text = movie.title
            tvOverview.text = movie.overview

            Picasso
                .get()
                .load("https://image.tmdb.org/t/p/w185/${movie.posterPath}")
                .into(ivProfile)
        }
    }
}