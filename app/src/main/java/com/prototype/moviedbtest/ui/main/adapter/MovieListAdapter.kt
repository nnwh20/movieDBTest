package com.prototype.moviedbtest.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.prototype.moviedbtest.R
import com.prototype.moviedbtest.data.network.model.serverResponse.Movie


/**
 * Created by Nanang Wahyu H on 20/06/20.
 */
class MovieListAdapter (private val context: Context, private val list: MutableList<Movie>, listener: onItemClickListener
) : androidx.recyclerview.widget.RecyclerView.Adapter<MovieListHolder>() {

    private val listener: onItemClickListener = listener

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MovieListHolder, position: Int) {
        val response = list[position]
        holder.bind(holder, response, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_movie_list, parent, false)
        return MovieListHolder(itemView)
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getElement(position: Int) = list[position]

    fun addAll(_list: List<Movie>) {
        list.addAll(_list)
        notifyDataSetChanged()
    }

    fun removeAll() {
        list.clear()
        notifyDataSetChanged()
    }

    interface onItemClickListener {
        fun itemDetail(movieId: Long)
    }
}