package com.prototype.moviedbtest.data.database.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by Nanang Wahyu H on 19/06/20.
 */
@Entity(tableName = "MovieFavorites", indices = [Index(value = ["id"], unique = true)])
data class MovieFavorites (

    @Expose
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    var id: Long,

    @Expose
    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title: String,

    @Expose
    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    var releaseDate: String,

    @Expose
    @SerializedName("desc")
    @ColumnInfo(name = "desc")
    var desc: String,

    @Expose
    @SerializedName("poster")
    @ColumnInfo(name = "poster")
    var poster: String
)

