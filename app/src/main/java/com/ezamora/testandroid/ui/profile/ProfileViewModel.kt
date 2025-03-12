package com.ezamora.testandroid.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezamora.testandroid.data.db.popular_movie.PopularMovie
import com.ezamora.testandroid.domain.use_cases.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val popularUseCase: MoviesUseCases
) : ViewModel() {

    private var _movies = MutableLiveData<Result<List<PopularMovie>>>()
    val movies : LiveData<Result<List<PopularMovie>>> get() = _movies

    val isLoading = MutableLiveData<Boolean>()

    fun getMovies() {
        isLoading.value = true
        viewModelScope.launch {
            _movies.value = popularUseCase.getPopularMovies()
            isLoading.value = false
        }
    }
}