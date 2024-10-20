package com.codepath.bestsellerlistapp;

import android.os.Bundle
import android.transition.TransitionInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


private const val TAG = "Details"

class Details : AppCompatActivity() {
    private lateinit var original_language: TextView
    private lateinit var popularity: TextView
    private lateinit var release_date: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_detail)

        window.sharedElementEnterTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)

        original_language = findViewById(R.id.original_language)
        popularity = findViewById(R.id.popularity)
        release_date = findViewById(R.id.release_date)


        val movie = intent.getSerializableExtra(MOVIE_EXTRA) as Movie

        original_language.text = movie.original_language
        popularity.text = movie.popularity.toString()
        release_date.text = movie.release_date

    }
}