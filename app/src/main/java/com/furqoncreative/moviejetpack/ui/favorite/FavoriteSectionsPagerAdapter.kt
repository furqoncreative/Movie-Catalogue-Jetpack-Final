package com.furqoncreative.moviejetpack.ui.favorite

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.furqoncreative.moviejetpack.ui.favorite.movie.FavoriteMovieFragment
import com.furqoncreative.moviejetpack.ui.favorite.tvshow.FavoriteTvShowFragment

class FavoriteSectionsPagerAdapter(
    fa: FragmentActivity
) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteMovieFragment()
            1 -> FavoriteTvShowFragment()
            else -> FavoriteMovieFragment()
        }
    }

}