package com.ezamora.testandroid.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezamora.testandroid.data.db.rated_movie.RatedMovie
import com.ezamora.testandroid.domain.use_cases.TopRatedUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val topRatedUseCases: TopRatedUseCases
) : ViewModel()  {

    private var _topRated = MutableLiveData<Result<List<RatedMovie>>>()
    val topRated : LiveData<Result<List<RatedMovie>>> get() = _topRated

    val isLoading = MutableLiveData<Boolean>()

    fun getMoviesTopRated() {
        isLoading.value = true
        viewModelScope.launch {
            _topRated.value = topRatedUseCases.getTopRatedMovies()
            isLoading.value = false
        }
    }

}