package com.ezamora.testandroid.di

import com.ezamora.testandroid.data.db.popular_movie.PopularMovieDataBase
import com.ezamora.testandroid.data.db.rated_movie.RatedMovieDataBase
import com.ezamora.testandroid.data.db.recomended_movie.RecomendedMovieDatabase
import com.ezamora.testandroid.domain.repository.MoviesRepository
import com.ezamora.testandroid.domain.use_cases.MoviesUseCases
import com.ezamora.testandroid.domain.use_cases.RecomendedUseCases
import com.ezamora.testandroid.domain.use_cases.TopRatedUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun providerMovieDBUseCase(
        moviesRepository: MoviesRepository,
        dbapi: PopularMovieDataBase
    ) : MoviesUseCases {
        return MoviesUseCases(moviesRepository, dbapi)
    }

    @Provides
    fun providerTopRatedDBUseCase(
        moviesRepository: MoviesRepository,
        ratedMovieDB: RatedMovieDataBase
    ) : TopRatedUseCases {
        return TopRatedUseCases(moviesRepository, ratedMovieDB)
    }

    @Provides
    fun providerRecomendedDbUseCase(
        moviesRepository: MoviesRepository,
        recomendedMovieDB: RecomendedMovieDatabase
    ) : RecomendedUseCases {
        return RecomendedUseCases(moviesRepository, recomendedMovieDB)
    }
}