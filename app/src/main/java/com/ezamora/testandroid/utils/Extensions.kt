package com.ezamora.testandroid.utils

import Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.ezamora.testandroid.data.db.popular_movie.PopularMovie
import com.ezamora.testandroid.data.db.rated_movie.RatedMovie

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun Movie.transformToPopular(): PopularMovie = PopularMovie(
    0,
    title,
    overview,
    release_date,
    poster_path,
    backdrop_path ?: poster_path,
    original_language,
    original_title,
    popularity,
    vote_average,
    false,
    id
)

fun Movie.transformToRated(): RatedMovie = RatedMovie(
    0,
    title,
    overview,
    release_date,
    poster_path,
    backdrop_path ?: poster_path,
    original_language,
    original_title,
    popularity,
    vote_average,
    false,
    id
)