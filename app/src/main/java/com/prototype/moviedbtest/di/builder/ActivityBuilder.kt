package com.prototype.moviedbtest.di.builder

import com.prototype.moviedbtest.di.scope.ActivityScoped
import com.prototype.moviedbtest.ui.main.MainActivity
import com.prototype.moviedbtest.ui.main.MainModule
import com.prototype.moviedbtest.ui.movie.MovieDetailActivity
import com.prototype.moviedbtest.ui.movie.MovieDetailModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by Nanang Wahyu H on 19/06/20.
 */
@Module
abstract class ActivityBuilder {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MovieDetailModule::class])
    internal abstract fun bindMovieDetailActivity(): MovieDetailActivity
}