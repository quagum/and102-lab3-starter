package com.codepath.bestsellerlistapp

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.codepath.bestsellerlistapp.R.id
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * [RecyclerView.Adapter] that can display a [Movie] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */

const val MOVIE_EXTRA = "MOVIE_EXTRA"

class MoviesRecyclerViewAdapter(
    private val movies: List<Movie>,
    private val mListener: OnListFragmentInteractionListener?
    )
    : RecyclerView.Adapter<MoviesRecyclerViewAdapter.MovieViewHolder>()
    {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_movie, parent, false)
        return MovieViewHolder(view)
    }

    /**
     * This lets us "bind" each Views in the ViewHolder to its' actual data!
     */
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.mMovieTitle.text = movie.title
        holder.mMovieDescription.text = movie.overview

        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500/" + movie.poster_path)
            .placeholder(R.drawable.loading)
            .centerInside()
            .apply(RequestOptions().transform(RoundedCornersTransformation(30, 0))) // 30 is the radius for rounded corners
            .into(holder.mMoviePoster)


    }

    /**
     * Remember: RecyclerView adapters require a getItemCount() method.
     */
    override fun getItemCount(): Int {
        return movies.size
    }

    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView), View.OnClickListener {
        val mMovieTitle: TextView = mView.findViewById<View>(id.movie_title) as TextView
        val mMovieDescription: TextView = mView.findViewById<View>(id.movie_description) as TextView
        val mMoviePoster: ImageView = mView.findViewById<View>(id.movie_image) as ImageView

        override fun toString(): String {
            return mMovieTitle.toString()
        }

        init {
            itemView.setOnClickListener(this)
        }

        // TODO: Write a helper method to help set up the onBindViewHolder method
        override fun onClick(v: View?) {
            // TODO: Get selected article
            val movie = movies[adapterPosition]

            // TODO: Navigate to Details screen and pass selected article
            val context = itemView.context
            val intent = Intent(context, Details::class.java)
            intent.putExtra(MOVIE_EXTRA, movie)
            context.startActivity(intent)

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context as Activity,
                mMoviePoster,
                mMoviePoster.transitionName
            )
        }

        fun bind(movie: Movie) {
            mMovieTitle.text = movie.title
            mMovieDescription.text = movie.overview
            Glide.with(mView)
                .load("https://image.tmdb.org/t/p/w500/" + movie.poster_path)
                .apply(
                    RequestOptions().transform(
                        RoundedCornersTransformation(
                            30,
                            0
                        )
                    )
                ) // 30 is the radius for rounded corners
                .into(mMoviePoster)
        }
    }
}
