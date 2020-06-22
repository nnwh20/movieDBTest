package com.prototype.moviedbtest.data.network.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.prototype.moviedbtest.BuildConfig
import com.prototype.moviedbtest.data.network.model.serverResponse.Movie
import com.prototype.moviedbtest.data.network.model.serverResponse.Movies
import com.prototype.moviedbtest.data.network.model.serverResponse.Reviews
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by Nanang Wahyu H on 19/06/20.
 */
interface ApiServiceInterface {

    @GET("now_playing")
    suspend fun getNowPlayingMovieList(@Query("api_key") apiKey: String): Movies

    @GET("popular")
    suspend fun getPopularMovieList(@Query("api_key") apiKey: String): Movies

    @GET("top_rated")
    suspend fun getTopRatedMovieList(@Query("api_key") apiKey: String): Movies

    @GET("upcoming")
    suspend fun getUpcomingMovieList(@Query("api_key") apiKey: String): Movies

    @GET("{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Long, @Query("api_key") apiKey: String): Movie

    @GET("{movie_id}/reviews")
    suspend fun getMovieReviews(@Path("movie_id") movieId: Long, @Query("api_key") apiKey: String): Reviews

    companion object Factory {
        fun create(): ApiServiceInterface {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(BuildConfig.BASE_URL)
                .build()

            return retrofit.create(ApiServiceInterface::class.java)
        }
    }
}