package com.furqoncreative.moviejetpack.ui.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.furqoncreative.moviejetpack.data.entity.favorite.FavoriteEntity
import com.furqoncreative.moviejetpack.data.entity.movie.MovieEntity
import com.furqoncreative.moviejetpack.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    fun getFavoriteById(id: Int) = repository.getFavoriteById(id)

    fun insertFavorite(favorite: FavoriteEntity) = repository.insertFavorite(favorite)

    fun deleteFavorite(id: Int) = repository.deleteFavorite(id)

    fun getMovie(id: Int): LiveData<MovieEntity> = repository.getMovie(id)

}