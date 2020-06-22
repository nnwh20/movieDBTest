package com.prototype.moviedbtest.ui.base


/**
 * Created by Nanang Wahyu H on 19/06/20.
 */
interface BasePresenter<T> {
    fun attachView(view: T)
    fun detachView()
}