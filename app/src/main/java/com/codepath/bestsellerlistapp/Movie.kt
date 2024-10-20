package com.codepath.bestsellerlistapp

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.serialization.InternalSerializationApi
import java.io.Serializable



/**
 * The Model for storing a single book from the NY Times API
 *
 * SerializedName tags MUST match the JSON response for the
 * object to correctly parse with the gson library.
 */
@Keep
class Movie : Serializable {
    @JvmField
    @SerializedName("title")
    var title: String? = null

    @SerializedName("overview")
    var overview: String? = null

    @SerializedName("poster_path")
    var poster_path: String? = null

    @SerializedName("original_language")
    var original_language: String? = null

    @SerializedName("popularity")
    var popularity: String? = null

    @SerializedName("release_date")
    var release_date: String? = null

}