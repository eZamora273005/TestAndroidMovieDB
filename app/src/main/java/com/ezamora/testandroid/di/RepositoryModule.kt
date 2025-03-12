package com.ezamora.testandroid.di

import android.app.Application
import androidx.room.Room
import com.ezamora.testandroid.data.api.MovieDBAPI
import com.ezamora.testandroid.data.db.popular_movie.PopularMovieDataBase
import com.ezamora.testandroid.data.db.rated_movie.RatedMovieDataBase
import com.ezamora.testandroid.data.repository.MoviesRepositoryImpl
import com.ezamora.testandroid.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun providerMoviesRepository(
        movieDBAPI: MovieDBAPI
    ) : MoviesRepository {
        return MoviesRepositoryImpl(movieDBAPI)
    }

    @Provides
    @Singleton
    fun databaseProvider(app: Application): PopularMovieDataBase = Room.databaseBuilder(
        app,
        PopularMovieDataBase::class.java,
        "movie-db"
    ).build()

    @Provides
    @Singleton
    fun databaseRatedProvider(app: Application): RatedMovieDataBase = Room.databaseBuilder(
        app,
        RatedMovieDataBase::class.java,
        "rated-movie-db"
    ).build()
}