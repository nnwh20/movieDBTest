package com.prototype.moviedbtest

import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.prototype.moviedbtest.di.component.AppComponent
import com.prototype.moviedbtest.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Created by Nanang Wahyu H on 19/06/20.
 */
class BaseApp : DaggerApplication() {

    private var defaultUEH: Thread.UncaughtExceptionHandler? = null

    companion object {
        lateinit var instance: BaseApp private set
        lateinit var sAppComponent: AppComponent
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        sAppComponent = DaggerAppComponent.builder().application(this).build()
        sAppComponent.inject(this)
        instance = this
        return sAppComponent
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        defaultUEH = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this::handleUnCaughtException)
    }

    private fun handleUnCaughtException(
        thread: Thread,
        throwable: Throwable
    ) {
        Log.e("ERROR","[DEBUG] Uncaught Exception: $throwable")
        throwable.printStackTrace()
        defaultUEH!!.uncaughtException(thread, throwable)
    }

    fun setAppComponent(appComponent: AppComponent) {
        sAppComponent = appComponent
    }

    fun getAppComponent(): AppComponent {
        return sAppComponent
    }
}