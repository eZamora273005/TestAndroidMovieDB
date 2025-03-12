package com.ezamora.testandroid.domain.use_cases

import PopularsModel
import com.ezamora.testandroid.data.db.popular_movie.PopularMovie
import com.ezamora.testandroid.data.db.popular_movie.PopularMovieDataBase
import com.ezamora.testandroid.domain.repository.MoviesRepository
import com.ezamora.testandroid.utils.transformToPopular
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesUseCases @Inject constructor(
    private val repository: MoviesRepository,
    private val db: PopularMovieDataBase
){
    suspend fun invoke() : Result<PopularsModel> {
        return repository.getPopulars()
    }

    suspend fun getPopularMovies(): Result<List<PopularMovie>> = withContext(Dispatchers.IO) {
        with(db.movieDao()) {
            if (movieCount() <= 0) {
                val moviesResult = repository.getPopulars()
                if (moviesResult.isSuccess) {
                    val popularsModel = moviesResult.getOrNull()
                    val transformedMovies = popularsModel?.results?.map { movie -> movie.transformToPopular() }
                    transformedMovies?.let { transformed ->
                        if (transformed.isNotEmpty()) {
                            insertMovies(transformed)
                        }
                    }
                } else {
                    return@withContext Result.failure(moviesResult.exceptionOrNull() ?: Exception("Error desconocido"))
                }
            }
            return@withContext Result.success(getAll())
        }
    }
}