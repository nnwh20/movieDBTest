package com.prototype.moviedbtest.ui.main.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.prototype.moviedbtest.R
import com.prototype.moviedbtest.data.network.model.serverResponse.Movie
import com.prototype.moviedbtest.util.CommonUtils.customFormatDate
import com.prototype.moviedbtest.util.CommonUtils.formatStringtoDate
import com.prototype.moviedbtest.util.CommonUtils.loadImage
import com.prototype.moviedbtest.util.dp


/**
 * Created by Nanang Wahyu H on 20/06/20.
 */
class MovieListHolder(internal var view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

    internal var layoutParent  = itemView.findViewById(R.id.layout_parent) as CardView
    internal var imgPoster  = itemView.findViewById(R.id.img_poster) as ImageView
    internal var txtTitle  = itemView.findViewById(R.id.txt_title) as TextView
    internal var txtSubtitle  = itemView.findViewById(R.id.txt_subtitle) as TextView
    internal var txtDesc  = itemView.findViewById(R.id.txt_desc) as TextView

    internal fun bind(holder: MovieListHolder, movie: Movie, listener:MovieListAdapter.onItemClickListener) {
        holder.txtTitle.text = if (movie.title.isEmpty()) "missing title name" else movie.title
        val date = formatStringtoDate(movie.releaseDate, "yyyy-MM-dd")
        date?.let { holder.txtSubtitle.text = if (movie.releaseDate.isEmpty()) "missing release date" else customFormatDate(it.time, "dd MMMM yyyy") }
        holder.txtDesc.text = if (movie.overview.isEmpty()) "missing overview" else movie.overview
        val posterUrl = "https://image.tmdb.org/t/p/w200${movie.posterPath}"
        loadImage(view.context, posterUrl, holder.imgPoster, 6.dp)
        layoutParent.setOnClickListener { listener.itemDetail(movie.id) }
    }
}