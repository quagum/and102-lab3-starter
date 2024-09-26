package com.codepath.bestsellerlistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.bestsellerlistapp.R.id

/**
 * [RecyclerView.Adapter] that can display a [Movie] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class MoviesRecyclerViewAdapter(
    private val movies: List<Movie>,
    private val mListener: OnListFragmentInteractionListener?
    )
    : RecyclerView.Adapter<MoviesRecyclerViewAdapter.MovieViewHolder>()
    {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_best_seller_book, parent, false)
        return MovieViewHolder(view)
    }

        /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: Movie? = null
        val mMovieTitle: TextView = mView.findViewById<View>(id.book_title) as TextView
        val mMovieDescription: TextView = mView.findViewById<View>(id.book_description) as TextView
        val mMoviePoster: ImageView = mView.findViewById<View>(id.book_image) as ImageView
        override fun toString(): String {
            return mMovieTitle.toString()
        }
    }

    /**
     * This lets us "bind" each Views in the ViewHolder to its' actual data!
     */
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.mMovieTitle.text = movie.title
        holder.mMovieDescription.text = movie.description

        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500/" + movie.poster_image)
            .centerInside()
            .into(holder.mMoviePoster)

        holder.mView.setOnClickListener {
            holder.mItem?.let { movie ->
                mListener?.onItemClick(movie)
            }
        }
    }

    /**
     * Remember: RecyclerView adapters require a getItemCount() method.
     */
    override fun getItemCount(): Int {
        return movies.size
    }
}