package com.prototype.moviedbtest.ui.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.prototype.moviedbtest.R
import com.prototype.moviedbtest.data.network.model.serverResponse.Review


/**
 * Created by Nanang Wahyu H on 21/06/20.
 */
class MovieReviewListAdapter (private val context: Context, private val list: MutableList<Review>
) : androidx.recyclerview.widget.RecyclerView.Adapter<MovieReviewListHolder>() {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MovieReviewListHolder, position: Int) {
        val response = list[position]
        holder.bind(holder, response)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieReviewListHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_movie_review_list, parent, false)
        return MovieReviewListHolder(itemView)
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getElement(position: Int) = list[position]

    fun addAll(_list: List<Review>) {
        list.addAll(_list)
        notifyDataSetChanged()
    }

    fun removeAll() {
        list.clear()
        notifyDataSetChanged()
    }
}