package com.codepath.bestsellerlistapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers

// --------------------------------//
// CHANGE THIS TO BE YOUR API KEY  //
// --------------------------------//
private const val API_KEY = BuildConfig.API_KEY

/*
 * The class for the only fragment in the app, which contains the progress bar,
 * recyclerView, and performs the network calls to the NY Times API.
 */
class MoviesFragment : Fragment(), OnListFragmentInteractionListener {

    /*
     * Constructing the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)

        // First RecyclerView (Horizontal)
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = horizontalLayoutManager

        // Second RecyclerView (Vertical)
        val upcomingRecyclerView = view.findViewById<View>(R.id.upcoming_list) as RecyclerView
        val horizontalLayoutManager2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        upcomingRecyclerView.layoutManager = horizontalLayoutManager2 //why was it vertical?

        // Progress bar
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar

        updateAdapter(progressBar, recyclerView)
        updateUpcomingAdapter(progressBar, upcomingRecyclerView)

        return view
    }

    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY
        // Using the client, perform the HTTP request
        client[
            "https://api.themoviedb.org/3/movie/popular",
            params,
            object : JsonHttpResponseHandler() {
            /*
             * The onSuccess function gets called when
             * HTTP response status is "200 OK"
             */
                override fun onSuccess(
                statusCode: Int,
                headers: Headers,
                json: JsonHttpResponseHandler.JSON
                ) {
                // The wait for a response is over
                progressBar.hide()

                //TODO - Parse JSON into Models
                //val resultsJSON : JSONObject = json.jsonObject.get("results") as JSONObject
                val booksRawJSON : String = json.jsonObject.get("results").toString()
                val gson = Gson()
                val arrayMovieType = object : TypeToken<List<Movie>>() {}.type
                val models : List<Movie> = gson.fromJson(booksRawJSON, arrayMovieType)
                recyclerView.adapter = MoviesRecyclerViewAdapter(models, this@MoviesFragment)

                // Look for this in Logcat:
                Log.d("MoviesFragment", "response successful")
            }

            /*
             * The onFailure function gets called when
             * HTTP response status is "4XX" (eg. 401, 403, 404)
             */
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                // The wait for a response is over
                progressBar.hide()

                // If the error is not null, log it!
                t?.message?.let {
                    Log.e("MovieFragment", errorResponse)
                }
            }
        }]

    }

    private fun updateUpcomingAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY
        // Using the client, perform the HTTP request
        client[
            "https://api.themoviedb.org/3/movie/upcoming",
            params,
            object : JsonHttpResponseHandler() {
                /*
                 * The onSuccess function gets called when
                 * HTTP response status is "200 OK"
                 */
                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JsonHttpResponseHandler.JSON
                ) {
                    // The wait for a response is over
                    progressBar.hide()

                    //TODO - Parse JSON into Models
                    //val resultsJSON : JSONObject = json.jsonObject.get("results") as JSONObject
                    val booksRawJSON : String = json.jsonObject.get("results").toString()
                    val gson = Gson()
                    val arrayMovieType = object : TypeToken<List<Movie>>() {}.type
                    val models : List<Movie> = gson.fromJson(booksRawJSON, arrayMovieType)
                    recyclerView.adapter = MoviesRecyclerViewAdapter(models, this@MoviesFragment)

                    // Look for this in Logcat:
                    Log.d("MoviesFragment", "response successful")
                }

                /*
                 * The onFailure function gets called when
                 * HTTP response status is "4XX" (eg. 401, 403, 404)
                 */
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    t: Throwable?
                ) {
                    // The wait for a response is over
                    progressBar.hide()

                    // If the error is not null, log it!
                    t?.message?.let {
                        Log.e("MovieFragment", errorResponse)
                    }
                }
            }]

    }


    override fun onItemClick(item: Movie) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }

}
