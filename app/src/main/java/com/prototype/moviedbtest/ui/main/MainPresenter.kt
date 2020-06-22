package com.prototype.moviedbtest.ui.main

import android.util.Log
import com.prototype.moviedbtest.BuildConfig
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
 * Created by Nanang Wahyu H on 19/06/20.
 */
class MainPresenter @Inject constructor() : MainContract.Presenter {

    private var view: MainContract.View? = null
    @Inject
    lateinit var api: ApiServiceInterface
    @Inject
    lateinit var coroutinesProvider: CoroutinesProvider
    @Inject
    lateinit var movieFavoritesRepository: MovieFavoritesRepository

    override fun showMovieList(category: MainActivity.CATEGORY) {
        val job = CoroutineScope(coroutinesProvider.uiContext).launch {
            try {
                view?.showLoading()
                when(category){
                    MainActivity.CATEGORY.NOW_PLAYING->{
                        val response = api.getNowPlayingMovieList(BuildConfig.API_KEY)
                        view?.showMovieList(response)
                    }
                    MainActivity.CATEGORY.POPULAR->{
                        val response = api.getPopularMovieList(BuildConfig.API_KEY)
                        view?.showMovieList(response)
                    }
                    MainActivity.CATEGORY.TOP_RATED->{
                        val response = api.getTopRatedMovieList(BuildConfig.API_KEY)
                        view?.showMovieList(response)
                    }
                    MainActivity.CATEGORY.UPCOMING->{
                        val response = api.getUpcomingMovieList(BuildConfig.API_KEY)
                        view?.showMovieList(response)
                    }
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

    override fun loadMovieFavoriteList() {
        val job = CoroutineScope(coroutinesProvider.uiContext).launch {
            try {
                val result = withContext(coroutinesProvider.bgContext) {
                    movieFavoritesRepository.loadAllMovie()
                }
                val movies = ArrayList<Movie>()
                for(movieFavorite in result){
                    val movie = Movie(movieFavorite.id, movieFavorite.title, movieFavorite.desc, movieFavorite.releaseDate, movieFavorite.poster)
                    movies.add(movie)
                }
                view?.showMovieFavoriteList(movies)
            } catch (e: Exception) {
                if (e !is CancellationException)
                    Log.e("ERROR", e.toString())
            }
        }
    }

    override fun attachView(view: MainContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}