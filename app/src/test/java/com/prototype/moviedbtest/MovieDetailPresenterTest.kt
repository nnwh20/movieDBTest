package com.prototype.moviedbtest

import android.app.Instrumentation
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.prototype.moviedbtest.data.database.dao.MovieFavorites
import com.prototype.moviedbtest.data.database.dao.MovieFavoritesRepository
import com.prototype.moviedbtest.data.network.api.ApiServiceInterface
import com.prototype.moviedbtest.data.network.model.serverResponse.Movie
import com.prototype.moviedbtest.data.network.model.serverResponse.Reviews
import com.prototype.moviedbtest.di.component.AppComponent
import com.prototype.moviedbtest.di.component.DaggerAppComponent
import com.prototype.moviedbtest.ui.movie.MovieDetailContract
import com.prototype.moviedbtest.ui.movie.MovieDetailPresenter
import com.prototype.moviedbtest.util.CoroutinesProvider
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.robolectric.annotation.Config


/**
 * Created by Nanang Wahyu H on 22/06/20.
 */
@RunWith(AndroidJUnit4::class)
@Config(manifest= Config.NONE)
class MovieDetailPresenterTest {

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
    lateinit var view: MovieDetailContract.View
    @Mock
    lateinit var presenter: MovieDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val app = Instrumentation.newApplication(BaseApp::class.java, appContext) as BaseApp
        presenter = MovieDetailPresenter()
        appComponent = DaggerAppComponent.builder()
            .application(app)
            .build()
        app.setAppComponent(appComponent)
        appComponent.inject(presenter)
        presenter.attachView(view)
    }

    @Test
    fun testLoadMovieDetail() = runBlocking {
        val movie: Movie = Mockito.mock(Movie::class.java)
        Mockito.`when`(coroutinesProvider.uiContext).thenReturn(Dispatchers.Main)
        Mockito.`when`(coroutinesProvider.bgContext).thenReturn(Dispatchers.Main)
        Mockito.`when`(api.getMovieDetail(12345, BuildConfig.API_KEY)).thenReturn(movie)
        Mockito.`when`(api.getMovieReviews(12345, BuildConfig.API_KEY)).thenReturn(Reviews(ArrayList()))
        Mockito.`when`(moviesFavoritesRepository.isMovieFavoriteAvailable(12345)).thenReturn(Observable.just(true))

        presenter.coroutinesProvider = coroutinesProvider
        presenter.api = api
        presenter.movieFavoritesRepository = moviesFavoritesRepository

        presenter.getMovieDetail(12345)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMovieDetail(movie)
        Mockito.verify(view).showMovieReview(Reviews(ArrayList()))
        Mockito.verify(view).onMovieStatus(anyBoolean())
        Mockito.verify(view, Mockito.times(2)).hideLoading()
    }

    @Test
    fun testLoadToggleFavorite() = runBlocking {
        val movie: Movie = Mockito.mock(Movie::class.java)
        val movieFavorites = Mockito.mock(MovieFavorites::class.java)
        Mockito.`when`(coroutinesProvider.uiContext).thenReturn(Dispatchers.Main)
        Mockito.`when`(coroutinesProvider.bgContext).thenReturn(Dispatchers.Main)
        Mockito.`when`(moviesFavoritesRepository.deleteByIdMovie(movie.id)).thenReturn(Observable.just(true))
        Mockito.`when`(moviesFavoritesRepository.insertMovie(movieFavorites)).thenReturn(Observable.just(true))

        presenter.coroutinesProvider = coroutinesProvider
        presenter.api = api
        presenter.movieFavoritesRepository = moviesFavoritesRepository

        presenter.toggleFavorite(true, movie)

        Mockito.verify(view).onToggleFavoriteFinish(anyBoolean())
    }
}