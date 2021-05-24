package com.furqoncreative.moviejetpack.ui.movie.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.furqoncreative.moviejetpack.data.entity.favorite.FavoriteEntity
import com.furqoncreative.moviejetpack.data.entity.movie.MovieEntity
import com.furqoncreative.moviejetpack.data.local.DataDummy
import com.furqoncreative.moviejetpack.data.repository.AppRepository
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {
    private lateinit var viewModel: MovieDetailViewModel
    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val movieId = dummyMovie.id
    private val dummyFavorite = DataDummy.generateDummyFavorite()[0]
    private val favoriteId = dummyFavorite.id ?: 460465

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: AppRepository

    @Mock
    private lateinit var observer: Observer<MovieEntity>

    @Mock
    private lateinit var observerFavorite: Observer<FavoriteEntity>

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(repository)
    }

    @Test
    fun testGetMovie() {
        val movie = MutableLiveData<MovieEntity>()
        movie.value = dummyMovie

        Mockito.`when`(repository.getMovie(movieId)).thenReturn(movie)
        val movieEntity = movieId?.let { viewModel.getMovie(it).value } as MovieEntity
        verify(repository).getMovie(movieId)

        assertNotNull(movieEntity)
        assertEquals(dummyMovie.id, movieEntity.id)
        assertEquals(dummyMovie.backdropPath, movieEntity.backdropPath)
        assertEquals(dummyMovie.title, movieEntity.title)
        assertEquals(dummyMovie.voteAverage, movieEntity.voteAverage, 0.0)
        assertEquals(dummyMovie.releaseDate, movieEntity.releaseDate)
        assertEquals(dummyMovie.overview, movieEntity.overview)

        viewModel.getMovie(movieId).observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }

    @Test
    fun testGetMovieFavoriteById() {
        val favorite = MutableLiveData<FavoriteEntity>()
        favorite.value = dummyFavorite

        Mockito.`when`(favoriteId.let { repository.getFavoriteById(it) }).thenReturn(favorite)
        val favoriteEntity =
            favoriteId.let { viewModel.getFavoriteById(it).value } as FavoriteEntity
        verify(repository).getFavoriteById(favoriteId)

        assertNotNull(favoriteEntity)
        assertEquals(dummyFavorite.id, favoriteEntity.id)
        assertEquals(dummyFavorite.title, favoriteEntity.title)
        assertEquals(dummyFavorite.category, favoriteEntity.category)
        assertEquals(dummyFavorite.posterPath, favoriteEntity.posterPath)
        assertEquals(dummyFavorite.voteAverage, favoriteEntity.voteAverage, 0.0)
        assertEquals(dummyFavorite.releaseDate, favoriteEntity.releaseDate)

        if (favoriteId != null) {
            viewModel.getFavoriteById(favoriteId).observeForever(observerFavorite)
        }
        verify(observerFavorite).onChanged(dummyFavorite)
    }

    @Test
    fun testInsertMovieFavorite() {
        viewModel.insertFavorite(dummyFavorite)
        verify(repository, times(1)).insertFavorite(dummyFavorite)
    }

    @Test
    fun testDeleteMovieFavorite() {
        viewModel.deleteFavorite(favoriteId)
        verify(repository, times(1)).deleteFavorite(favoriteId)
    }
}