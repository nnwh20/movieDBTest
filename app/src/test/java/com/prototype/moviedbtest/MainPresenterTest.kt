package com.prototype.moviedbtest

import android.app.Instrumentation
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.prototype.moviedbtest.data.database.dao.MovieFavoritesRepository
import com.prototype.moviedbtest.data.network.api.ApiServiceInterface
import com.prototype.moviedbtest.data.network.model.serverResponse.Movies
import com.prototype.moviedbtest.di.component.AppComponent
import com.prototype.moviedbtest.di.component.DaggerAppComponent
import com.prototype.moviedbtest.ui.main.MainActivity
import com.prototype.moviedbtest.ui.main.MainContract
import com.prototype.moviedbtest.ui.main.MainPresenter
import com.prototype.moviedbtest.util.CoroutinesProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.robolectric.annotation.Config


/**
 * Created by Nanang Wahyu H on 22/06/20.
 */
@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE)
class MainPresenterTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    private lateinit var appComponent: AppComponent
    @Mock
    lateinit var api: ApiServiceInterface
    @Mock
    lateinit var coroutinesProvider: CoroutinesProvider
    @Mock
    lateinit var moviesFavoritesRepository: MovieFavoritesRepository
    @Mock
    lateinit var view: MainContract.View
    @Mock
    lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val app = Instrumentation.newApplication(BaseApp::class.java, appContext) as BaseApp
        presenter = MainPresenter()
        appComponent = DaggerAppComponent.builder()
            .application(app)
            .build()
        app.setAppComponent(appComponent)
        appComponent.inject(presenter)
        presenter.attachView(view)
    }

    @Test
    fun testLoadMovieNowPlaying() = runBlocking {
        Mockito.`when`(coroutinesProvider.uiContext).thenReturn(Dispatchers.Main)
        Mockito.`when`(api.getNowPlayingMovieList(BuildConfig.API_KEY)).thenReturn(Movies(ArrayList()))

        presenter.coroutinesProvider = coroutinesProvider
        presenter.api = api

        presenter.showMovieList(MainActivity.CATEGORY.NOW_PLAYING)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMovieList(Movies(ArrayList()))
        Mockito.verify(view, Mockito.times(2)).hideLoading()
    }

    @Test
    fun testLoadMoviePopular() = runBlocking {
        Mockito.`when`(coroutinesProvider.uiContext).thenReturn(Dispatchers.Main)
        Mockito.`when`(api.getPopularMovieList(BuildConfig.API_KEY)).thenReturn(Movies(ArrayList()))

        presenter.coroutinesProvider = coroutinesProvider
        presenter.api = api

        presenter.showMovieList(MainActivity.CATEGORY.POPULAR)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMovieList(Movies(ArrayList()))
        Mockito.verify(view, Mockito.times(2)).hideLoading()
    }

    @Test
    fun testLoadMovieTopRated() = runBlocking {
        Mockito.`when`(coroutinesProvider.uiContext).thenReturn(Dispatchers.Main)
        Mockito.`when`(api.getTopRatedMovieList(BuildConfig.API_KEY)).thenReturn(Movies(ArrayList()))

        presenter.coroutinesProvider = coroutinesProvider
        presenter.api = api

        presenter.showMovieList(MainActivity.CATEGORY.TOP_RATED)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMovieList(Movies(ArrayList()))
        Mockito.verify(view, Mockito.times(2)).hideLoading()
    }

    @Test
    fun testLoadMovieUpcoming() = runBlocking {
        Mockito.`when`(coroutinesProvider.uiContext).thenReturn(Dispatchers.Main)
        Mockito.`when`(api.getUpcomingMovieList(BuildConfig.API_KEY)).thenReturn(Movies(ArrayList()))

        presenter.coroutinesProvider = coroutinesProvider
        presenter.api = api

        presenter.showMovieList(MainActivity.CATEGORY.UPCOMING)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMovieList(Movies(ArrayList()))
        Mockito.verify(view, Mockito.times(2)).hideLoading()
    }

    @Test
    fun testLoadMovieFavorites() = runBlocking {
        Mockito.`when`(coroutinesProvider.uiContext).thenReturn(Dispatchers.Main)
        Mockito.`when`(coroutinesProvider.bgContext).thenReturn(Dispatchers.Main)
        Mockito.`when`(moviesFavoritesRepository.loadAllMovie()).thenReturn(arrayListOf())

        presenter.coroutinesProvider = coroutinesProvider
        presenter.movieFavoritesRepository = moviesFavoritesRepository

        presenter.loadMovieFavoriteList()

        Mockito.verify(view).showMovieFavoriteList(arrayListOf())
    }
}