package com.ezamora.testandroid.data.models

data class TopRatedModel (
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

data class Movie(
    val id: Int,
    val title: String,
    val original_title: String,
    val release_date: String,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String,
    val vote_average: Double,
    val vote_count: Int,
    val popularity: Double,
    val genre_ids: List<Int>
)

