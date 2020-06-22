package com.prototype.moviedbtest.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.prototype.moviedbtest.R
import com.prototype.moviedbtest.data.network.model.serverResponse.Movie
import com.prototype.moviedbtest.data.network.model.serverResponse.Movies
import com.prototype.moviedbtest.ui.base.BaseActivity
import com.prototype.moviedbtest.ui.main.adapter.MovieListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_bottom_sheet_category.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter

    private lateinit var listAdapter: MovieListAdapter
    private var menuItem: MenuItem? = null

    enum class CATEGORY{
        NOW_PLAYING, POPULAR, TOP_RATED, UPCOMING
    }

    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)

        setSupportActionBar(toolbar)

        setUp()

    }

    private fun setUp(){
        val bottomSheetBehavior = BottomSheetBehavior.from(layoutBottomSheet)
        floatingActionButton.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(view: View, state: Int) {
                when (state) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        floatingActionButton.hide()
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        floatingActionButton.show()
                    }
                }
            }

            override fun onSlide(view: View, p1: Float) {
            }
        })

        txt_popular.setOnClickListener {
            presenter.showMovieList(CATEGORY.POPULAR)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        txt_now_playing.setOnClickListener {
            presenter.showMovieList(CATEGORY.NOW_PLAYING)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        txt_top_rated.setOnClickListener {
            presenter.showMovieList(CATEGORY.TOP_RATED)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        txt_upcoming.setOnClickListener {
            presenter.showMovieList(CATEGORY.UPCOMING)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        val bundle = intent.extras
        if (bundle != null) {
            isFavorite = bundle.getBoolean("is_favorite")
            if(isFavorite){
                presenter.loadMovieFavoriteList()
                floatingActionButton.visibility = View.GONE
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = "Favorite Movies"
                toolbar.setNavigationOnClickListener {onBackPressed()}
            }else{
                floatingActionButton.visibility = View.VISIBLE
            }
        }else{
            presenter.showMovieList(CATEGORY.NOW_PLAYING)
        }
    }

    override fun onResume() {
        super.onResume()
        if(isFavorite){
            presenter.loadMovieFavoriteList()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite, menu)
        menuItem = menu?.findItem(R.id.favorite)
        menuItem?.isVisible = !isFavorite
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite) {
            gotoFavorite()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showMovieList(movies: Movies) {
            listAdapter = MovieListAdapter(this, movies.result,
                object : MovieListAdapter.onItemClickListener {
                    override fun itemDetail(movieId: Long) {
                        gotoMovieDetail(movieId)
                    }
                }
            )
            recyclerView!!.adapter = listAdapter
            recyclerView!!.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun showMovieFavoriteList(movies: MutableList<Movie>) {
        if(movies.isNullOrEmpty()){
            txt_empty.visibility = View.VISIBLE
        }else{
            txt_empty.visibility = View.GONE
        }
        listAdapter = MovieListAdapter(this, movies,
            object : MovieListAdapter.onItemClickListener {
                override fun itemDetail(movieId: Long) {
                    gotoMovieDetail(movieId)
                }
            }
        )
        recyclerView!!.adapter = listAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
}