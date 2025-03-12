package com.ezamora.testandroid.ui.movies

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ezamora.testandroid.R
import com.ezamora.testandroid.data.db.rated_movie.RatedMovie
import com.ezamora.testandroid.databinding.ItemProfileBinding
import com.ezamora.testandroid.utils.inflate
import com.squareup.picasso.Picasso

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    lateinit var moviesList : List<RatedMovie>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = parent.inflate(R.layout.item_profile,  false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = moviesList[position]
        holder.bind(item)
    }

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemProfileBinding.bind(itemView)
        fun bind(movie : RatedMovie) = with(binding){
            tvTitle.text = movie.title
            tvOverview.text = movie.overview

            Picasso
                .get()
                .load("https://image.tmdb.org/t/p/w185/${movie.posterPath}")
                .into(ivProfile)
        }
    }
}