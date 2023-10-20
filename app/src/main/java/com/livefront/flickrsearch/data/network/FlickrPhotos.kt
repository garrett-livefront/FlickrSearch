package com.livefront.flickrsearch.data.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Class to store the list of images returned from the feed
 */
@Parcelize
data class FlickrPhotos(
    val items: List<FlickrPhoto>
): Parcelable

/**
 * Class used to store info about each photo in the feed
 */
@Parcelize
data class FlickrPhoto(
    val title: String,
    val media: Map<String, String>,
    val author: String,
    val tags: String,
    @SerializedName("date_taken") val dateTaken: String,
): Parcelable

/**
 * helper function to extract the user's name out of the author string
 */
fun FlickrPhoto.extractUsername(): String? {
    val regex = Regex("\"(.*?)\"")
    val match = regex.find(author)
    return match?.groupValues?.getOrNull(1)
}