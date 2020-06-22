package com.prototype.moviedbtest.ui.movie

import com.prototype.moviedbtest.di.scope.ActivityScoped
import dagger.Binds
import dagger.Module


/**
 * Created by Nanang Wahyu H on 21/06/20.
 */
@Module
abstract class MovieDetailModule {

    @ActivityScoped
    @Binds
    internal abstract fun movieDetailPresenter(main: MovieDetailPresenter): MovieDetailContract.Presenter
}