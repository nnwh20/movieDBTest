package com.prototype.moviedbtest.data.database.dao

import io.reactivex.Observable
import javax.inject.Inject


/**
 * Created by Nanang Wahyu H on 19/06/20.
 */
class MovieFavoritesRepositoryImpl @Inject constructor(private val dao: MovieFavoritesDao) : MovieFavoritesRepository {
    override fun isMovieFavoriteAvailable(id: Long): Observable<Boolean> = Observable.just(dao.loadById(id).isEmpty())
    override fun insertMovie(options: MovieFavorites): Observable<Boolean>? {
        dao.insert(options)
        return Observable.just(true)
    }
    override fun loadMovie(): Observable<List<MovieFavorites>> = Observable.fromCallable { dao.loadAll() }
    override fun deleteByIdMovie(id: Long): Observable<Boolean>? {
        dao.deleteById(id)
        return Observable.just(true)
    }
    override suspend fun loadAllMovie(): List<MovieFavorites> = dao.loadAll()
}