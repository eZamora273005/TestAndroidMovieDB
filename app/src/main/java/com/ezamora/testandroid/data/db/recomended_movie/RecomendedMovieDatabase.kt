package com.ezamora.testandroid.data.db.recomended_movie

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecomendedMovie::class], version = 1)
abstract class RecomendedMovieDatabase : RoomDatabase() {
    abstract fun recomendedMovieDao() : RecomendedMovieDao
}