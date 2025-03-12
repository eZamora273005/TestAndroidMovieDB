package com.ezamora.testandroid.domain.use_cases

import com.ezamora.testandroid.data.db.rated_movie.RatedMovie
import com.ezamora.testandroid.data.db.rated_movie.RatedMovieDataBase
import com.ezamora.testandroid.domain.repository.MoviesRepository
import com.ezamora.testandroid.utils.transformToRated
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TopRatedUseCases @Inject constructor(
    private val repository: MoviesRepository,
    private val ratedDb: RatedMovieDataBase
) {
    suspend fun getTopRatedMovies(): Result<List<RatedMovie>> = withContext(Dispatchers.IO) {
        with(ratedDb.ratedMovieDao()) {
            if (movieCount() <= 0) {
                val ratedResults = repository.getTopRated()
                if (ratedResults.isSuccess) {
                    val ratedModel = ratedResults.getOrNull()
                    val transformedRated = ratedModel?.results?.map { movie -> movie.transformToRated() }
                    transformedRated?.let { transformed ->
                        if (transformed.isNotEmpty()) {
                            insertMovies(transformed)
                        }
                    }
                } else {
                    return@withContext Result.failure(ratedResults.exceptionOrNull() ?: Exception("Error desconocido"))
                }
            }
            return@withContext Result.success(getAll())
        }
    }
}