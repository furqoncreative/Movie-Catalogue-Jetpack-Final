package com.furqoncreative.moviejetpack.ui.main

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.furqoncreative.moviejetpack.R
import com.furqoncreative.moviejetpack.data.local.DataDummy
import com.furqoncreative.moviejetpack.utils.EspressoIdlingResource
import org.hamcrest.Description
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityTest {
    @get:Rule
    var rule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    private val dummyMovies = DataDummy.generateDummyMovies()
    private val dummyTvShows = DataDummy.generateDummyTvShows()
    private val dummyFavorite = DataDummy.generateDummyTvShows()

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResouce)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResouce)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movie))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies.size))
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        onView(withId(R.id.txtTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.txtDate)).check(matches(isDisplayed()))
        onView(withId(R.id.txtRating)).check(matches(isDisplayed()))
        onView(withId(R.id.txtOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.imgBackdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.imgPoster)).check(matches(isDisplayed()))

        onView(withId(R.id.imgBack)).perform(ViewActions.click())
    }

    @Test
    fun loadTvShows() {
        onView(withText("Tv Show")).perform(ViewActions.click())
        onView(withId(R.id.rv_tv_show))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShows.size))
    }

    @Test
    fun loadDetailTvShow() {
        onView(withText("TV SHOW")).perform(ViewActions.click())
        onView(withId(R.id.rv_tv_show)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        onView(withId(R.id.txtTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.txtDate)).check(matches(isDisplayed()))
        onView(withId(R.id.txtRating)).check(matches(isDisplayed()))
        onView(withId(R.id.txtOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.imgBackdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.imgPoster)).check(matches(isDisplayed()))

        onView(withId(R.id.imgBack)).perform(ViewActions.click())
    }

    @Test
    fun loadFavorites() {
        onView(withId(R.id.action_favorite)).perform(ViewActions.click())
        if (getCountFromRecyclerView(R.id.rv_movie) > 0) {
            onView(withId(R.id.rv_movie))
                .check(matches(isDisplayed()))
            onView(withId(R.id.rv_movie))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyFavorite.size))
            loadDetailMovie()
        }

        onView(withText("TV SHOW")).perform(ViewActions.click())
        if (getCountFromRecyclerView(R.id.rv_tv_show) > 0) {
            onView(withId(R.id.rv_tv_show))
                .check(matches(isDisplayed()))
            onView(withId(R.id.rv_tv_show))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyFavorite.size))
            loadDetailTvShow()
        }
    }


    private fun getCountFromRecyclerView(@IdRes RecyclerViewId: Int): Int {
        var count = 0
        val matcher: TypeSafeMatcher<View?> = object : TypeSafeMatcher<View?>() {
            override fun matchesSafely(item: View?): Boolean {
                count = (item as RecyclerView).adapter?.itemCount ?: 0
                return true
            }

            override fun describeTo(description: Description?) {

            }
        }
        onView(allOf(withId(RecyclerViewId), isDisplayed())).check(matches(matcher))
        val result = count
        count = 0
        return result
    }
}