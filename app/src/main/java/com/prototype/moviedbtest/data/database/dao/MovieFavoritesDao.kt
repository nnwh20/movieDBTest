package com.prototype.moviedbtest.data.database.dao

import androidx.room.*


/**
 * Created by Nanang Wahyu H on 19/06/20.
 */
@Dao
interface MovieFavoritesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(options: List<MovieFavorites>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(options: MovieFavorites)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(movie: MovieFavorites)

    @Query("SELECT * FROM MovieFavorites")
    fun loadAll(): List<MovieFavorites>

    @Query("DELETE FROM MovieFavorites WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT * FROM MovieFavorites WHERE id = :id")
    fun loadById(id: Long):List<MovieFavorites>
}