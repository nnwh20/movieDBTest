package com.prototype.moviedbtest.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.prototype.moviedbtest.data.database.AppDatabase
import com.prototype.moviedbtest.data.database.dao.MovieFavoritesRepository
import com.prototype.moviedbtest.data.database.dao.MovieFavoritesRepositoryImpl
import com.prototype.moviedbtest.data.network.api.ApiServiceInterface
import com.prototype.moviedbtest.util.Constants
import com.prototype.moviedbtest.util.CoroutinesProvider
import com.prototype.moviedbtest.util.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton


/**
 * Created by Nanang Wahyu H on 19/06/20.
 */
@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideApiService(): ApiServiceInterface {
        return ApiServiceInterface.create()
    }

    @Provides
    @Singleton
    internal fun provideAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, Constants.APP_DB_NAME).build()

    @Provides
    @Singleton
    internal fun provideMovieFavoritesRepoHelper(appDatabase: AppDatabase): MovieFavoritesRepository = MovieFavoritesRepositoryImpl(appDatabase.MovieFavoritesDao())

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider = SchedulerProvider()

    @Provides
    @Singleton
    internal fun provideCoroutinesProvider(): CoroutinesProvider = CoroutinesProvider()

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }
}