package com.prototype.moviedbtest.ui.movie

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prototype.moviedbtest.R
import com.prototype.moviedbtest.data.network.model.serverResponse.Movie
import com.prototype.moviedbtest.data.network.model.serverResponse.Reviews
import com.prototype.moviedbtest.ui.base.BaseActivity
import com.prototype.moviedbtest.ui.movie.adapter.MovieReviewListAdapter
import com.prototype.moviedbtest.util.CommonUtils
import com.prototype.moviedbtest.util.dp
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_movie_detail.toolbar
import javax.inject.Inject


class MovieDetailActivity : BaseActivity(), MovieDetailContract.View {

    @Inject
    lateinit var presenter: MovieDetailContract.Presenter
    private lateinit var listAdapter: MovieReviewListAdapter
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        presenter.attachView(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {onBackPressed()}

        val bundle = intent.extras
        if (bundle != null) {
            val movieId = bundle.getLong("movie_id")
            presenter.getMovieDetail(movieId)
        }

        checkStatus(isFavorite)
    }

    private fun toggleFavorite(movie: Movie){
        ic_favorites.setOnClickListener {
            presenter.toggleFavorite(isFavorite, movie)
        }
    }

    override fun onToggleFavoriteFinish(isSuccess: Boolean) {
        if(isSuccess){toggleFavorite()}
    }

    private fun toggleFavorite(){
        isFavorite = if(isFavorite){
            ic_favorites.setBackgroundResource(R.drawable.ic_favorite_inactive)
            false
        }else{
            ic_favorites.setBackgroundResource(R.drawable.ic_favorite_active)
            true
        }
    }

    override fun onMovieStatus(isFavorite: Boolean) {
        checkStatus(isFavorite)
        this.isFavorite = isFavorite
    }

    private fun checkStatus(isFavorite: Boolean){
        if(isFavorite){
            ic_favorites.setBackgroundResource(R.drawable.ic_favorite_active)
        }else{
            ic_favorites.setBackgroundResource(R.drawable.ic_favorite_inactive)
        }
    }

    override fun showMovieDetail(movie: Movie) {
        toolbar.title = if (movie.title.isEmpty()) "missing title name" else movie.title
        txt_title.text = if (movie.title.isEmpty()) "missing title name" else movie.title
        val date = CommonUtils.formatStringtoDate(movie.releaseDate, "yyyy-MM-dd")
        date?.let { txt_subtitle.text = if (movie.releaseDate.isEmpty()) "missing release date" else CommonUtils.customFormatDate(
            it.time,
            "dd MMMM yyyy"
        )
        }
        txt_desc.text = if (movie.overview.isEmpty()) "missing overview" else movie.overview
        val posterUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
        CommonUtils.loadImage(this, posterUrl, img_poster, 6.dp)
        toggleFavorite(movie)
    }

    override fun showMovieReview(reviews: Reviews) {
        if(reviews.result.isNullOrEmpty()){
            txt_empty.visibility = View.VISIBLE
        }else{
            txt_empty.visibility = View.GONE
        }
        listAdapter = MovieReviewListAdapter(this, reviews.result)
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(divider)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }
}