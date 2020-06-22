package com.prototype.moviedbtest.data.network.model.serverResponse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by Nanang Wahyu H on 22/06/20.
 */
@Parcelize
data class Reviews (
    @SerializedName("results")
    var result: ArrayList<Review>
) : Parcelable

@Parcelize
data class Review (
    @SerializedName("id")
    var id: String,
    @SerializedName("author")
    var author: String,
    @SerializedName("content")
    var content: String,
    @SerializedName("url")
    var url: String
) : Parcelable