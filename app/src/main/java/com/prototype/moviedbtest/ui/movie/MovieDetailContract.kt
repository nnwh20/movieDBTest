package com.prototype.moviedbtest.ui.movie

import com.prototype.moviedbtest.data.network.model.serverResponse.Movie
import com.prototype.moviedbtest.data.network.model.serverResponse.Reviews
import com.prototype.moviedbtest.ui.base.BasePresenter
import com.prototype.moviedbtest.ui.base.BaseView


/**
 * Created by Nanang Wahyu H on 21/06/20.
 */
interface MovieDetailContract {
    interface Presenter : BasePresenter<View> {
        fun toggleFavorite(isFavorites: Boolean, movie: Movie)
        fun getMovieDetail(movieId: Long)
    }

    interface View : BaseView {
        fun onToggleFavoriteFinish(isSuccess: Boolean)
        fun onMovieStatus(isFavorite: Boolean)
        fun showMovieDetail(movie: Movie)
        fun showMovieReview(reviews: Reviews)
    }

}