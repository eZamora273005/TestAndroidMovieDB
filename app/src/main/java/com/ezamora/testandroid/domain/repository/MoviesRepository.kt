package com.ezamora.testandroid.domain.repository

import PopularsModel

interface MoviesRepository {
    suspend fun getPopulars(): Result<PopularsModel>
    suspend fun getTopRated() : Result<PopularsModel>
    suspend fun getRecomended() : Result<PopularsModel>
}