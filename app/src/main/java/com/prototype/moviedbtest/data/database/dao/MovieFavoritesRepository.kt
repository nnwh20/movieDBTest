package com.prototype.moviedbtest.data.database.dao

import io.reactivex.Observable


/**
 * Created by Nanang Wahyu H on 19/06/20.
 */
interface MovieFavoritesRepository {
    fun isMovieFavoriteAvailable(id: Long): Observable<Boolean>
    fun insertMovie(options: MovieFavorites): Observable<Boolean>?
    fun loadMovie(): Observable<List<MovieFavorites>>
    fun deleteByIdMovie(id: Long): Observable<Boolean>?
    suspend fun loadAllMovie(): List<MovieFavorites>
}