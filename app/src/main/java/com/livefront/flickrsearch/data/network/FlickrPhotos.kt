package com.livefront.flickrsearch.data.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlickrPhotos(
    val items: List<FlickrPhoto>
): Parcelable

@Parcelize
data class FlickrPhoto(
    val title: String,
    val media: Map<String, String>,
    val author: String,
    val tags: String,
    @SerializedName("date_taken") val dateTaken: String,
): Parcelable

fun FlickrPhoto.extractUsername(): String? {
    val regex = Regex("\"(.*?)\"")
    val match = regex.find(author)
    return match?.groupValues?.getOrNull(1)
}