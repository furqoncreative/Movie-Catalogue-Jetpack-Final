package com.furqoncreative.moviejetpack.ui.favorite.movie

import androidx.lifecycle.ViewModel
import com.furqoncreative.moviejetpack.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    fun getFavoriteMovie() = repository.getFavorite()

}