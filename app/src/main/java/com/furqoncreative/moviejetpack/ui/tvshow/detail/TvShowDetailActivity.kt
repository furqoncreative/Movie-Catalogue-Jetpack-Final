package com.furqoncreative.moviejetpack.ui.tvshow.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.furqoncreative.moviejetpack.BuildConfig.BASE_IMAGE_URL
import com.furqoncreative.moviejetpack.R
import com.furqoncreative.moviejetpack.data.entity.favorite.FavoriteEntity
import com.furqoncreative.moviejetpack.databinding.ActivityTvShowDetailBinding
import com.furqoncreative.moviejetpack.ui.movie.detail.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowDetailActivity : AppCompatActivity() {
    companion object{
        const val ID = "ID"
    }

    private val viewModel: TvShowDetailViewModel by viewModels()
    private lateinit var binding: ActivityTvShowDetailBinding
    private var statusFavorite: Boolean? = null
    private var favoriteData = FavoriteEntity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tvShowId = intent?.getIntExtra(MovieDetailActivity.ID, 0)
        tvShowId?.let {
            getTvShow(it)
            checkFavorite(it)
        }
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.imgFavorite.setOnClickListener {
            if (tvShowId != null) {
                if (statusFavorite == true) {
                    setUnfavorite(tvShowId)
                } else {
                    setFavorite(favoriteData)
                }
            }
        }
    }


    private fun getTvShow(id: Int) {
        viewModel.getTvShow(id).observe(this, { item ->
            if (item != null) {
                favoriteData = FavoriteEntity(
                    "TVSHOW",
                    item.name,
                    item.posterPath,
                    item.firstAirDate,
                    item.voteAverage,
                    item.id
                )

                binding.progressBar.visibility = View.GONE
                binding.labelOverview.visibility = View.VISIBLE
                binding.txtRating.visibility = View.VISIBLE

                Glide.with(binding.root)
                    .load(BASE_IMAGE_URL + item.posterPath)
                    .into(binding.imgPoster)

                Glide.with(binding.root)
                    .load(BASE_IMAGE_URL + item.backdropPath)
                    .into(binding.imgBackdrop)

                binding.txtTitle.text = item.name
                binding.txtOverview.text = item.overview
                binding.txtRating.text = item.voteAverage.toString()
                binding.txtDate.text = item.firstAirDate

            }
        })
    }


    private fun checkFavorite(id: Int) {
        viewModel.getFavoriteById(id).observe(this, {
            statusFavorite = it != null
            Log.d("TAG", "checkFavorite: $it")
            Log.d("TAG", "checkFavorite: $statusFavorite")
            if (statusFavorite == true) {
                binding.imgFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_baseline_favorite_24
                    )
                )
            } else {
                binding.imgFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_baseline_favorite_border_24
                    )
                )
            }
        })

    }

    private fun setFavorite(favoriteEntity: FavoriteEntity) {
        viewModel.insertFavorite(favoriteEntity)
        statusFavorite = true
        favoriteEntity.id?.let { checkFavorite(it) }
    }

    private fun setUnfavorite(id: Int) {
        viewModel.deleteFavorite(id)
        statusFavorite = false
        checkFavorite(id)
    }
}