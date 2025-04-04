package com.ezamora.testandroid.ui.recomended

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezamora.testandroid.data.db.recomended_movie.RecomendedMovie
import com.ezamora.testandroid.domain.use_cases.RecomendedUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecomendedViewModel @Inject constructor(
    private val recomendedUseCases: RecomendedUseCases
) : ViewModel() {
    private var _topRated = MutableLiveData<Result<List<RecomendedMovie>>>()
    val topRated : LiveData<Result<List<RecomendedMovie>>> get() = _topRated

    val isLoading = MutableLiveData<Boolean>()

    fun getRecomendedMovies() {
        isLoading.value = true
        viewModelScope.launch {
            _topRated.value = recomendedUseCases.getRecomendedMovies()
            isLoading.value = false
        }
    }
}