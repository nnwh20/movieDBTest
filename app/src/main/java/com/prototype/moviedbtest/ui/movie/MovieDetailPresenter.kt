package com.prototype.moviedbtest.ui.movie

import android.util.Log
import com.prototype.moviedbtest.BuildConfig
import com.prototype.moviedbtest.data.database.dao.MovieFavorites
import com.prototype.moviedbtest.data.database.dao.MovieFavoritesRepository
import com.prototype.moviedbtest.data.network.api.ApiServiceInterface
import com.prototype.moviedbtest.data.network.model.serverResponse.Movie
import com.prototype.moviedbtest.util.CoroutinesProvider
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Created by Nanang Wahyu H on 21/06/20.
 */
class MovieDetailPresenter @Inject constructor() : MovieDetailContract.Presenter {

    private var view: MovieDetailContract.View? = null

    @Inject
    lateinit var api: ApiServiceInterface

    @Inject
    lateinit var coroutinesProvider: CoroutinesProvider

    @Inject
    lateinit var movieFavoritesRepository: MovieFavoritesRepository

    override fun toggleFavorite(isFavorites: Boolean, movie: Movie) {
        val job = CoroutineScope(coroutinesProvider.uiContext).launch {
            try {
                if(isFavorites){
                    withContext(coroutinesProvider.bgContext) {
                        movieFavoritesRepository.deleteByIdMovie(movie.id)
                    }?.subscribe {
                        view?.onToggleFavoriteFinish(it)
                    }
                }else{
                    withContext(coroutinesProvider.bgContext) {
                        val movieFavorites = MovieFavorites(movie.id, movie.title, movie.releaseDate, movie.overview, movie.posterPath)
                        movieFavoritesRepository.insertMovie(movieFavorites)
                    }?.subscribe {
                        view?.onToggleFavoriteFinish(it)
                    }
                }

            } catch (e: Exception) {
                if (e !is CancellationException)
                    view?.onToggleFavoriteFinish(false)
            }
        }
    }

    override fun getMovieDetail(movieId: Long) {
        val job = CoroutineScope(coroutinesProvider.uiContext).launch {
            try {
                view?.showLoading()

                val movie = api.getMovieDetail(movieId, BuildConfig.API_KEY)
                view?.showMovieDetail(movie)

                val reviews = api.getMovieReviews(movieId, BuildConfig.API_KEY)
                view?.showMovieReview(reviews)

                val result = withContext(coroutinesProvider.bgContext) {
                    movieFavoritesRepository.isMovieFavoriteAvailable(movieId)
                }.subscribe {
                    view?.onMovieStatus(!it)
                }

                view?.hideLoading()
            } catch (e: Exception) {
                if (e !is CancellationException)
                    Log.e("ERROR", e.toString())
            } finally {
                view?.hideLoading()
            }
        }
    }

    override fun attachView(view: MovieDetailContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}