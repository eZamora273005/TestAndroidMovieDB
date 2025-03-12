package com.ezamora.testandroid.data.api

import PopularsModel
import com.ezamora.testandroid.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBAPI {
    @GET("3/movie/popular?language=en-US&page=1")
    suspend fun getPopulars(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ) : Response<PopularsModel>

    @GET("3/movie/top_rated?language=en-US&page=1")
    suspend fun getTopRated(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ) : Response<PopularsModel>

    @GET("movie/now_playing?api_key=TU_API_KEY&language=es-ES&page=1")
    suspend fun getRecomended(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ) : PopularsModel
}