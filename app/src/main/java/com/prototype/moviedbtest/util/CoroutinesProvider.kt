package com.prototype.moviedbtest.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


/**
 * Created by Nanang Wahyu H on 19/06/20.
 */
class CoroutinesProvider {

    // dispatches execution into Android main thread
    val uiContext: CoroutineDispatcher by lazy { Dispatchers.Main }
    // represent a pool of shared threads as coroutine dispatcher
    val bgContext: CoroutineDispatcher by lazy { Dispatchers.IO }
}