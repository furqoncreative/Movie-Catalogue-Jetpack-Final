package com.furqoncreative.moviejetpack.data.local

import androidx.paging.DataSource
import com.furqoncreative.moviejetpack.data.entity.favorite.FavoriteEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val appDatabase: AppDatabase,
) {

    fun getFavorite(): DataSource.Factory<Int, FavoriteEntity> = appDatabase.favoriteDao().getAllFavorite()

    fun getFavoriteById(id: Int) = appDatabase.favoriteDao().getFavoriteById(id)

    fun insertFavorite(favorite: FavoriteEntity) =
        appDatabase.favoriteDao().insertFavorite(favorite)

    fun deleteFavorite(id: Int) = appDatabase.favoriteDao().deleteFavorite(id)
}