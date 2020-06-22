package com.prototype.moviedbtest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prototype.moviedbtest.data.database.converters.Converters
import com.prototype.moviedbtest.data.database.dao.MovieFavorites
import com.prototype.moviedbtest.data.database.dao.MovieFavoritesDao


/**
 * Created by Nanang Wahyu H on 19/06/20.
 */
@Database(entities = [(MovieFavorites::class)], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun MovieFavoritesDao(): MovieFavoritesDao
}