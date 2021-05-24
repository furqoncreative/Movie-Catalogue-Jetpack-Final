package com.furqoncreative.moviejetpack.ui.favorite.tvshow

import androidx.lifecycle.ViewModel
import com.furqoncreative.moviejetpack.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteTvShowViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    fun getFavoriteTvShow() = repository.getFavorite()

}