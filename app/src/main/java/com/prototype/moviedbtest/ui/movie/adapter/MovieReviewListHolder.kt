package com.prototype.moviedbtest.ui.movie.adapter

import android.view.View
import android.widget.TextView
import com.prototype.moviedbtest.R
import com.prototype.moviedbtest.data.network.model.serverResponse.Review


/**
 * Created by Nanang Wahyu H on 21/06/20.
 */
class MovieReviewListHolder(internal var view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

    internal var txtAuthor  = itemView.findViewById(R.id.txt_author) as TextView
    internal var txtContent  = itemView.findViewById(R.id.txt_content) as TextView

    internal fun bind(holder: MovieReviewListHolder, review: Review) {
        holder.txtAuthor.text = if (review.author.isEmpty()) "missing title name" else "Written by ${review.author}"
        holder.txtContent.text = if (review.content.isEmpty()) "missing overview" else review.content
    }
}