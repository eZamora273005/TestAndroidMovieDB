package com.ezamora.testandroid.data.db.recomended_movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecomendedMovie(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val popularity: Double,
    val voteAverage: Double,
    val favorite: Boolean,
    val movieId: Int
)
