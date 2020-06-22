package com.prototype.moviedbtest.di.component

import android.app.Application
import com.prototype.moviedbtest.di.builder.ActivityBuilder
import com.prototype.moviedbtest.di.module.AppModule
import com.prototype.moviedbtest.ui.main.MainPresenter
import com.prototype.moviedbtest.ui.movie.MovieDetailPresenter
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton


/**
 * Created by Nanang Wahyu H on 19/06/20.
 */

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    AppModule::class, ActivityBuilder::class])
interface AppComponent : AndroidInjector<DaggerApplication> {

    override fun inject(target: DaggerApplication)

    fun inject(presenter: MainPresenter)
    fun inject(presenter: MovieDetailPresenter)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}