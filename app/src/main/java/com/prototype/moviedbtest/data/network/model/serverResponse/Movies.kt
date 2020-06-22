package com.prototype.moviedbtest.data.network.model.serverResponse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by Nanang Wahyu H on 19/06/20.
 */
@Parcelize
data class Movies (
    @SerializedName("results")
    var result: ArrayList<Movie>
) : Parcelable