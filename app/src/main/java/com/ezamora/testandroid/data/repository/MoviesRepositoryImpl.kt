package com.ezamora.testandroid.data.repository

import PopularsModel
import com.ezamora.testandroid.data.api.MovieDBAPI
import com.ezamora.testandroid.domain.repository.MoviesRepository
import retrofit2.Response
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val movieDBAPI: MovieDBAPI
) : MoviesRepository {
    override suspend fun getPopulars(): Result<PopularsModel> {
        return safeApiCall { movieDBAPI.getPopulars() }
    }

    override suspend fun getTopRated(): Result<PopularsModel> {
        return safeApiCall { movieDBAPI.getTopRated() }
    }

    override suspend fun getRecomended(): Result<PopularsModel> {
        return safeApiCall { movieDBAPI.getRecomended() }
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}