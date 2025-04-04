package com.ezamora.testandroid.data.db.recomended_movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecomendedMovieDao {
    @Query("SELECT * FROM RecomendedMovie")
    fun getAll(): List<RecomendedMovie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies: List<RecomendedMovie>)

    @Query("SELECT *  FROM RecomendedMovie WHERE id = :id")
    fun getMovieById(id: Int): RecomendedMovie

    @Query("SELECT COUNT(id) FROM RecomendedMovie ")
    fun movieCount() : Int
}