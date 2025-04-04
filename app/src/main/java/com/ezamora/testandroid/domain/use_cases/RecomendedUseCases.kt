package com.ezamora.testandroid.domain.use_cases

import com.ezamora.testandroid.data.db.recomended_movie.RecomendedMovie
import com.ezamora.testandroid.data.db.recomended_movie.RecomendedMovieDatabase
import com.ezamora.testandroid.domain.repository.MoviesRepository
import com.ezamora.testandroid.utils.transformToRecomended
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecomendedUseCases @Inject constructor(
    private val repository: MoviesRepository,
    private val ratedDb: RecomendedMovieDatabase
) {
    suspend fun getRecomendedMovies(): Result<List<RecomendedMovie>> = withContext(Dispatchers.IO) {
        with(ratedDb.recomendedMovieDao()) {
            if (movieCount() <= 0) {
                val recomendedResults = repository.getRecomended()
                if (recomendedResults.isSuccess) {
                    val recomendedModel = recomendedResults.getOrNull()
                    val transformedRecomended = recomendedModel?.results?.map { movie -> movie.transformToRecomended() }
                    transformedRecomended?.let { transformed ->
                        if (transformed.isNotEmpty()) {
                            insertMovies(transformed)
                        }
                    }
                } else {
                    return@withContext Result.failure(recomendedResults.exceptionOrNull() ?: Exception("Error desconocido"))
                }
            }
            return@withContext Result.success(getAll())
        }
    }
}