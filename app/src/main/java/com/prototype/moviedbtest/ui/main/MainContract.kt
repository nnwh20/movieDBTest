package com.prototype.moviedbtest.ui.main

import com.prototype.moviedbtest.data.network.model.serverResponse.Movie
import com.prototype.moviedbtest.data.network.model.serverResponse.Movies
import com.prototype.moviedbtest.ui.base.BasePresenter
import com.prototype.moviedbtest.ui.base.BaseView


/**
 * Created by Nanang Wahyu H on 19/06/20.
 */
interface MainContract {
    interface Presenter : BasePresenter<View> {
        fun showMovieList(category: MainActivity.CATEGORY)
        fun loadMovieFavoriteList()
    }

    interface View : BaseView {
        fun showMovieList(movies: Movies)
        fun showMovieFavoriteList(movies: MutableList<Movie>)
    }

}