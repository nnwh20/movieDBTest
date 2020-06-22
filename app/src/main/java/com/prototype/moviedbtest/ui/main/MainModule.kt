package com.prototype.moviedbtest.ui.main

import com.prototype.moviedbtest.di.scope.ActivityScoped
import dagger.Binds
import dagger.Module


/**
 * Created by Nanang Wahyu H on 19/06/20.
 */
@Module
abstract class MainModule {

    @ActivityScoped
    @Binds
    internal abstract fun mainPresenter(main: MainPresenter): MainContract.Presenter

}